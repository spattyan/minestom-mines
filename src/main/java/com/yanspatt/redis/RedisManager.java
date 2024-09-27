package com.yanspatt.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisManager {

    public void initConnectionPool() {
        // Initialize connection pool
        JedisPool pool = new JedisPool("31.220.99.137", 6379);

        // Get a connection from the pool
        Jedis jedis = pool.getResource();

        jedis.set("server", "mines-01");


        System.out.println(jedis.get("server"));

    }
}
