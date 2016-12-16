package mina;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;

public class MinaTimeServer {
	private static final int PORT = 9123;

    public static void main(String[] args) throws IOException {
        //����buffer
        ByteBuffer.setUseDirectBuffers(false);
        ByteBuffer.setAllocator(new SimpleByteBufferAllocator());
        //����acceptor
        IoAcceptor acceptor = new SocketAcceptor();
        //����config
        SocketAcceptorConfig cfg = new SocketAcceptorConfig();
        //����config,����filter
        cfg.getSessionConfig().setReuseAddress( true );
        cfg.getFilterChain().addLast( "logger", new LoggingFilter() );
        cfg.getFilterChain().addLast( "codec", new ProtocolCodecFilter( new TextLineCodecFactory( Charset.forName( "UTF-8" ))));
        //����port handler cfg
        acceptor.bind( new InetSocketAddress(PORT), new TimeServerHandler(), cfg);
        System.out.println("MINA Time server started.");
    }

}
