package com.yanspatt.repository.redis;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yanspatt.adapter.MineBlockAdapter;
import com.yanspatt.model.mine.Mine;
import com.yanspatt.model.mine.packetMine.MinedBlock;
import com.yanspatt.model.user.User;
import com.yanspatt.adapter.MineAdapter;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Optional;

public class UserRedisRepository {


    private Gson gson;
    private JedisPool pool;

    public UserRedisRepository(JedisPool redisPool) {
        this.pool = redisPool;
        this.gson = new GsonBuilder().registerTypeAdapter(Mine.class, new MineAdapter()).registerTypeAdapter(MinedBlock.class,new MineBlockAdapter()).create();
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
