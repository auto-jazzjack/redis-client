package io.malang.connection;

import io.malang.codec.Codec;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.CompletableFuture;

import static io.malang.util.RedisCommandUtil.appendGet;
import static io.malang.util.RedisCommandUtil.appendSet;

@Data
@EqualsAndHashCode(callSuper = true)
@RequiredArgsConstructor
public class RedisCommand<K, V, O> extends CompletableFuture<O> {

    private final K key;
    private final V value;

    private O output;
    private final Command command;
    private final Codec<K, O> codec;

    private static final String DELIMITER = "\r\n";


    @AllArgsConstructor
    public enum Command {
        SET("set"),
        GET("get");

        private final String cmd;
    }

    public ByteBuf toCommand() {
        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer();
        try {

            switch (command) {
                case SET:
                    appendSet(buffer, this);
                    return buffer;
                case GET:
                    appendGet(buffer, this);
                    return buffer;
            }
            return null;
        } finally {
            //buffer.release();
        }
    }

    public boolean completeRaw(ByteBuf byteBuf) {

        if (byteBuf.readableBytes() <= 0) {
            return false;
        }
        byte b = byteBuf.readByte();
        switch (b) {
            case '+':
                this.output = this.codec.decodeValue(byteBuf, byteBuf.readableBytes());
                break;

            case '$':
                byte b1 = byteBuf.readByte();//length
                byteBuf.readBytes(2);//DELIMETER
                this.output = this.codec.decodeValue(byteBuf, b1 - '0');
                byteBuf.readBytes(2);//DELIMETER
                break;

        }
        this.complete(this.output);
        return true;
    }
}
