package io.malang.command;

import io.malang.connection.RedisCommand;
import io.malang.connection.StatefulConnection;
import lombok.RequiredArgsConstructor;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

@RequiredArgsConstructor
public class RedisSubscription<K, V, T> implements Subscription, Subscriber<T> {

    private final Subscriber<? super T> subscriber;
    private final RedisCommand<K, V, T> redisCommand;
    private final StatefulConnection<K, T> connection;
    private Subscription subscription;


    @Override
    public void onSubscribe(Subscription s) {
        this.subscription = s;
        this.subscriber.onSubscribe(this.subscription);
    }

    @Override
    public void onNext(T o) {
        subscriber.onNext(o);
    }

    @Override
    public void onError(Throwable t) {
        subscriber.onError(t);
    }

    @Override
    public void onComplete() {
        subscriber.onComplete();
    }

    @Override
    public void request(long n) {
        // this.subscription.request(n);
        this.connection.dispatch(redisCommand);
    }

    @Override
    public void cancel() {
        this.subscription.cancel();
    }
}