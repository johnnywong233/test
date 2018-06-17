package benchmark.zip.compare;

import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;

/**
 * Just to set all annotations in one place
 */
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
@Fork(1)
@Warmup(iterations = 2)
@Measurement(iterations = 3)
@BenchmarkMode(Mode.SingleShotTime)
public class TestParent {
    //uncomment the following line to run file size tests
    private Path m_inputFile = InputGenerator.FILE_PATH.toPath();

    @Setup
    public void setup() {
        m_inputFile = InputGenerator.FILE_PATH.toPath();
    }

    interface StreamFactory {
        OutputStream getStream(final OutputStream underlyingStream) throws IOException;
    }

    int baseBenchmark(final StreamFactory factory) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream((int) m_inputFile.toFile().length());
        try (OutputStream os = factory.getStream(bos)) {
            Files.copy(m_inputFile, os);
        }
        return bos.size();
    }

    //http://www.codeceo.com/article/java-compress-performance.html
    public static void main(String[] args) throws IOException {
        System.out.println("GZIP;" + new GZipTest().gzip());
        System.out.println("Snappy (normal);" + new SnappyTest().snappyNormalOutput());
        System.out.println("Snappy (framed);" + new SnappyTest().snappyFramedOutput());
        System.out.println("LZ4 (fast 64K);" + new Lz4Test().testFastNative64K());
        System.out.println("LZ4 (fast 128K);" + new Lz4Test().testFastNative128K());
        System.out.println("LZ4 (fast 32M);" + new Lz4Test().testFastNative32M());
        System.out.println("LZ4 (fast double 64K);" + new Lz4Test().testFastNativeDouble64K());
        System.out.println("LZ4 (fast double 32M);" + new Lz4Test().testFastNativeDouble32M());
        System.out.println("LZ4 (fast triple 32M);" + new Lz4Test().testFastNativeTriple32M());
        System.out.println("LZ4 (high);" + new Lz4Test().testHighNative());
        for (int i = 1; i <= 9; ++i) {
            JdkDeflateTest test = new JdkDeflateTest();
            test.m_lvl = i;
            System.out.println("Deflate (lvl=" + i + ");" + test.deflate());
        }
    }


}