package io.file.zip.compare;

import org.openjdk.jmh.annotations.Benchmark;
import org.xerial.snappy.SnappyFramedOutputStream;
import org.xerial.snappy.SnappyOutputStream;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Snappy library tests
 */
public class SnappyTest extends TestParent {

    @Benchmark
    int snappyNormalOutput() throws IOException
    {
        return baseBenchmark(underlyingStream -> new SnappyOutputStream(underlyingStream, 65536));
    }

    @Benchmark
    int snappyFramedOutput() throws IOException
    {
        return baseBenchmark(new StreamFactory() {
            @Override
            public OutputStream getStream(OutputStream underlyingStream) throws IOException {
                return new SnappyFramedOutputStream( underlyingStream );
            }
        });
    }
}