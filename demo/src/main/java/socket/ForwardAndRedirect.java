package socket;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class ForwardAndRedirect {
    public static void main(String[] args) throws Exception{
        // this is the client side, request port 8080
		try (Socket socket = new Socket("localhost", 8080)) {
			// request
			OutputStream out = socket.getOutputStream();
			PrintWriter pw = new PrintWriter(out);
			pw.println("GET /johnny/ForwardAndRedirect.jsp HTTP/1.1");  // Request-Line, make sure the URL(/Test/ForwardAndRedirect.jsp) is right
			pw.println("Host: localhost:8080");  // request header, Host is must
			pw.println();  // end the request, must have
			pw.flush();  // submit the request

			// get the server response
			InputStream is = socket.getInputStream();
			InputStreamReader reader = new InputStreamReader(is);

			// output the response content
			while (true) {
				System.out.print((char) reader.read());
			}
		}
    }
}
