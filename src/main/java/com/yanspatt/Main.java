package com.yanspatt;

import com.yanspatt.benchmark.ServerBenchmark;
import com.yanspatt.controller.EventController;
import com.yanspatt.controller.InstanceController;
import com.yanspatt.controller.UserController;
import com.yanspatt.manager.RedisManager;
import com.yanspatt.model.user.User;
import com.yanspatt.repository.cache.UserCache;
import com.yanspatt.repository.redis.UserRedisRepository;
import com.yanspatt.service.UserService;
import lombok.Getter;
import net.minestom.server.MinecraftServer;
import net.minestom.server.extras.MojangAuth;
import net.minestom.server.extras.velocity.VelocityProxy;
import net.minestom.server.network.packet.server.common.TransferPacket;
import net.minestom.server.timer.SchedulerManager;

public class Main {

    @Getter
    private static MinesServer minesServer;

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

        minesServer = new MinesServer();
        minesServer.init();

        ServerBenchmark benchmark = new ServerBenchmark();
        benchmark.setup();

        System.out.println("Using Port: " + port);

        SchedulerManager scheduler = MinecraftServer.getSchedulerManager();
        scheduler.buildShutdownTask(() -> {
            minesServer.getUserCache().getCache().asMap().forEach((key,value) -> {
                minesServer.getUserController().saveUser(value);
            });
        });

        minecraftServer.start("0.0.0.0", port);

    }

}