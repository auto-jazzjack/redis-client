package io.malang.connection;

import io.malang.codec.Codec;
import io.malang.command.ClusterInfoRedisCommand;
import io.malang.command.RedisCommand;
import io.netty.channel.group.ChannelGroup;
import lombok.Getter;

import java.util.Map;


public class StatefulConnection<K, V> {

    @Getter
    private final ChannelGroup channel;
    private final Codec<K> keyCodec;
    private final Codec<V> valueCodec;

    private static final int SLOT_SIZE = 16384;

    public StatefulConnection(ChannelGroup channel, Codec<K> keyCodec, Codec<V> valueCodec) {
        this.channel = channel;
        this.keyCodec = keyCodec;
        this.valueCodec = valueCodec;
        //init(this.channel);
        //send cluster info to all channel
    }

    private void init(ChannelGroup channelSet) {

        ClusterInfoRedisCommand clusterInfo = new ClusterInfoRedisCommand();
        channelSet.writeAndFlush(clusterInfo);
        try{
            Map<String, String> v = clusterInfo.get();
            System.out.println(v);
        }catch (Exception e){
            System.out.println(e);
        }

        System.out.println();


        /*channelSet.forEach(i -> {
            ClusterInfoRedisCommand<K, Object, V> clusterInfo = new ClusterInfoRedisCommand<>(this.codec);


            i.pipeline().addLast(new R)
            try {
                V v = clusterInfo.get(1, TimeUnit.SECONDS);
                System.out.println(v);
            } catch (Exception e) {
                System.out.println(e);
            }

        });*/
        /**
         * from /
         * */

    }

    /**
     * This is method writing data to channel
     */
    public void dispatch(RedisCommand<K, V, ?> redisCommand) {
        channel.writeAndFlush(redisCommand);
    }

    public ReactorRedisConnection<K, V> reactor() {
        return new ReactorRedisConnectionImpl<>(keyCodec, valueCodec, this);
    }


}
