package com.yanspatt.manager;

import lombok.Getter;
import redis.clients.jedis.JedisPool;

public class RedisManager {
    @Getter
    private JedisPool pool;

    public void initConnectionPool() {
        // Initialize connection pool
        JedisPool pool = new JedisPool("localhost", 6379);

        this.pool = pool;
        // Get a connection from the pool
    }
}
