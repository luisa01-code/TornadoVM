/*
 * This file is part of Tornado: A heterogeneous programming framework:
 * https://github.com/beehive-lab/tornadovm
 *
 * Copyright (c) 2020, APT Group, Department of Computer Science,
 * School of Engineering, The University of Manchester. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 */
package uk.ac.manchester.tornado.drivers.ptx.graal;

import jdk.vm.ci.common.InitTimer;
import jdk.vm.ci.hotspot.HotSpotConstantReflectionProvider;
import jdk.vm.ci.hotspot.HotSpotJVMCIRuntime;
import jdk.vm.ci.hotspot.HotSpotMetaAccessProvider;
import jdk.vm.ci.runtime.JVMCIBackend;
import org.graalvm.compiler.hotspot.HotSpotGraalCompiler;
import org.graalvm.compiler.hotspot.meta.HotSpotGCProvider;
import org.graalvm.compiler.hotspot.meta.HotSpotStampProvider;
import org.graalvm.compiler.nodes.graphbuilderconf.GraphBuilderConfiguration;
import org.graalvm.compiler.nodes.graphbuilderconf.InvocationPlugins;
import org.graalvm.compiler.nodes.spi.Replacements;
import org.graalvm.compiler.options.OptionValues;
import org.graalvm.compiler.phases.util.Providers;
import org.graalvm.compiler.printer.GraalDebugHandlersFactory;
import org.graalvm.compiler.replacements.StandardGraphBuilderPlugins;
import org.graalvm.compiler.replacements.classfile.ClassfileBytecodeProvider;
import uk.ac.manchester.tornado.drivers.ptx.PTXDevice;
import uk.ac.manchester.tornado.drivers.ptx.PTXDeviceContext;
import uk.ac.manchester.tornado.drivers.ptx.PTXTargetDescription;
import uk.ac.manchester.tornado.drivers.ptx.graal.backend.PTXBackend;
import uk.ac.manchester.tornado.drivers.ptx.graal.compiler.PTXCompilerConfiguration;
import uk.ac.manchester.tornado.drivers.ptx.graal.compiler.plugins.PTXGraphBuilderPlugins;
import uk.ac.manchester.tornado.drivers.ptx.graal.lir.PTXAddressLowering;
import uk.ac.manchester.tornado.drivers.ptx.graal.lir.PTXKind;
import uk.ac.manchester.tornado.runtime.TornadoVMConfig;
import uk.ac.manchester.tornado.runtime.graal.DummySnippetFactory;
import uk.ac.manchester.tornado.runtime.graal.compiler.TornadoConstantFieldProvider;
import uk.ac.manchester.tornado.runtime.graal.compiler.TornadoForeignCallsProvider;
import uk.ac.manchester.tornado.runtime.graal.compiler.TornadoReplacements;
import uk.ac.manchester.tornado.runtime.graal.compiler.TornadoSnippetReflectionProvider;

import java.util.Collections;

import static jdk.vm.ci.common.InitTimer.timer;

public class PTXHotSpotBackendFactory {

    private static final HotSpotStampProvider stampProvider = new HotSpotStampProvider();
    private static final TornadoSnippetReflectionProvider snippetReflection = new TornadoSnippetReflectionProvider();
    private static final TornadoForeignCallsProvider foreignCalls = new TornadoForeignCallsProvider();
    private static final TornadoConstantFieldProvider constantFieldProvider = new TornadoConstantFieldProvider();
    private static final PTXCompilerConfiguration compilerConfiguration = new PTXCompilerConfiguration();
    private static final PTXAddressLowering addressLowering = new PTXAddressLowering();

    public static PTXBackend createBackend(OptionValues options, HotSpotJVMCIRuntime jvmciRuntime, TornadoVMConfig vmConfig, PTXDevice device) {
        JVMCIBackend jvmci = jvmciRuntime.getHostJVMCIBackend();
        HotSpotMetaAccessProvider metaAccess = (HotSpotMetaAccessProvider) jvmci.getMetaAccess();
        HotSpotConstantReflectionProvider constantReflection = (HotSpotConstantReflectionProvider) jvmci.getConstantReflection();

        PTXArchitecture arch = new PTXArchitecture(PTXKind.U64, device.getByteOrder());
        PTXTargetDescription target = new PTXTargetDescription(arch);
        PTXDeviceContext deviceContext = device.getPTXContext().getDeviceContext();
        PTXCodeProvider codeCache = new PTXCodeProvider(target);
        HotSpotGraalCompiler hotSpotGraalCompiler = ((HotSpotGraalCompiler) jvmciRuntime.getCompiler());
        HotSpotGCProvider hotSpotGCProvider = new HotSpotGCProvider(hotSpotGraalCompiler.getGraalRuntime().getVMConfig());

        PTXProviders providers;
        PTXSuitesProvider suites;
        GraphBuilderConfiguration.Plugins plugins;
        PTXLoweringProvider lowerer;

        try (InitTimer t = timer("create providers")) {
            lowerer = new PTXLoweringProvider(metaAccess, foreignCalls, constantReflection, target, false, vmConfig);

            Providers p = new Providers(metaAccess, codeCache, constantReflection, constantFieldProvider, foreignCalls, lowerer, null, stampProvider, hotSpotGCProvider);
            ClassfileBytecodeProvider bytecodeProvider = new ClassfileBytecodeProvider(metaAccess, snippetReflection);
            GraalDebugHandlersFactory graalDebugHandlersFactory = new GraalDebugHandlersFactory(snippetReflection);
            TornadoReplacements replacements = new TornadoReplacements(graalDebugHandlersFactory, p, snippetReflection, bytecodeProvider, target);
            plugins = createGraphBuilderPlugins(metaAccess, replacements);

            replacements.setGraphBuilderPlugins(plugins);

            suites = new PTXSuitesProvider(options, deviceContext, plugins, metaAccess, compilerConfiguration, addressLowering);
            providers = new PTXProviders(metaAccess, codeCache, constantReflection, constantFieldProvider, foreignCalls, lowerer, replacements, stampProvider, null, suites);

            lowerer.initialize(options, Collections.singleton(graalDebugHandlersFactory), new DummySnippetFactory(), providers, snippetReflection);

        }
        try (InitTimer rt = timer("instantiate backend")) {
            return new PTXBackend(providers, deviceContext, target, codeCache, options);
        }

    }

    protected static GraphBuilderConfiguration.Plugins createGraphBuilderPlugins(HotSpotMetaAccessProvider metaAccess, Replacements replacements) {
        InvocationPlugins invocationPlugins = new InvocationPlugins();
        GraphBuilderConfiguration.Plugins plugins = new GraphBuilderConfiguration.Plugins(invocationPlugins);

        PTXGraphBuilderPlugins.registerParameterPlugins(plugins);
        PTXGraphBuilderPlugins.registerNewInstancePlugins(plugins);

        StandardGraphBuilderPlugins.registerInvocationPlugins(metaAccess, snippetReflection, invocationPlugins, replacements, true, true);
        PTXGraphBuilderPlugins.registerInvocationPlugins(plugins, invocationPlugins);
        return plugins;
    }
}
