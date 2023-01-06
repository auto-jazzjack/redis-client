package io.malang.codec;

import io.netty.buffer.ByteBuf;

public interface Codec<K, V> {

    V decodeValue(ByteBuf byteBuf);

    ByteBuf encodeValue(V o);

    K decodeKey(ByteBuf byteBuf);

    ByteBuf encodeKey(K o);
}
