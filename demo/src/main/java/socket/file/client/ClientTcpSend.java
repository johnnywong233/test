package socket.file.client;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ClientTcpSend {
	private static final String CLIENT_IP = "127.0.0.1";
    public static int port = 33456;
 
    public static void main(String[] args) {
        int length;
        byte[] sendBytes;
        Socket socket = null;
        DataOutputStream dos = null;
        FileInputStream fis = null;
        try {
            try {
                socket = new Socket();
                socket.connect(new InetSocketAddress(CLIENT_IP, port),30 * 1000);
                dos = new DataOutputStream(socket.getOutputStream());
                File file = new File("E:\\Java_ex\\test_file\\11.xml");
                fis = new FileInputStream(file);
                sendBytes = new byte[1024*4];
                while ((length = fis.read(sendBytes, 0, sendBytes.length)) > 0) {
                    dos.write(sendBytes, 0, length);
                    dos.flush();
                }
            } finally {
                if (dos != null) {
                    dos.close();
                }
                if (fis != null) {
                    fis.close();
                }
                if (socket != null) {
                    socket.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
