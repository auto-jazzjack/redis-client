package io.malang.command;

import io.malang.codec.Codec;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import lombok.EqualsAndHashCode;

import static io.malang.util.RedisCommandUtil.*;

@EqualsAndHashCode(callSuper = true)
public class SetRedisCommand<K, V, O> extends RedisCommand<K, O> {

    private final K key;
    private final V value;

    public SetRedisCommand(K key, V value, Codec<K, O> codec) {
        super(codec);
        this.key = key;
        this.value = value;
    }

    @Override
    public Command command() {
        return Command.SET;
    }

    @Override
    public ByteBuf toRedisProtocol() {
        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.buffer();
        byteBuf.writeByte(ARRAY);
        byteBuf.writeByte('3');
        byteBuf.writeBytes(DELIMITER);

        byteBuf.writeByte('$');
        byteBuf.writeByte('3');
        byteBuf.writeBytes(DELIMITER);

        byteBuf.writeBytes(SET);
        byteBuf.writeBytes(DELIMITER);

        writeIfString(byteBuf, this.key);
        writeIfString(byteBuf, this.value);
        return byteBuf;
    }

    @Override
    public void complete(ByteBuf byteBuf) {

        if (byteBuf.readableBytes() <= 0) {
            return;
        }

    }
}
