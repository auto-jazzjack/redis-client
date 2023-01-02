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

        StatefulConnection<String, String> connect = ConnectionBuilder.<String, String>builder()
                .codec(new Codec<>() {
                    @Override
                    public String decodeValue(ByteBuf byteBuf) {
                        byte[] v = new byte[byteBuf.readableBytes()];
                        for (int i = 0; i < v.length; i++) {
                            v[i] = byteBuf.readByte();
                        }
                        return new String(v);
                    }

                    @Override
                    public ByteBuf encodeValue(String o) {
                        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer(o.length());
                        buffer.writeCharSequence(o, StandardCharsets.UTF_8);
                        return buffer;
                    }

                    @Override
                    public String decodeVKey(ByteBuf byteBuf) {
                        byte[] v = new byte[byteBuf.readableBytes()];
                        for (int i = 0; i < v.length; i++) {
                            v[i] = byteBuf.readByte();
                        }
                        return new String(v);
                    }

                    @Override
                    public ByteBuf encodeKey(String o) {
                        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer(o.length());
                        buffer.writeCharSequence(o, StandardCharsets.UTF_8);
                        return buffer;
                    }
                })
                .uri(URI.create("redis://127.0.0.1"))
                .connect();
        String hello = connect.reactor()
                .set("hello", "asd")
                .block();

        System.out.println(hello);


    }
}
