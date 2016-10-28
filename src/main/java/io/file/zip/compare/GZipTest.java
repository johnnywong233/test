package io.file.zip.compare;

import org.openjdk.jmh.annotations.Benchmark;

import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;

/**
 * JDK GZIP test
 */
public class GZipTest extends TestParent {
    @Benchmark
    int gzip() throws IOException {
        return baseBenchmark(new StreamFactory() {
            @Override
            public OutputStream getStream(OutputStream underlyingStream) throws IOException {
                return new GZIPOutputStream( underlyingStream, 65536 );
            }
        });
    }
}
