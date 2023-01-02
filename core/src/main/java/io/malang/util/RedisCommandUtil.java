package io.malang.util;

import io.malang.connection.RedisCommand;
import io.netty.buffer.ByteBuf;

import java.nio.charset.StandardCharsets;

public class RedisCommandUtil {

    private static final byte ARRAY = '*';
    private static final byte STRING = '$';
    private static final byte[] SET = "SET".getBytes(StandardCharsets.US_ASCII);
    private static final byte[] DELIMITER = {'\r', '\n'};

    public static <K, V, O> void appendSet(ByteBuf byteBuf, RedisCommand<K, V, O> command) {
        byteBuf.writeByte(ARRAY);
        byteBuf.writeByte('3');
        byteBuf.writeBytes(DELIMITER);

        byteBuf.writeByte('$');
        byteBuf.writeByte('3');
        byteBuf.writeBytes(DELIMITER);

        byteBuf.writeBytes(SET);
        byteBuf.writeBytes(DELIMITER);

        if (command.getKey() instanceof String) {
            byteBuf.writeByte(STRING);
            byteBuf.writeByte(((String) command.getKey()).length() + '0');
            byteBuf.writeBytes(DELIMITER);

            byteBuf.writeBytes(((String) command.getKey()).getBytes(StandardCharsets.US_ASCII));
            byteBuf.writeBytes(DELIMITER);
        }

        if (command.getValue() instanceof String) {
            byteBuf.writeByte(STRING);
            byteBuf.writeByte(((String) command.getValue()).length() + '0');
            byteBuf.writeBytes(DELIMITER);

            byteBuf.writeBytes(((String) command.getValue()).getBytes(StandardCharsets.US_ASCII));
            byteBuf.writeBytes(DELIMITER);
        }

    }
}
