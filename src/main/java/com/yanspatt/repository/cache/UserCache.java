package com.yanspatt.repository.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.yanspatt.model.user.User;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class UserCache {

    private Cache<String, User> cache;

    public UserCache() {
        this.cache = CacheBuilder.newBuilder()
                .expireAfterWrite(10, TimeUnit.MINUTES)
                .maximumSize(1000)
                .build();
    }

    public Optional<User> getCachedUser(String username) {
        return Optional.ofNullable(cache.getIfPresent(username.toLowerCase()));
    }

    public void cacheUser(String username, User user) {
        cache.put(username.toLowerCase(), user);
    }

    public void invalidateUser(String username) {
        cache.invalidate(username);
    }

}
