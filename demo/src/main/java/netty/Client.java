package netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.concurrent.TimeUnit;

/**
 * @author Johnny
 * @since 2018/9/26-21:51
 * 用Netty实现一个TCP client时，希望当连接断掉的时候Netty能够自动重连。Netty Client有两种情况下需要重连：
 * 1. Netty Client启动时需要重连
 * Netty的作者在stackoverflow上给出解决方案 <a href="https://stackoverflow.com/questions/19739054/whats-the-best-way-to-reconnect-after-connection-closed-in-netty">...</a>
 * 2. 在程序运行中连接断掉需要重连。
 * Netty的例子uptime中实现一种解决方案 <a href="https://github.com/netty/netty/blob/master/example/src/main/java/io/netty/example/uptime/UptimeClientHandler.java">...</a>
 */
public class Client {
    private final EventLoopGroup loop = new NioEventLoopGroup();

    public static void main(String[] args) {
        new Client().run();
    }

    void createBootstrap(Bootstrap bootstrap, EventLoopGroup eventLoop) {
        if (bootstrap != null) {
            final MyInboundHandler handler = new MyInboundHandler(this);
            bootstrap.group(eventLoop);
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) {
                    socketChannel.pipeline().addLast(handler);
                }
            });
            bootstrap.remoteAddress("localhost", 8888);
            bootstrap.connect().addListener(new ConnectionListener(this));
        }
    }

    public void run() {
        createBootstrap(new Bootstrap(), loop);
    }
}

/**
 * 实现ChannelFutureListener 用来启动时监测是否连接成功，不成功的话重试
 */
class ConnectionListener implements ChannelFutureListener {
    private final Client client;

    ConnectionListener(Client client) {
        this.client = client;
    }

    @Override
    public void operationComplete(ChannelFuture channelFuture) {
        if (!channelFuture.isSuccess()) {
            System.out.println("Reconnect");
            final EventLoop loop = channelFuture.channel().eventLoop();
            loop.schedule(() -> client.createBootstrap(new Bootstrap(), loop), 1L, TimeUnit.SECONDS);
        }
    }
}
