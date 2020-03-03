package uk.ac.manchester.tornado.drivers.cuda;

import uk.ac.manchester.tornado.runtime.common.TornadoLogger;

public class CUDAStream extends TornadoLogger {
    private final byte[] streamWrapper;

    public CUDAStream() {
        streamWrapper = cuCreateStream();
    }

    private native static int writeArrayDtoH(long bufferId, long offset, long length, byte[] array, long hostOffset, int[] waitEvents);
    private native static int writeArrayDtoH(long bufferId, long offset, long length, short[] array, long hostOffset, int[] waitEvents);
    private native static int writeArrayDtoH(long bufferId, long offset, long length, char[] array, long hostOffset, int[] waitEvents);
    private native static int writeArrayDtoH(long bufferId, long offset, long length, int[] array, long hostOffset, int[] waitEvents);
    private native static int writeArrayDtoH(long bufferId, long offset, long length, long[] array, long hostOffset, int[] waitEvents);
    private native static int writeArrayDtoH(long bufferId, long offset, long length, float[] array, long hostOffset, int[] waitEvents);
    private native static int writeArrayDtoH(long bufferId, long offset, long length, double[] array, long hostOffset, int[] waitEvents);

    private native static int writeArrayDtoHAsync(long bufferId, long offset, long length, byte[] array, long hostOffset, int[] waitEvents);
    private native static int writeArrayDtoHAsync(long bufferId, long offset, long length, short[] array, long hostOffset, int[] waitEvents);
    private native static int writeArrayDtoHAsync(long bufferId, long offset, long length, char[] array, long hostOffset, int[] waitEvents);
    private native static int writeArrayDtoHAsync(long bufferId, long offset, long length, int[] array, long hostOffset, int[] waitEvents);
    private native static int writeArrayDtoHAsync(long bufferId, long offset, long length, long[] array, long hostOffset, int[] waitEvents);
    private native static int writeArrayDtoHAsync(long bufferId, long offset, long length, float[] array, long hostOffset, int[] waitEvents);
    private native static int writeArrayDtoHAsync(long bufferId, long offset, long length, double[] array, long hostOffset, int[] waitEvents);

    private native static void writeArrayHtoD(long bufferId, long offset, long length, byte[] array, long hostOffset, int[] waitEvents);
    private native static void writeArrayHtoD(long bufferId, long offset, long length, short[] array, long hostOffset, int[] waitEvents);
    private native static void writeArrayHtoD(long bufferId, long offset, long length, char[] array, long hostOffset, int[] waitEvents);
    private native static void writeArrayHtoD(long bufferId, long offset, long length, int[] array, long hostOffset, int[] waitEvents);
    private native static void writeArrayHtoD(long bufferId, long offset, long length, long[] array, long hostOffset, int[] waitEvents);
    private native static void writeArrayHtoD(long bufferId, long offset, long length, float[] array, long hostOffset, int[] waitEvents);
    private native static void writeArrayHtoD(long bufferId, long offset, long length, double[] array, long hostOffset, int[] waitEvents);

    private native static int writeArrayHtoDAsync(long bufferId, long offset, long length, byte[] array, long hostOffset, int[] waitEvents);
    private native static int writeArrayHtoDAsync(long bufferId, long offset, long length, short[] array, long hostOffset, int[] waitEvents);
    private native static int writeArrayHtoDAsync(long bufferId, long offset, long length, char[] array, long hostOffset, int[] waitEvents);
    private native static int writeArrayHtoDAsync(long bufferId, long offset, long length, int[] array, long hostOffset, int[] waitEvents);
    private native static int writeArrayHtoDAsync(long bufferId, long offset, long length, long[] array, long hostOffset, int[] waitEvents);
    private native static int writeArrayHtoDAsync(long bufferId, long offset, long length, float[] array, long hostOffset, int[] waitEvents);
    private native static int writeArrayHtoDAsync(long bufferId, long offset, long length, double[] array, long hostOffset, int[] waitEvents);

    private native static int cuLaunchKernel(byte[] module, String name, int gridDimX, int gridDimY, int gridDimZ, int blockDimX, int blockDimY, int blockDimZ, long sharedMemBytes, byte[] stream, byte[] args);

    private native static byte[] cuCreateStream();
    private native static void cuDestroyStream(byte[] streamWrapper);
    private native static void cuStreamSynchronize(byte[] streamWrapper);

    public int enqueueKernelLaunch(CUDAModule module, byte[] kernelParams, int[] gridDim, int[] blockDim) {
        return cuLaunchKernel(module.nativeModule, module.kernelFunctionName,
                              gridDim[0], gridDim[1], gridDim[2],
                              blockDim[0], blockDim[1], blockDim[2],
                              0, streamWrapper,
                              kernelParams
        );
    }

    public int enqueueRead(long bufferId, long offset, long length, byte[] array, long hostOffset, int[] waitEvents) {
        return writeArrayDtoH(bufferId, offset, length, array, hostOffset, waitEvents);
    }

    public int enqueueRead(long bufferId, long offset, long length, short[] array, long hostOffset, int[] waitEvents) {
        return writeArrayDtoH(bufferId, offset, length, array, hostOffset, waitEvents);
    }

    public int enqueueRead(long bufferId, long offset, long length, char[] array, long hostOffset, int[] waitEvents) {
        return writeArrayDtoH(bufferId, offset, length, array, hostOffset, waitEvents);
    }

    public int enqueueRead(long bufferId, long offset, long length, int[] array, long hostOffset, int[] waitEvents) {
        return writeArrayDtoH(bufferId, offset, length, array, hostOffset, waitEvents);
    }

