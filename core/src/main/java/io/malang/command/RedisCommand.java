package io.malang.command;

import io.malang.codec.Codec;
import io.netty.buffer.ByteBuf;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.CompletableFuture;


@RequiredArgsConstructor
public abstract class RedisCommand<K, V, O> extends CompletableFuture<O> {

    abstract public ByteBuf toRedisProtocol();

    public abstract Command command();

    public abstract void completeCommand(ByteBuf byteBuf);
}
