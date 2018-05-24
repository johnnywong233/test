package socket.demo1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Author: Johnny
 * Date: 2016/11/27
 * Time: 20:02
 * Socket通信多线程、长连接
 */
public class TalkClient {
    //http://www.osblog.net/blog/676.html
    public static void main(String args[]) throws IOException {
        Socket socket = new Socket("192.168.3.104", 8877);
        PrintWriter os = new PrintWriter(socket.getOutputStream());
        BufferedReader is = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        int i = 1;
        while (socket.isConnected()) {
            os.print("BEAT,12345678,160314,092832,1.00,123241,#" + "\n");
            os.flush();
            System.out.println("Client:" + i);
            System.out.println("Server:" + is.readLine());
            i++;
        }
        //continue while
        os.close(); //close Socket output
        is.close(); //close Socket input
        socket.close(); //close Socket
    }
}
