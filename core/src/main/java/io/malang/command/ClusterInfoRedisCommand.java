package io.malang.command;

import io.malang.util.Pair;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufUtil;
import lombok.EqualsAndHashCode;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import static io.malang.util.RedisCommandUtil.*;

@EqualsAndHashCode(callSuper = true)
public class ClusterInfoRedisCommand extends RedisCommand<Void, Void, Map<String, String>> {

    public ClusterInfoRedisCommand() {

        super();
    }

    @Override
    public Command command() {
        return Command.CLUSTER_INFO;
    }

    @Override
    public ByteBuf toRedisProtocol() {
        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer();
        buffer.writeByte(ARRAY);
        buffer.writeByte('2');
        buffer.writeBytes(DELIMITER);
        writeIfString(buffer, "cluster");
        writeIfString(buffer, "info");
        return buffer;
    }

    @Override
    public void completeCommand(ByteBuf byteBuf) {
        ByteBuf clusterInfoLength = ByteBufAllocator.DEFAULT.buffer();

        try {
            byteBuf.readByte();//$
            while (byteBuf.readableBytes() > 0) {
                byte b = byteBuf.readByte();
                if (b == '\r') {
                    byteBuf.readByte();
                    break;
                } else {
                    clusterInfoLength.writeByte(b);

                }
            }

            CharSequence charSequence = clusterInfoLength.readCharSequence(clusterInfoLength.readableBytes(), StandardCharsets.UTF_8);

            byte[] bytes = ByteBufUtil.getBytes(byteBuf, byteBuf.readerIndex(), Integer.parseInt(charSequence.toString()));

            String s = new String(bytes);
            this.complete(Arrays.stream(s.split("\r\n"))
                    .map(i -> {
                        String[] split = i.split(":");
                        return Pair.of(split[0], split[1]);
                    })
                    .collect(Collectors.toMap(Pair::getKey, Pair::getValue)));

        } catch (Exception e) {
            this.completeExceptionally(e);
        } finally {
            clusterInfoLength.release();
        }

    }
}
