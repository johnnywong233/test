package mina;

public class MinaTimeServer {
	private static final int PORT = 9123;

    public static void main(String[] args) throws IOException {
        //设置buffer
        ByteBuffer.setUseDirectBuffers(false);
        ByteBuffer.setAllocator(new SimpleByteBufferAllocator());
        //定义acceptor
        IoAcceptor acceptor = new SocketAcceptor();
        //定义config
        SocketAcceptorConfig cfg = new SocketAcceptorConfig();
        //设置config,加入filter
        cfg.getSessionConfig().setReuseAddress( true );
        cfg.getFilterChain().addLast( "logger", new LoggingFilter() );
        cfg.getFilterChain().addLast( "codec", new ProtocolCodecFilter( new TextLineCodecFactory( Charset.forName( "UTF-8" ))));
        //加入port handler cfg
        acceptor.bind( new InetSocketAddress(PORT), new TimeServerHandler(), cfg);
        System.out.println("MINA Time server started.");
    }

}
