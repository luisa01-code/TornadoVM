import uk.ac.manchester.tornado.drivers.ptx.PTXTornadoDriverProvider;

module tornado.drivers.ptx {
    requires transitive jdk.internal.vm.ci;
    requires transitive jdk.internal.vm.compiler;
    requires transitive tornado.api;
    requires transitive tornado.runtime;
    requires tornado.drivers.common;

    exports uk.ac.manchester.tornado.drivers.ptx;
    exports uk.ac.manchester.tornado.drivers.ptx.enums;
    exports uk.ac.manchester.tornado.drivers.ptx.graal;
    exports uk.ac.manchester.tornado.drivers.ptx.graal.asm;
    exports uk.ac.manchester.tornado.drivers.ptx.graal.backend;
    exports uk.ac.manchester.tornado.drivers.ptx.graal.compiler;
    exports uk.ac.manchester.tornado.drivers.ptx.graal.lir;
    exports uk.ac.manchester.tornado.drivers.ptx.graal.meta;
    exports uk.ac.manchester.tornado.drivers.ptx.graal.nodes;
    exports uk.ac.manchester.tornado.drivers.ptx.graal.nodes.calc;
    exports uk.ac.manchester.tornado.drivers.ptx.graal.nodes.vector;
    exports uk.ac.manchester.tornado.drivers.ptx.graal.phases;
    exports uk.ac.manchester.tornado.drivers.ptx.mm;
    exports uk.ac.manchester.tornado.drivers.ptx.runtime;

    provides uk.ac.manchester.tornado.runtime.TornadoDriverProvider with
            PTXTornadoDriverProvider;

}
