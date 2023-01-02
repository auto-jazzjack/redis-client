package io.malang.command;

import io.malang.codec.Codec;
import io.malang.connection.RedisCommand;
import io.malang.connection.StatefulConnection;
import io.malang.publisher.RedisPublisher;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;


@AllArgsConstructor
public class ReactorRedisCommandImpl<K, V> implements ReactorRedisCommand<K, V> {

    private final Codec<K, V> codec;
    private final StatefulConnection<K, V> connection;

    @Override
    public Mono<V> set(K key, V value) {
        return Mono.from(new RedisPublisher<>(() -> new RedisCommand<K, Object, V>(key, value, RedisCommand.Command.SET, codec), connection));
    }
}
