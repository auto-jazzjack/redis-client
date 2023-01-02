package io.malang.handler;

import io.malang.connection.RedisCommand;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;

public class RedisConnectionHandler extends ChannelDuplexHandler {

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        if (msg instanceof RedisCommand) {
            ctx.writeAndFlush(((RedisCommand<?, ?, ?>) msg).toCommand(), promise);
        } else {
            super.write(ctx, msg, promise);
        }
    }

    @Override
    public void read(ChannelHandlerContext ctx) throws Exception {
        super.read(ctx);
    }
}
