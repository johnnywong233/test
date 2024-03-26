package socket.nio;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by johnny on 2016/10/10.
 * this is the
 */
public class Client {
    private SocketChannel client;
    private final Selector selector = getSelector();

    private volatile boolean run = true;

    private final List<Object> messageQueue = new LinkedList<>();

    private Selector getSelector() {
        try {
            return Selector.open();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Client() {
        try {
            client = SocketChannel.open();
            client.configureBlocking(false);
            client.connect(new InetSocketAddress(InetAddress.getLocalHost(), 5555));
            client.register(selector, SelectionKey.OP_CONNECT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        new Thread(() -> {
            while (run) {
                try {
                    if (selector.select(20) == 0) {
                        continue;
                    }
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()) {
                        SelectionKey selectionKey = iterator.next();
                        iterator.remove();
                        if (selectionKey.isConnectable()) {
                            SocketChannel sc = (SocketChannel) selectionKey.channel();
                            sc.finishConnect();
                            sc.register(selector, SelectionKey.OP_READ);
                        } else if (selectionKey.isWritable()) {
                            selectionKey.interestOps(SelectionKey.OP_READ);
                            Object requestMessage = selectionKey.attachment();
                            SocketChannel writeSocketChannel = (SocketChannel) selectionKey.channel();
                            byte[] requestByteData = null;
                            if (requestMessage instanceof byte[]) {
                                requestByteData = (byte[]) requestMessage;
                            } else if (requestMessage instanceof String) {
                                requestByteData = ((String) requestMessage).getBytes();
                                System.out.println("client send Message:[" + requestMessage + "]");
                            } else {
                                System.out.println("unsupport send Message Type" + requestMessage.getClass());
                            }
                            System.out.println("requestMessage:" + requestMessage);
                            if (requestByteData != null && requestByteData.length > 0) {
                                try {
                                    writeSocketChannel.write(ByteBuffer.wrap(requestByteData));
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        } else if (selectionKey.isReadable()) {
                            SocketChannel readSocketChannel = (SocketChannel) selectionKey.channel();
                            ByteBuffer tmp = ByteBuffer.allocate(1024);
                            int len;
                            byte[] data = new byte[0];
                            if ((len = readSocketChannel.read(tmp)) > 0) {
                                data = Arrays.copyOf(data, data.length + len);
                                System.arraycopy(tmp.array(), 0, data, data.length - len, len);
                                tmp.rewind();
                            }
                            if (data.length > 0) {
                                System.out.println("客户端接收到数据:[" + new String(data) + "]");
                            }
                        }
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                    close();
                }
            }
        }).start();
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            SelectionKey selectionKey = client.keyFor(selector);
            selectionKey.cancel();
            client.close();
            run = false;
        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    private void writeData(String data) {
        messageQueue.add(data);
        while (messageQueue.size() > 0) {
            Object firstSendData = messageQueue.remove(0);
            try {
                client.register(selector, SelectionKey.OP_WRITE, firstSendData);
            } catch (ClosedChannelException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(40);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Client client = new Client();
        long t1 = System.currentTimeMillis();
        for (int i = 10; i < 200; i++) {
            client.writeData(i + "hi there" + "bye and night" + i);
        }
        long t2 = System.currentTimeMillis();
        System.out.println("总共耗时：" + (t2 - t1) + "ms");
        client.close();
    }
}