package io.malang.command;

import io.malang.codec.Codec;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import lombok.EqualsAndHashCode;

import static io.malang.util.RedisCommandUtil.*;

@EqualsAndHashCode(callSuper = true)
public class ClusterInfoRedisCommand<K, O> extends RedisCommand<K, O> {

    public ClusterInfoRedisCommand(Codec<K, O> codec) {
        super(codec);
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
    public void complete(ByteBuf byteBuf) {
        if (byteBuf.readableBytes() > 0) {
            int i = byteBuf.readableBytes();

        }
    }
}
