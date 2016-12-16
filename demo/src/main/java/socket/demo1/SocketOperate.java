package socket.demo1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Author: Johnny
 * Date: 2016/11/27
 * Time: 20:01
 */
public class SocketOperate implements Runnable {
    private Socket socket;
    //该线程所处理的Socket所对应的输入流
    private BufferedReader br = null;
    private InputStreamReader reader = null;

    public SocketOperate(Socket socket) throws IOException {
        this.socket = socket;
        reader = new InputStreamReader(this.socket.getInputStream(), "utf-8");
        br = new BufferedReader(reader);
    }

    @Override
    public void run() {
        try {
            // 采用循环不断从Socket中读取客户端发送过来的数据
            while (true) {
                String content = readFromClient();
                System.out.println(content);
                if (content == null) {
                    break;
                }
                OutputStream os = socket.getOutputStream();
                os.write(("RES, OK,<1年2班,小明>, 123241,#" + "\n").getBytes("utf-8"));
                os.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //定义读取客户端数据的方法
    private String readFromClient() {
        try {
            return br.readLine();
        }
        //如果捕捉到异常，表明该Socket对应的客户端已经关闭
        catch (IOException e) {
            try {
                br.close();
                reader.close();
                socket.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        return null;
    }
}
