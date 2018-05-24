package socket.file.server;

import socket.file.client.ClientTcpSend;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerTcpListener implements Runnable {
    @SuppressWarnings("resource")
    /*
	 * http://www.admin10000.com/document/576.html
	 */
    public static void main(String[] args) {
        try {
            final ServerSocket server = new ServerSocket(ClientTcpSend.port);
            Thread th = new Thread(() -> {
                while (true) {
                    try {
                        System.out.println("start listening...");
                        Socket socket = server.accept();
                        System.out.println("port exists");
                        receiveFile(socket);
                    } catch (Exception e) {
                    }
                }
            });
            th.run(); //thread start running
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run() {
    }

    public static void receiveFile(Socket socket) {

        byte[] inputByte;
        int length;
        DataInputStream dis = null;
        FileOutputStream fos = null;
        try {
            try {

                dis = new DataInputStream(socket.getInputStream());
                fos = new FileOutputStream(new File("E:\\Java_ex\\test_file\\1.xml"));
                inputByte = new byte[1024 * 4];
                System.out.println("start receiving data...");
                while ((length = dis.read(inputByte, 0, inputByte.length)) > 0) {
                    fos.write(inputByte, 0, length);
                    fos.flush();
                }
                System.out.println("finish receiving...");
            } finally {
                if (fos != null)
                    fos.close();
                if (dis != null)
                    dis.close();
                if (socket != null)
                    socket.close();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
