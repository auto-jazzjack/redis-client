package io.malang.connection;

import io.malang.codec.Codec;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import static io.malang.util.RedisCommandUtil.appendSet;

@Data
@RequiredArgsConstructor
public class RedisCommand<K, V, O> {

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
}
