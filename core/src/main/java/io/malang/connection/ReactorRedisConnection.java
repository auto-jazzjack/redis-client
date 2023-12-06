package io.malang.connection;

import reactor.core.publisher.Mono;


public interface ReactorRedisConnection<K, V> {
    Mono<String> set(K key, V value);

    Mono<V> get(K key);
}
