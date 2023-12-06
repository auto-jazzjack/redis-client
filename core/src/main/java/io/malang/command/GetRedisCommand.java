package io.malang.command;

import io.malang.codec.Codec;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import lombok.EqualsAndHashCode;

import static io.malang.util.RedisCommandUtil.*;

@EqualsAndHashCode(callSuper = true)
public class GetRedisCommand<K, O> extends RedisCommand<K, O, O> {

    private final K key;
    private final Codec<O>codec;

    public GetRedisCommand(K key, Codec<O> codec) {
        this.key = key;
        this.codec = codec;
    }

    @Override
    public Command command() {
        return Command.GET;
    }

    @Override
    public ByteBuf toRedisProtocol() {
        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.buffer();
        byteBuf.writeByte(ARRAY);
        byteBuf.writeByte('2');
        byteBuf.writeBytes(DELIMITER);

        byteBuf.writeByte('$');
        byteBuf.writeByte('3');
        byteBuf.writeBytes(DELIMITER);

        byteBuf.writeBytes(GET);
        byteBuf.writeBytes(DELIMITER);

        writeIfString(byteBuf, this.key);
        return byteBuf;
    }

    @Override
    public void completeCommand(ByteBuf byteBuf) {

        if (byteBuf.readableBytes() <= 0) {
            return;
        }

        O output = null;
        byte b = byteBuf.readByte();
        switch (b) {
            case '+':
                output = codec.decode(byteBuf, byteBuf.readableBytes());
                break;

            case '$':
                byte b1 = byteBuf.readByte();//length
                byteBuf.readBytes(2);//DELIMITER
                output = this.codec.decode(byteBuf, b1 - '0');
                byteBuf.readBytes(2);//DELIMITER
                break;

        }
        this.complete(output);
    }
}
