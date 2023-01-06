package io.malang.command;

import io.malang.connection.RedisCommand;
import io.malang.connection.StatefulConnection;
import lombok.RequiredArgsConstructor;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

@RequiredArgsConstructor
public class RedisSubscription<K, V, T> implements Subscription, Subscriber<T> {

    private final Subscriber<? super T> down;
    private final RedisCommand<K, V, T> redisCommand;
    private final StatefulConnection<K, T> connection;
    private Subscription subscription;


    @Override
    public void onSubscribe(Subscription s) {
        this.subscription = s;
        this.down.onSubscribe(this.subscription);
    }

    @Override
    public void onNext(T o) {
        down.onNext(o);
    }

    @Override
    public void onError(Throwable t) {
        down.onError(t);
    }

    @Override
    public void onComplete() {
        down.onComplete();
    }

    @Override
    public void request(long n) {
        // this.subscription.request(n);
        this.subscription.request(n);
        this.connection.dispatch(this.redisCommand);
    }

    @Override
    public void cancel() {
        this.subscription.cancel();
        this.redisCommand.cancel(true);
    }
}