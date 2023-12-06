package io.malang.codec;

import io.netty.buffer.ByteBuf;

public interface Codec<V> {

    V decode(ByteBuf byteBuf, int size);

    default ByteBuf encode(V o) {
        return null;
    }
}
