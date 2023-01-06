package io.malang.connection;

import io.malang.codec.Codec;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

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
        SET("set");

        private final String cmd;
    }

    public ByteBuf toCommand() {
        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer();
        switch (command) {
            case SET:
                appendSet(buffer, this);
                return buffer;
        }
        return null;
    }

    public boolean completeRaw(ByteBuf byteBuf) {

        if (byteBuf.readableBytes() <= 0) {
            return false;
        }
        byte b = byteBuf.readByte();
        switch (b) {
            case '+':
                this.output = this.codec.decodeValue(byteBuf);
                break;

        }
        this.complete(this.output);
        return true;
    }
}
