package com.yanspatt;

import com.yanspatt.controller.*;
import com.yanspatt.factory.MineFactory;
import com.yanspatt.factory.PickaxeFactory;
import com.yanspatt.manager.RedisManager;
import com.yanspatt.repository.cache.UserCache;
import com.yanspatt.repository.redis.UserRedisRepository;
import com.yanspatt.service.UserService;
import com.yanspatt.util.Scoreboard;
import com.yanspatt.util.inventory.InventoryManager;
import lombok.Getter;

@Getter
public class MinesServer {

    @Getter
    private static MinesServer instance;

    private InstanceController instanceController;
    private EventController eventController;
    private EnchantmentController enchantmentController;

    private RedisManager redisManager;

    private UserController userController;
    private MineController mineController;


    private UserService userService;
    private UserCache userCache;
    private UserRedisRepository userRedisRepository;

    private InventoryManager inventoryManager;

    private Scoreboard scoreboard;

    // Factory

    private PickaxeFactory pickaxeFactory;
    private MineFactory mineFactory;

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
        mineController = new MineController(userService);

        pickaxeFactory = new PickaxeFactory();


        instanceController = new InstanceController();


        eventController = new EventController(this);
        eventController.registerEvents();

        inventoryManager = new InventoryManager(this);
        inventoryManager.init();

        enchantmentController = new EnchantmentController();

        scoreboard = new Scoreboard();

        this.mineFactory = new MineFactory();
    }

}
