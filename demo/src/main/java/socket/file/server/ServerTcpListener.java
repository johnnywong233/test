package socket.file.server;

import lombok.extern.slf4j.Slf4j;
import socket.file.client.ClientTcpSend;

import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

@Slf4j
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
                        log.error("", e);
                    }
                }
            });
            th.start();
        } catch (Exception e) {
            log.error("", e);
        }
    }

    public static void receiveFile(Socket socket) {

        byte[] inputByte;
        int length;
        try {
            try (DataInputStream dis = new DataInputStream(socket.getInputStream()); FileOutputStream fos = new FileOutputStream("E:\\Java_ex\\test_file\\1.xml")) {
                inputByte = new byte[1024 * 4];
                System.out.println("start receiving data...");
                while ((length = dis.read(inputByte, 0, inputByte.length)) > 0) {
                    fos.write(inputByte, 0, length);
                    fos.flush();
                }
            } finally {
                if (socket != null) {
                    socket.close();
                }
            }
        } catch (Exception e) {
            log.error("receiveFile fail", e);
        }
    }

    @Override
    public void run() {
    }
}
