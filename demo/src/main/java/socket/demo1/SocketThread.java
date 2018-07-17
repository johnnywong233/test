package socket.demo1;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Author: Johnny
 * Date: 2016/11/27
 * Time: 20:00
 */
public class SocketThread extends Thread {
    private ServerSocket serverSocket = null;

    SocketThread(ServerSocket serverScoket) {
        try {
            if (null == serverSocket) {
                this.serverSocket = new ServerSocket(8877);
                System.out.println("socket start");
            }
        } catch (Exception e) {
            System.out.println("SocketThread create socket service error.....");
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                if (serverSocket == null) {
                    break;
                } else if (serverSocket.isClosed()) {
                    break;
                }
                Socket socket = serverSocket.accept();
                if (null != socket && !socket.isClosed()) {
                    //处理接受的数据
                    Thread t1 = new Thread(new SocketOperate(socket));
                    t1.start();
                } else {
                    break;
                }
            } catch (Exception e) {
                System.out.println("SocketThread create socket service error.");
                e.printStackTrace();
            }
        }
    }

    void closeSocketServer() {
        try {
            if (null != serverSocket && !serverSocket.isClosed()) {
                serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
