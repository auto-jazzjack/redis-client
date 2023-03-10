package io.malang.util;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Pair<K, V> {
    private K key;
    private V value;

    public static <K, V> Pair<K, V> of(K key, V value) {
        return new Pair<>(key, value);
    }
}
