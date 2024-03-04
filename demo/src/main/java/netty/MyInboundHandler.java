package netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.EventLoop;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.concurrent.TimeUnit;

/**
 * @author Johnny
 * @since 2018/9/26-21:54
 * 在ChannelHandler监测连接是否断掉，断掉的话也要重连
 */
public class MyInboundHandler extends SimpleChannelInboundHandler {
    private final Client client;

    MyInboundHandler(Client client) {
        this.client = client;
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        final EventLoop eventLoop = ctx.channel().eventLoop();
        eventLoop.schedule(() -> client.createBootstrap(new Bootstrap(), eventLoop), 1L, TimeUnit.SECONDS);
        super.channelInactive(ctx);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) {

    }
}