    public int enqueueRead(long bufferId, long offset, long length, long[] array, long hostOffset, int[] waitEvents) {
        return writeArrayDtoH(bufferId, offset, length, array, hostOffset, waitEvents);
    }

    public int enqueueRead(long bufferId, long offset, long length, float[] array, long hostOffset, int[] waitEvents) {
        return writeArrayDtoH(bufferId, offset, length, array, hostOffset, waitEvents);
    }

    public int enqueueRead(long bufferId, long offset, long length, double[] array, long hostOffset, int[] waitEvents) {
        return writeArrayDtoH(bufferId, offset, length, array, hostOffset, waitEvents);
    }


    public int enqueueAsyncRead(long bufferId, long offset, long length, byte[] array, long hostOffset, int[] waitEvents) {
        return writeArrayDtoHAsync(bufferId, offset, length, array, hostOffset, waitEvents);
    }

    public int enqueueAsyncRead(long bufferId, long offset, long length, short[] array, long hostOffset, int[] waitEvents) {
        return writeArrayDtoHAsync(bufferId, offset, length, array, hostOffset, waitEvents);
    }

    public int enqueueAsyncRead(long bufferId, long offset, long length, char[] array, long hostOffset, int[] waitEvents) {
        return writeArrayDtoHAsync(bufferId, offset, length, array, hostOffset, waitEvents);
    }

    public int enqueueAsyncRead(long bufferId, long offset, long length, int[] array, long hostOffset, int[] waitEvents) {
        return writeArrayDtoHAsync(bufferId, offset, length, array, hostOffset, waitEvents);
    }

    public int enqueueAsyncRead(long bufferId, long offset, long length, long[] array, long hostOffset, int[] waitEvents) {
        return writeArrayDtoHAsync(bufferId, offset, length, array, hostOffset, waitEvents);
    }

    public int enqueueAsyncRead(long bufferId, long offset, long length, float[] array, long hostOffset, int[] waitEvents) {
        return writeArrayDtoHAsync(bufferId, offset, length, array, hostOffset, waitEvents);
    }

    public int enqueueAsyncRead(long bufferId, long offset, long length, double[] array, long hostOffset, int[] waitEvents) {
        return writeArrayDtoHAsync(bufferId, offset, length, array, hostOffset, waitEvents);
    }


    public void enqueueWrite(long bufferId, long offset, long length, byte[] array, long hostOffset, int[] waitEvents) {
        writeArrayHtoD(bufferId, offset, length, array, hostOffset, waitEvents);
    }

    public void enqueueWrite(long bufferId, long offset, long length, short[] array, long hostOffset, int[] waitEvents) {
        writeArrayHtoD(bufferId, offset, length, array, hostOffset, waitEvents);
    }

    public void enqueueWrite(long bufferId, long offset, long length, char[] array, long hostOffset, int[] waitEvents) {
        writeArrayHtoD(bufferId, offset, length, array, hostOffset, waitEvents);
    }

    public void enqueueWrite(long bufferId, long offset, long length, int[] array, long hostOffset, int[] waitEvents) {
        writeArrayHtoD(bufferId, offset, length, array, hostOffset, waitEvents);
    }

    public void enqueueWrite(long bufferId, long offset, long length, long[] array, int hostOffset, int[] waitEvents) {
        writeArrayHtoD(bufferId, offset, length, array, hostOffset, waitEvents);
    }

    public void enqueueWrite(long bufferId, long offset, long length, float[] array, int hostOffset, int[] waitEvents) {
        writeArrayHtoD(bufferId, offset, length, array, hostOffset, waitEvents);
    }

    public void enqueueWrite(long bufferId, long offset, long length, double[] array, int hostOffset, int[] waitEvents) {
        writeArrayHtoD(bufferId, offset, length, array, hostOffset, waitEvents);
    }


    public int enqueueAsyncWrite(long bufferId, long offset, long length, byte[] array, long hostOffset, int[] waitEvents) {
        return writeArrayHtoDAsync(bufferId, offset, length, array, hostOffset, waitEvents);
    }

    public int enqueueAsyncWrite(long bufferId, long offset, long length, char[] array, long hostOffset, int[] waitEvents) {
        return writeArrayHtoDAsync(bufferId, offset, length, array, hostOffset, waitEvents);
    }

    public int enqueueAsyncWrite(long bufferId, long offset, long length, short[] array, long hostOffset, int[] waitEvents) {
        return writeArrayHtoDAsync(bufferId, offset, length, array, hostOffset, waitEvents);
    }

    public int enqueueAsyncWrite(long bufferId, long offset, long length, int[] array, long hostOffset, int[] waitEvents) {
        return writeArrayHtoDAsync(bufferId, offset, length, array, hostOffset, waitEvents);
    }

    public int enqueueAsyncWrite(long bufferId, long offset, long length, long[] array, long hostOffset, int[] waitEvents) {
        return writeArrayHtoDAsync(bufferId, offset, length, array, hostOffset, waitEvents);
    }

    public int enqueueAsyncWrite(long bufferId, long offset, long length, float[] array, long hostOffset, int[] waitEvents) {
        return writeArrayHtoDAsync(bufferId, offset, length, array, hostOffset, waitEvents);
    }

    public int enqueueAsyncWrite(long bufferId, long offset, long length, double[] array, long hostOffset, int[] waitEvents) {
        return writeArrayHtoDAsync(bufferId, offset, length, array, hostOffset, waitEvents);
    }

    public void reset() {

    }

    public void sync() {
        cuStreamSynchronize(streamWrapper);
    }

    public void cleanup() {
        cuDestroyStream(streamWrapper);
    }
}
