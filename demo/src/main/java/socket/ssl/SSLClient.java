package socket.ssl;

import javax.net.SocketFactory;
import javax.net.ssl.SSLSocketFactory;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Author: Johnny
 * Date: 2016/11/23
 * Time: 0:00
 */
public class SSLClient {

    //TODO
    //http://www.codeceo.com/article/ssl-communication-and-examples-in-java.html
    public static void main(String[] args) throws Exception {
        // Set the key store to use for validating the server cert.
        String CLIENT_KEY_STORE = "/Users/liweinan/projs/ssl/src/main/resources/META-INF/client_ks";
        System.setProperty("javax.net.ssl.trustStore", CLIENT_KEY_STORE);

        System.setProperty("javax.net.debug", "ssl,handshake");

        SSLClient client = new SSLClient();
        Socket s = client.clientWithoutCert();

        PrintWriter writer = new PrintWriter(s.getOutputStream());
        BufferedReader reader = new BufferedReader(new InputStreamReader(s
                .getInputStream()));
        writer.println("hello");
        writer.flush();
        System.out.println(reader.readLine());
        s.close();
    }

    private Socket clientWithoutCert() throws Exception {
        SocketFactory sf = SSLSocketFactory.getDefault();
        return sf.createSocket("localhost", 8443);
    }

}
