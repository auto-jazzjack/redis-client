package io.malang.connection;

import io.malang.codec.Codec;
import io.malang.command.Command;
import io.malang.command.GetRedisCommand;
import io.malang.command.SetRedisCommand;
import io.malang.publisher.RedisPublisher;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;


@AllArgsConstructor
public class ReactorRedisConnectionImpl<K, V> implements ReactorRedisConnection<K, V> {

    private final Codec<K, V> codec;
    private final StatefulConnection<K, V> connection;

    @Override
    public Mono<V> set(K key, V value) {
        return Mono.from(new RedisPublisher<>(() -> new SetRedisCommand<>(key, value, codec), connection));
    }

    @Override
    public Mono<V> get(K key) {
        return Mono.from(new RedisPublisher<>(() -> new GetRedisCommand<>(key, codec), connection));
    }
}
