package benchmark.zip.compare;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Param;

import java.io.IOException;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;

/**
 * JDK java.util.zip Deflater/Inflater test
 */
public class JdkDeflateTest extends TestParent {
    @Param({"1", "2", "3", "4", "5", "6", "7", "8", "9"})
    public int level;

    //@Benchmark method should be public.
    @Benchmark
    public int deflate() throws IOException {
        return baseBenchmark(underlyingStream -> new DeflaterOutputStream(underlyingStream, new Deflater(level, true), 512));
    }
}
