package com.yanspatt.repository.redis;

import com.google.gson.Gson;
import com.yanspatt.model.pickaxe.Pickaxe;
import com.yanspatt.model.user.User;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;

import java.util.Map;
import java.util.Optional;

public class UserRedisRepository {


    private Gson gson;
    private JedisPool pool;

    public UserRedisRepository(JedisPool redisPool) {
        this.pool = redisPool;
        this.gson = new Gson();
    }

    public Optional<User> get(String username) {
        String key = getKey(username);

        try (Jedis jedis = pool.getResource()) {
            Map<String, String> entries = jedis.hgetAll(key);

            if (entries.isEmpty()) {
                return Optional.empty();
            }

            User user = gson.fromJson(entries.get(username), User.class);

            return Optional.of(user);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }

    }

    public void save(User user) {
        String key = getKey(user.getUsername());

        try (Jedis jedis = pool.getResource()) {
            // TODO convert user to single key

            jedis.hset(key, user.getUsername().toLowerCase(), gson.toJson(user));


            Transaction transaction = jedis.multi();
            transaction.hset(key, "username", user.getUsername());
            transaction.hset(key, "blocksMined", String.valueOf(user.getBlocksMined()));
            transaction.hset(key, "tokens", String.valueOf(user.getTokens()));
            transaction.hset(key, "level", String.valueOf(user.getLevel()));
            transaction.hset(key, "pickaxe", gson.toJson(user.getPickaxe()));

            transaction.exec();
        }

    }


    public void removeFromLocalCache(String username) {

    }

    public String getKey(String username) {
        return "rankup01:mines:users";
    }

}
