package io.malang.connection;

import io.malang.codec.Codec;
import io.malang.command.ReactorRedisCommand;
import io.malang.command.ReactorRedisCommandImpl;
import io.netty.channel.group.ChannelGroup;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StatefulConnection<K, V> {

    private final ChannelGroup channel;
    private final Codec<K, V> codec;

    /**
     * This is method writing data to channel
     */
    public void dispatch(RedisCommand<K, ?, V> redisCommand) {
        channel.writeAndFlush(redisCommand);
        //channel.writeAndFlush(codec.encodeKey(redisCommand.getInput()));
    }

    public ReactorRedisCommand<K, V> reactor() {
        return new ReactorRedisCommandImpl<K, V>(codec, this);
    }


}
