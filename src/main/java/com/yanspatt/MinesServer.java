package com.yanspatt;

import com.yanspatt.controller.EventController;
import com.yanspatt.controller.InstanceController;
import com.yanspatt.controller.UserController;
import com.yanspatt.factory.PickaxeFactory;
import com.yanspatt.manager.RedisManager;
import com.yanspatt.repository.cache.UserCache;
import com.yanspatt.repository.redis.UserRedisRepository;
import com.yanspatt.service.UserService;
import lombok.Getter;

@Getter
public class MinesServer {

    @Getter
    private static MinesServer instance;

    private InstanceController instanceController;
    private EventController eventController;

    private RedisManager redisManager;

    private UserController userController;
    private UserService userService;
    private UserCache userCache;
    private UserRedisRepository userRedisRepository;

    // Factory

    private PickaxeFactory pickaxeFactory;

    public MinesServer() {
        instance = this;
    }

    public void init() {
        redisManager = new RedisManager();
        redisManager.initConnectionPool();

        // user
        userCache = new UserCache();
        userRedisRepository = new UserRedisRepository(redisManager.getPool());

        userService = new UserService(userCache, userRedisRepository);

        userController = new UserController(userService);

        pickaxeFactory = new PickaxeFactory();


        instanceController = new InstanceController();

        eventController = new EventController(this);
        eventController.registerEvents();
    }

}
