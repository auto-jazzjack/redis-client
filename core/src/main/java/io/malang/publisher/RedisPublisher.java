package io.malang.publisher;

import io.malang.command.RedisSubscription;
import io.malang.connection.RedisCommand;
import io.malang.connection.StatefulConnection;
import lombok.RequiredArgsConstructor;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

import java.util.function.Supplier;

/**
 * This class publish RedisCommand to Channel and get callback
 */

@RequiredArgsConstructor
public class RedisPublisher<K, V, O> implements Publisher<O> {

    private final Supplier<RedisCommand<K, ?, ? super O>> supplier;
    private final StatefulConnection<K, O> connection;
    RedisCommand<K, ?, ? super O> redisCommand;


    @Override
    @SuppressWarnings("unchecked")
    public void subscribe(Subscriber<? super O> s) {
        if (redisCommand == null) {
            redisCommand = supplier.get();
        }

        RedisSubscription<K, V, O> subscription = new RedisSubscription<>(s, (RedisCommand<K, V, O>) redisCommand, connection);
        s.onSubscribe(subscription);

    }

}
