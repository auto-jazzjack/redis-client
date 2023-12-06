package io.malang;

import io.malang.codec.Codec;
import io.malang.connection.ConnectionBuilder;
import io.malang.connection.StatefulConnection;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import org.junit.Test;

import java.net.URI;
import java.nio.charset.StandardCharsets;

public class TestSample {

    @Test
    public void start() {
        System.out.println();
        Codec<String> codec = new Codec<>() {
            @Override
            public String decode(ByteBuf byteBuf, int size) {
                int limit = Math.min(size, byteBuf.readableBytes());
                byte[] v = new byte[limit];

                for (int i = 0; i < limit; i++) {
                    v[i] = byteBuf.readByte();
                }

                return new String(v);
            }

            @Override
            public ByteBuf encode(String o) {
                ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer(o.length());

                try {
                    buffer.writeCharSequence(o, StandardCharsets.US_ASCII);
                    System.out.println();
                    return buffer;
                } catch (Exception e) {
                    System.out.println(e);
                    return null;
                } finally {
                    buffer.release();
                }
            }
        };

        StatefulConnection<String, String> connect = ConnectionBuilder.<String, String>builder()
                .keyCodec(codec)
                .valueCodec(codec)
                .uri(URI.create("redis://127.0.0.1"))
                .connect();

        for (int i = 0; i < 100; i++) {
            String hello = connect.reactor()
                    .set(i + "", i + "")
                    .block();

            String hello1 = connect.reactor()
                    .get(i + "")
                    .block();

            //System.out.println(hello);
            System.out.println(hello1);
        }


    }
}
