package io.malang.handler;

import io.malang.connection.RedisCommand;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;

import java.util.ArrayDeque;
import java.util.Deque;

public class RedisConnectionHandler extends ChannelDuplexHandler {


    private final Deque<RedisCommand<?, ?, ?>> queue;

    public RedisConnectionHandler() {
        queue = new ArrayDeque<>();
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        if (msg instanceof RedisCommand) {
            RedisCommand<?, ?, ?> v = (RedisCommand<?, ?, ?>) msg;
            queue.add(v);
            ctx.writeAndFlush(v.toCommand(), promise);
        } else {
            super.write(ctx, msg, promise);
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof ByteBuf) {
            if (canDecode((ByteBuf) msg)) {
                ((ByteBuf) msg).release();
            }
        } else {
            super.channelRead(ctx, msg);
        }
    }

    boolean canDecode(ByteBuf byteBuf) {
        int i = byteBuf.readableBytes();
        if (i <= 0 || this.queue.isEmpty()) {
            return false;
        }

        RedisCommand<?, ?, ?> peek = this.queue.peek();
        peek.completeRaw(byteBuf);
        this.queue.poll();


        return true;
    }

    private void decode() {

    }
}
