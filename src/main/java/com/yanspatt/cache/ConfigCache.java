package com.yanspatt.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConfigCache<T> {
    private final Map<String, T> cache = new ConcurrentHashMap<>();

    public void put(String key, T value) {
        cache.put(key, value);
    }

    public T get(String key) {
        return cache.get(key);
    }

    public void clear() {
        cache.clear();
    }
}
