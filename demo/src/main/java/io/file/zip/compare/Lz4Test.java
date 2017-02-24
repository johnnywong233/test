package io.file.zip.compare;

import net.jpountz.lz4.LZ4BlockOutputStream;
import net.jpountz.lz4.LZ4Compressor;
import net.jpountz.lz4.LZ4Factory;
import org.openjdk.jmh.annotations.Benchmark;

import java.io.IOException;

/**
 * Java LZ4 implementations test
 */
public class Lz4Test extends TestParent {

    @Benchmark
    int testFastNative64K() throws IOException {
        return lz4(LZ4Factory.nativeInstance().fastCompressor(), BLOCK_64K);
    }

    @Benchmark
    int testFastNative128K() throws IOException {
        int BLOCK_128K = 128 * 1024;
        return lz4(LZ4Factory.nativeInstance().fastCompressor(), BLOCK_128K);
    }

    @Benchmark
    int testFastNative32M() throws IOException {
        return lz4(LZ4Factory.nativeInstance().fastCompressor(), MAX_BLOCK_SIZE);
    }

    //Uncomment these tests if you want to see the performance of the less efficient implementations.
    //Keep in mind that you will not get extra benefits from using those. The only 2 options which affect
    //the output size are fast/high compressor and a compressor buffer size.
    //In terms of performance, you will be affected more by a change of a slower compressor rather than
    //by increasing a compressor buffer.

    @Benchmark
    int testHighNative() throws IOException {
        return lz4(LZ4Factory.nativeInstance().highCompressor(), BLOCK_64K);
    }

    @Benchmark
    public int testFastUnsafe() throws IOException {
        return lz4(LZ4Factory.unsafeInstance().fastCompressor(), BLOCK_64K);
    }

    @Benchmark
    public int testHighUnsafe() throws IOException {
        return lz4(LZ4Factory.unsafeInstance().highCompressor(), BLOCK_64K);
    }

    @Benchmark
    public int testFastSafe() throws IOException {
        return lz4(LZ4Factory.safeInstance().fastCompressor(), BLOCK_64K);
    }

    @Benchmark
    public int testHighSafe() throws IOException {
        return lz4(LZ4Factory.safeInstance().highCompressor(), BLOCK_64K);
    }

    private final int BLOCK_64K = 64 * 1024;
    private final int MAX_BLOCK_SIZE = 32 * 1024 * 1024;

    @Benchmark
    int testFastNativeDouble64K() throws IOException {
        final LZ4Compressor compressor = LZ4Factory.nativeInstance().fastCompressor();
        return baseBenchmark(underlyingStream -> new LZ4BlockOutputStream(new LZ4BlockOutputStream(underlyingStream, BLOCK_64K, compressor), BLOCK_64K, compressor));
    }

    @Benchmark
    int testFastNativeDouble32M() throws IOException {
        final LZ4Compressor compressor = LZ4Factory.nativeInstance().fastCompressor();
        return baseBenchmark(underlyingStream -> new LZ4BlockOutputStream(new LZ4BlockOutputStream(underlyingStream, MAX_BLOCK_SIZE, compressor), MAX_BLOCK_SIZE, compressor));
    }

    @Benchmark
    int testFastNativeTriple32M() throws IOException {
        final LZ4Compressor compressor = LZ4Factory.nativeInstance().fastCompressor();
        return baseBenchmark(underlyingStream -> new LZ4BlockOutputStream(new LZ4BlockOutputStream(new LZ4BlockOutputStream(underlyingStream, MAX_BLOCK_SIZE, compressor), MAX_BLOCK_SIZE, compressor), MAX_BLOCK_SIZE, compressor));
    }

    private int lz4(final LZ4Compressor compressor, final int blockSize) throws IOException {
        return baseBenchmark(underlyingStream -> new LZ4BlockOutputStream(underlyingStream, blockSize, compressor));
    }
}
