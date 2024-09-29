package com.yanspatt.service;

import com.yanspatt.repository.cache.UserCache;
import com.yanspatt.model.user.User;
import com.yanspatt.repository.redis.UserRedisRepository;

import java.util.Optional;

public class UserService {

    private final UserCache userCache;
    private final UserRedisRepository userRedisRepository;

    public UserService(UserCache userCache, UserRedisRepository userRedisRepository) {
        this.userCache = userCache;
        this.userRedisRepository = userRedisRepository;
    }

    public Optional<User> getUser(String username) {
        Optional<User> cachedUser = userCache.getCachedUser(username);
        if (cachedUser.isPresent()) {
            return cachedUser;
        }

        Optional<User> redisUser = userRedisRepository.get(username);
        redisUser.ifPresent(user -> userCache.cacheUser(username, user));

        return redisUser;
    }

    public void saveUser(User user) {
        userRedisRepository.save(user);
        userCache.invalidateUser(user.getUsername());
    }

}
