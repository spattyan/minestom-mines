package com.yanspatt.repository.redis;

import com.google.common.base.Stopwatch;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yanspatt.model.mine.Mine;
import com.yanspatt.model.user.User;
import com.yanspatt.adapter.MineAdapter;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisException;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Slf4j
public class UserRedisRepository {


    private Gson gson;
    private JedisPool pool;

    public UserRedisRepository(JedisPool redisPool) {
        this.pool = redisPool;
        this.gson = new GsonBuilder().registerTypeAdapter(Mine.class, new MineAdapter()).create();
    }

    public Optional<User> get(String username) {
        Stopwatch stopwatch = Stopwatch.createStarted();

        try (Jedis jedis = pool.getResource()) {
            String data = jedis.hget(getKey(), username.toLowerCase());

            if (data == null) {
                return Optional.empty();
            }

            User user = gson.fromJson(data, User.class);
            stopwatch.stop();
            System.out.println("delay: " + stopwatch.elapsed(TimeUnit.MILLISECONDS) + "ms");
            return Optional.of(user);
        } catch (Exception exception) {
            log.error("error: ",exception);;
            return Optional.empty();
        }

    }

    public void save(User user) {
        try (Jedis jedis = pool.getResource()) {
            jedis.hset(getKey(), user.getName().toLowerCase(), gson.toJson(user));
        } catch (Exception exception) {
            log.error("error: ",exception);;
        }

    }


    public String getKey() {
        return "rankup01:mines:users";
    }

}
