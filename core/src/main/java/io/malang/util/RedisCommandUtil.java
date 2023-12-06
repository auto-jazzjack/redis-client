package io.malang.util;


import io.netty.buffer.ByteBuf;

import java.nio.charset.StandardCharsets;

public class RedisCommandUtil {

    public static final byte ARRAY = '*';
    public static final byte STRING = '$';
    public static final byte[] SET = "SET".getBytes(StandardCharsets.US_ASCII);
    public static final byte[] GET = "GET".getBytes(StandardCharsets.US_ASCII);
    public static final byte[] DELIMITER = {'\r', '\n'};


    public static void writeIfString(ByteBuf byteBuf, Object obj) {

        if (obj instanceof String) {
            byteBuf.writeByte(STRING);
            byteBuf.writeByte(((String) obj).length() + '0');
            byteBuf.writeBytes(DELIMITER);

            byteBuf.writeBytes(((String) obj).getBytes(StandardCharsets.US_ASCII));
            byteBuf.writeBytes(DELIMITER);
        }
    }
}
