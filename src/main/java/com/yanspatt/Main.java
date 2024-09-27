package com.yanspatt;

import com.yanspatt.benchmark.ServerBenchmark;
import com.yanspatt.controller.EventController;
import com.yanspatt.controller.InstanceController;
import com.yanspatt.redis.RedisManager;
import lombok.Getter;
import net.minestom.server.MinecraftServer;
import net.minestom.server.extras.velocity.VelocityProxy;

public class Main {

    @Getter
    private static InstanceController instanceController;

    @Getter
    private static EventController eventController;

    @Getter
    private static RedisManager redisManager;

    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        MinecraftServer minecraftServer = MinecraftServer.init();


        var port = 25565;

        if (args.length > 0) {
            if (args[0] != null && args[0].startsWith("25")) {
                port = Integer.parseInt(args[0]);
            }

            if (args[1] != null) {
                VelocityProxy.enable(args[1]);
                System.out.println("Velocity: " + args[1]);
            }
        }

        instanceController = new InstanceController();

        eventController = new EventController();
        eventController.registerEvents();

        redisManager = new RedisManager();

        redisManager.initConnectionPool();

        ServerBenchmark benchmark = new ServerBenchmark();
        benchmark.setup();

        System.out.println("Using Port: " + port);

        minecraftServer.start("0.0.0.0", port);

    }

}