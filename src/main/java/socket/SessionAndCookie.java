package socket;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class SessionAndCookie {
	//run CookieTest.jsp first, then this application.
    public static void main(String args[]) throws Exception{
        Socket socket = new Socket("localhost", 8080);
        try {
            OutputStream out = socket.getOutputStream();
            PrintWriter pw = new PrintWriter(out);
//            pw.println("GET /johnny/CookieTest.jsp HTTP/1.1");
            
            pw.println("GET /johnny/SessionTest.jsp HTTP/1.1");
            // try with/with this session ID
            pw.println("Cookie: JSESSIONID=767ED5E72FDF20DE7158BE0F96E57130");
            
            pw.println("Host: localhost:8080");
            pw.println();
            pw.flush();

            InputStream is = socket.getInputStream();
            InputStreamReader reader = new InputStreamReader(is);

            while (true) {
                System.out.print((char)reader.read());
            }
        } finally {
            if(socket != null)
                socket.close();
        }
    }
}
