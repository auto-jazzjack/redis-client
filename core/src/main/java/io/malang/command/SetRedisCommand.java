package io.malang.command;

import io.malang.codec.Codec;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import lombok.EqualsAndHashCode;

import java.nio.charset.StandardCharsets;

import static io.malang.util.RedisCommandUtil.*;

@EqualsAndHashCode(callSuper = true)
public class SetRedisCommand<K, V> extends RedisCommand<K, V, String> {

    private final K key;
    private final V value;
    private final Codec<K> keyCodec;
    private final Codec<V> valueCodec;

    public SetRedisCommand(K key, V value, Codec<K> keyCodec, Codec<V> valueCodec) {
        this.keyCodec = keyCodec;
        this.valueCodec = valueCodec;
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

        //byteBuf.writeBytes(this.keyCodec.encode(key));
        writeIfString(byteBuf, key);
        writeIfString(byteBuf, value);
        //byteBuf.writeBytes(this.valueCodec.encode(value));
        return byteBuf;
    }

    @Override
    public void completeCommand(ByteBuf byteBuf) {

        if (byteBuf.readableBytes() <= 0) {
            complete(null);
        } else {
            complete(String.valueOf(byteBuf.readCharSequence(byteBuf.readableBytes(), StandardCharsets.UTF_8)));
        }

    }
}
