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
        String key = getKey();

        try (Jedis jedis = pool.getResource()) {
            String data = jedis.hget(key, username.toLowerCase());

            if (data == null) {
                return Optional.empty();
            }

            User user = gson.fromJson(data, User.class);

            return Optional.of(user);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }

    }

    public void save(User user) {
        String key = getKey();

        try (Jedis jedis = pool.getResource()) {
            // TODO convert user to single key

            jedis.hset(key, user.getUsername().toLowerCase(), gson.toJson(user));
        }

    }


    public void removeFromLocalCache(String username) {

    }

    public String getKey() {
        return "rankup01:mines:users";
    }

}
