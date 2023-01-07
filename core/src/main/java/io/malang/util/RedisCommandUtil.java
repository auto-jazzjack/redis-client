package io.malang.util;

import io.malang.connection.RedisCommand;
import io.netty.buffer.ByteBuf;

import java.nio.charset.StandardCharsets;

public class RedisCommandUtil {

    private static final byte ARRAY = '*';
    private static final byte STRING = '$';
    private static final byte[] SET = "SET".getBytes(StandardCharsets.US_ASCII);
    private static final byte[] GET = "GET".getBytes(StandardCharsets.US_ASCII);
    private static final byte[] DELIMITER = {'\r', '\n'};

    public static <K, V, O> void appendGet(ByteBuf byteBuf, RedisCommand<K, V, O> command) {
        byteBuf.writeByte(ARRAY);
        byteBuf.writeByte('2');
        byteBuf.writeBytes(DELIMITER);

        byteBuf.writeByte('$');
        byteBuf.writeByte('3');
        byteBuf.writeBytes(DELIMITER);

        byteBuf.writeBytes(GET);
        byteBuf.writeBytes(DELIMITER);

        writeIfString(byteBuf, command.getKey());
    }

    public static <K, V, O> void appendSet(ByteBuf byteBuf, RedisCommand<K, V, O> command) {
        byteBuf.writeByte(ARRAY);
        byteBuf.writeByte('3');
        byteBuf.writeBytes(DELIMITER);

        byteBuf.writeByte('$');
        byteBuf.writeByte('3');
        byteBuf.writeBytes(DELIMITER);

        byteBuf.writeBytes(SET);
        byteBuf.writeBytes(DELIMITER);

        writeIfString(byteBuf, command.getKey());
        writeIfString(byteBuf, command.getValue());
    }

    private static void writeIfString(ByteBuf byteBuf, Object obj) {

        if (obj instanceof String) {
            byteBuf.writeByte(STRING);
            byteBuf.writeByte(((String) obj).length() + '0');
            byteBuf.writeBytes(DELIMITER);

            byteBuf.writeBytes(((String) obj).getBytes(StandardCharsets.US_ASCII));
            byteBuf.writeBytes(DELIMITER);
        }
    }
}
