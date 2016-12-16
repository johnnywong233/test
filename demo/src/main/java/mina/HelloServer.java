package mina;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.apache.mina.common.*;
import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.transport.socket.SocketAcceptor;
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
	        //ʹ���ַ�������
	        chain.addLast("codec", 
	new ProtocolCodecFilter(new TextLineCodecFactory()));
	        //����HelloServer
	        acceptor.bind(new InetSocketAddress(PORT), new HelloHandler(), config);
	        System.out.println("HelloServer started on port " + PORT);
		}
	}

	/**
	 * HelloServer�Ĵ����߼�
	 */
	class HelloHandler extends IoHandlerAdapter {
	/**
	 * �����쳣����ʱ����
	 */
	@Override
	    public void exceptionCaught(IoSession ssn, Throwable cause) {
	        cause.printStackTrace();
	        ssn.close();
	    }

	/**
	 * ��������ʱ����
	 */
	@Override
	public void sessionOpened(IoSession ssn) throws Exception {
	    System.out.println("session open for " + ssn.getRemoteAddress());
		}

	/**
	 * ���ӱ��ر�ʱ����
	 */
	public void sessionClosed(IoSession ssn) throws Exception {
	    System.out.println("session closed from " + ssn.getRemoteAddress());
		}

	/**
	 * �յ����Կͻ��˵���Ϣ
	 */
	public void messageReceived(IoSession ssn, Object msg) throws Exception {    
	    String ip = ssn.getRemoteAddress().toString();
	System.out.println("===> Message From " + ip +" : " + msg);    
	    ssn.write("Hello " + msg);
	    }

}
