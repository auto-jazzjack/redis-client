package io.malang.publisher;

import io.malang.command.RedisSubscription;
import io.malang.connection.RedisCommand;
import io.malang.connection.StatefulConnection;
import lombok.RequiredArgsConstructor;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * This class publish RedisCommand to Channel and get callback
 */

@RequiredArgsConstructor
public class RedisPublisher<K, V, O> implements Publisher<O> {

    private final Supplier<RedisCommand<K, V, O>> supplier;
    private final StatefulConnection<K, O> connection;
    RedisCommand<K, V, O> redisCommand;


    @Override
    public void subscribe(Subscriber<? super O> actual) {
        if (redisCommand == null) {
            redisCommand = supplier.get();
        }

        RedisSubscription<K, V, O> subscription = new RedisSubscription<>(actual, redisCommand, connection);
        actual.onSubscribe(subscription);

        this.redisCommand
                .thenAccept(new Consumer<O>() {
                    @Override
                    public void accept(O o) {

                    }
                });


    }

}
