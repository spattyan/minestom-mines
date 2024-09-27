package com.yanspatt.repository.redis;

import com.yanspatt.model.user.User;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;

import java.util.Map;
import java.util.Optional;

public class UserRedisRepository {

    private JedisPool pool;

    public UserRedisRepository(JedisPool redisPool) {
        this.pool = redisPool;
    }

    public Optional<User> get(String username) {
        String key = getKey(username);

        try (Jedis jedis = pool.getResource()) {
            Map<String, String> entries = jedis.hgetAll(key);

            if (entries.isEmpty()) {
                return Optional.empty();
            }

            User user = new User(entries.get("username"));
            user.setBlocksMined(Integer.parseInt(entries.get("blocksMined")));
            user.setTokens(Integer.parseInt(entries.get("tokens")));
            user.setLevel(Integer.parseInt(entries.get("level")));


            return Optional.of(user);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }

    }

    public void save(User user) {
        String key = getKey(user.getUsername());

        try (Jedis jedis = pool.getResource()) {

            Transaction transaction = jedis.multi();
            transaction.hset(key, "username", user.getUsername());
            transaction.hset(key, "blocksMined", String.valueOf(user.getBlocksMined()));
            transaction.hset(key, "tokens", String.valueOf(user.getTokens()));
            transaction.hset(key, "level", String.valueOf(user.getLevel()));
            transaction.exec();
        }

    }


    public void removeFromLocalCache(String username) {

    }

    public String getKey(String username) {
        return "user:" + username.toLowerCase() +":mines:data";
    }

}
