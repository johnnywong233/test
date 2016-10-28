package mina;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.transport.socket.nio.*;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;

public class HelloServer {
	/*
	 * https://www.ibm.com/developerworks/cn/opensource/os-cn-apmina/
	 */
	private static final int PORT = 8080;
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
	        IoAcceptor acceptor = new SocketAcceptor();        
	        IoAcceptorConfig config = new SocketAcceptorConfig();
	        DefaultIoFilterChainBuilder chain = config.getFilterChain();
	        //使用字符串编码
	        chain.addLast("codec", 
	new ProtocolCodecFilter(new TextLineCodecFactory()));
	        //启动HelloServer
	        acceptor.bind(new InetSocketAddress(PORT), new HelloHandler(), config);
	        System.out.println("HelloServer started on port " + PORT);
		}
	}

	/**
	 * HelloServer的处理逻辑
	 */
	class HelloHandler extends IoHandlerAdapter {
	/**
	 * 当有异常发生时触发
	 */
	@Override
	    public void exceptionCaught(IoSession ssn, Throwable cause) {
	        cause.printStackTrace();
	        ssn.close();
	    }

	/**
	 * 有新连接时触发
	 */
	@Override
	public void sessionOpened(IoSession ssn) throws Exception {
	    System.out.println("session open for " + ssn.getRemoteAddress());
		}

	/**
	 * 连接被关闭时触发
	 */
	public void sessionClosed(IoSession ssn) throws Exception {
	    System.out.println("session closed from " + ssn.getRemoteAddress());
		}

	/**
	 * 收到来自客户端的消息
	 */
	public void messageReceived(IoSession ssn, Object msg) throws Exception {    
	    String ip = ssn.getRemoteAddress().toString();
	System.out.println("===> Message From " + ip +" : " + msg);    
	    ssn.write("Hello " + msg);
	    }

}
