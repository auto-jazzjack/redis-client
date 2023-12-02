package io.malang.command;

import io.malang.connection.StatefulConnection;
import lombok.RequiredArgsConstructor;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

@RequiredArgsConstructor
public class RedisSubscription<K, V, T> implements Subscription, Subscriber<T> {

    private final Subscriber<? super T> actual;
    private final RedisCommand<K, T> redisCommand;
    private final StatefulConnection<K, T> connection;


    @Override
    public void onSubscribe(Subscription s) {
        this.actual.onSubscribe(s);
    }

    @Override
    public void onNext(T o) {
        actual.onNext(o);
    }

    @Override
    public void onError(Throwable t) {
        actual.onError(t);
    }

    @Override
    public void onComplete() {
        actual.onComplete();
    }

    @Override
    public void request(long n) {
        this.connection.dispatch(this.redisCommand);
        this.redisCommand
                .thenAccept(t -> {
                    onNext(t);
                    onComplete();
                });

    }

    @Override
    public void cancel() {
        this.redisCommand.cancel(true);
    }
}