package benchmark.zip.compare;

import org.openjdk.jmh.annotations.Benchmark;
import org.xerial.snappy.SnappyFramedOutputStream;
import org.xerial.snappy.SnappyOutputStream;

import java.io.IOException;

/**
 * Snappy library tests
 */
public class SnappyTest extends TestParent {

    @Benchmark
    public int snappyNormalOutput() throws IOException {
        return baseBenchmark(underlyingStream -> new SnappyOutputStream(underlyingStream, 65536));
    }

    @Benchmark
    public int snappyFramedOutput() throws IOException {
        return baseBenchmark(SnappyFramedOutputStream::new);
    }
}