/*
 * Copyright (c) 2020, APT Group, Department of Computer Science,
 * School of Engineering, The University of Manchester. All rights reserved.
 * Copyright (c) 2009, 2017, Oracle and/or its affiliates. All rights reserved.
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

import jdk.vm.ci.code.InstalledCode;
import uk.ac.manchester.tornado.drivers.ptx.PTXDeviceContext;
import uk.ac.manchester.tornado.drivers.ptx.PTXModule;
import uk.ac.manchester.tornado.runtime.common.CallStack;
import uk.ac.manchester.tornado.runtime.common.DeviceBuffer;
import uk.ac.manchester.tornado.runtime.common.TornadoInstalledCode;
import uk.ac.manchester.tornado.runtime.tasks.meta.TaskMetaData;

import static uk.ac.manchester.tornado.api.exceptions.TornadoInternalError.unimplemented;

public class PTXInstalledCode extends InstalledCode implements TornadoInstalledCode {
    private PTXModule module;
    private PTXDeviceContext deviceContext;

    public PTXInstalledCode(String name, PTXModule module, PTXDeviceContext deviceContext) {
        super(name);
        this.module = module;
        this.deviceContext = deviceContext;
    }

    @Override
    public int launchWithDependencies(CallStack stack, DeviceBuffer atomicSpace, TaskMetaData meta, long batchThreads, int[] waitEvents) {
        unimplemented("launch with deps");
        return 0;
    }

    @Override
    public int launchWithoutDependencies(CallStack stack, DeviceBuffer atomicSpace, TaskMetaData meta, long batchThreads) {
        return deviceContext.enqueueKernelLaunch(module, stack, batchThreads);
    }

    public String getGeneratedSourceCode() {
        return new String(module.getSource());
    }
}
