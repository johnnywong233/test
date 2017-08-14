package mq.demo;

import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Author: Johnny
 * Date: 2017/7/7
 * Time: 2:13
 * 消息模拟
 */
public class MessageSimulator {
    //消息队列
    private static ArrayBlockingQueue<Message> messageQueue = new ArrayBlockingQueue<>(100);

    //http://blog.csdn.net/luoweifu/article/details/45568411
    public static void main(String[] args) {
        WindowSimulator generator = new WindowSimulator(messageQueue);
        //产生消息
        generator.GenerateMsg();

        //消息循环
        Message msg;
        while ((msg = messageQueue.poll()) != null) {
            ((MessageProcess) msg.getSource()).doMessage(msg);
        }
    }
}

class Message {
    //消息类型
    static final int KEY_MSG = 1;
    static final int MOUSE_MSG = 2;
    private static final int SYS_MSG = 3;

    private Object source;  //来源
    private int type;       //类型
    private String info;    //信息

    public Message(Object source, int type, String info) {
        super();
        this.source = source;
        this.type = type;
        this.info = info;
    }

    public Object getSource() {
        return source;
    }

    public void setSource(Object source) {
        this.source = source;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public static int getKeyMsg() {
        return KEY_MSG;
    }

    public static int getMouseMsg() {
        return MOUSE_MSG;
    }

    public static int getSysMsg() {
        return SYS_MSG;
    }
}

interface MessageProcess {
    void doMessage(Message msg);
}

/**
 * 窗口模拟类
 */
class WindowSimulator implements MessageProcess {
    private ArrayBlockingQueue<Message> msgQueue;

    WindowSimulator(ArrayBlockingQueue<Message> msgQueue) {
        this.msgQueue = msgQueue;
    }

    @SuppressWarnings("resource")
	void GenerateMsg() {
        while (true) {
            Scanner scanner = new Scanner(System.in);
            int msgType = scanner.nextInt();
            if (msgType < 0) {
                //输入负数结束循环
                break;
            }
            String msgInfo = scanner.next();
            Message msg = new Message(this, msgType, msgInfo);
            try {
                //新消息加入到队尾
                msgQueue.put(msg);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    //消息处理
    public void doMessage(Message msg) {
        switch (msg.getType()) {
            case Message.KEY_MSG:
                onKeyDown(msg);
                break;
            case Message.MOUSE_MSG:
                onMouseDown(msg);
                break;
            default:
                onSysEvent(msg);
        }
    }

    //键盘事件
    private static void onKeyDown(Message msg) {
        System.out.println("键盘事件：");
        System.out.println("type:" + msg.getType());
        System.out.println("info:" + msg.getInfo());
    }

    //鼠标事件
    private static void onMouseDown(Message msg) {
        System.out.println("鼠标事件：");
        System.out.println("type:" + msg.getType());
        System.out.println("info:" + msg.getInfo());
    }

    //操作系统产生的消息
    private static void onSysEvent(Message msg) {
        System.out.println("系统事件：");
        System.out.println("type:" + msg.getType());
        System.out.println("info:" + msg.getInfo());
    }
}