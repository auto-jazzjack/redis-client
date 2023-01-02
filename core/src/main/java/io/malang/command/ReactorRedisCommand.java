package io.malang.command;

import reactor.core.publisher.Mono;


public interface ReactorRedisCommand<K, V> {
    Mono<V> set(K key, V value);
}
