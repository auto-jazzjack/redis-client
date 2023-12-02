package io.malang.connection;

import io.malang.codec.Codec;
import io.malang.handler.RedisConnectionHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.URI;
import java.util.Objects;

public class ConnectionBuilder<K, V> {

    private Codec<K, V> codec;

    private URI uri;

    private RedisConnectionHandler handler;

    private ChannelGroup channels;

    ConnectionBuilder() {

    }

    public static <K, V> ConnectionBuilder<K, V> builder() {
        return new ConnectionBuilder<>();
    }

    public ConnectionBuilder<K, V> codec(Codec<K, V> codec) {
        this.codec = codec;
        return this;
    }

    public ConnectionBuilder<K, V> uri(URI uri) {
        this.uri = uri;
        return this;
    }

    public StatefulConnection<K, V> connect() {
        Objects.requireNonNull(uri);
        Objects.requireNonNull(codec);

        Bootstrap bootstrap = new Bootstrap();

        bootstrap.group(new NioEventLoopGroup(Runtime.getRuntime().availableProcessors()));
        bootstrap.channel(NioSocketChannel.class);
        channels = new DefaultChannelGroup(new NioEventLoopGroup().next());
        bootstrap.handler(new ChannelInitializer<>() {
            @Override
            protected void initChannel(Channel ch) {
                handler = new RedisConnectionHandler();
                channels.add(ch.pipeline().addLast(handler).channel());
            }

            @Override
            public void channelInactive(ChannelHandlerContext ctx) throws Exception {
                channels.remove(ctx.channel());
                super.channelInactive(ctx);
            }
        });
        bootstrap.connect(uri.getHost(), uri.getPort() == -1 ? 6379 : uri.getPort());
        return new StatefulConnection<>(channels, codec);
    }


}
