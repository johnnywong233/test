package socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class GetAndPost {
	/*
	 * http://blog.csdn.net/xiao__gui/article/details/16981245
	 * run this [application] first, then GetPost.jsp
	 */
	public static void main(String[] args) throws IOException {
		// this the server side, and set listening port 8081  
		try (ServerSocket serverSocket = new ServerSocket(8081)) {
			// 等待接收请求，这是一个阻塞的方法，当请求到来的时候才会继续向下执行
			Socket socket = serverSocket.accept();

			// get the request content
			InputStream is = socket.getInputStream();
			InputStreamReader reader = new InputStreamReader(is);

			// output the response
			while (true) {
				System.out.print((char) reader.read());
			}
		}
	}
}
