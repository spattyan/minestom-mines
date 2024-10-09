package com.yanspatt.repository.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.yanspatt.model.user.User;
import lombok.Getter;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Getter
public class UserCache {

    private final Cache<String, User> cache;

    public UserCache() {
        this.cache = CacheBuilder.newBuilder()
                .expireAfterWrite(24, TimeUnit.HOURS)
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
        cache.invalidate(username.toLowerCase());
    }

}
