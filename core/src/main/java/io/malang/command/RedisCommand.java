package io.malang.command;

import io.malang.codec.Codec;
import io.netty.buffer.ByteBuf;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.CompletableFuture;


@RequiredArgsConstructor
public abstract class RedisCommand<K, O> extends CompletableFuture<O> {

    protected final Codec<K, O> codec;

    protected O output;

    abstract public ByteBuf toRedisProtocol();

    public abstract Command command();

    public abstract void complete(ByteBuf byteBuf);
}
