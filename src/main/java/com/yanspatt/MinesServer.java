package com.yanspatt;

import com.yanspatt.controller.*;
import com.yanspatt.factory.MineFactory;
import com.yanspatt.factory.PickaxeFactory;
import com.yanspatt.manager.ConfigManager;
import com.yanspatt.manager.RedisManager;
import com.yanspatt.repository.cache.UserCache;
import com.yanspatt.repository.redis.UserRedisRepository;
import com.yanspatt.scheduler.SchedulerJob;
import com.yanspatt.scheduler.ServerScheduler;
import com.yanspatt.service.EnchantmentService;
import com.yanspatt.service.UserService;
import com.yanspatt.util.inventory.InventoryManager;
import lombok.Getter;
import net.minestom.server.MinecraftServer;

@Getter
public class MinesServer {

    @Getter
    private static MinesServer instance;

    private InstanceController instanceController;
    private EventController eventController;
    private EnchantmentController enchantmentController;

    private RedisManager redisManager;
    private ConfigManager configManager;

    private UserController userController;

    private UserService userService;
    private EnchantmentService enchantmentService;

    private UserCache userCache;
    private UserRedisRepository userRedisRepository;

    private InventoryManager inventoryManager;

    private ServerScheduler scheduler;

    // Factory

    private PickaxeFactory pickaxeFactory;
    private MineFactory mineFactory;

    public MinesServer() {
        instance = this;
    }

    public void init() {

        scheduler = new ServerScheduler();
        scheduler.scheduleJobs();


        redisManager = new RedisManager();
        redisManager.initConnectionPool();

        configManager = new ConfigManager();

        // user
        userCache = new UserCache();
        userRedisRepository = new UserRedisRepository(redisManager.getPool());

        scheduler.addJob(new SchedulerJob(-1,1000, () -> {
            userCache.getCache().asMap().values().forEach(user -> {
               userRedisRepository.save(user);
            });
        }));

        userService = new UserService(userCache, userRedisRepository);

        try {
            enchantmentService = new EnchantmentService();
        } catch (Exception e) {
            MinecraftServer.stopCleanly();
            e.printStackTrace();
        }

        userController = new UserController(userService);

        pickaxeFactory = new PickaxeFactory();

        instanceController = new InstanceController();

        eventController = new EventController(this);
        eventController.registerEvents();

        inventoryManager = new InventoryManager(this);
        inventoryManager.init();

        enchantmentController = new EnchantmentController();

        this.mineFactory = new MineFactory();

    }

}
