package com.yanspatt;

import com.yanspatt.benchmark.ServerBenchmark;
import lombok.Getter;
import net.minestom.server.MinecraftServer;
import net.minestom.server.extras.velocity.VelocityProxy;
import net.minestom.server.timer.SchedulerManager;

public class Main {

    @Getter
    private static MinesServer minesServer;

    public static void main(String[] args) {
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

        });
        System.setProperty("minestom.chunk-view-distance", "8");
        System.setProperty("minestom.entity-view-distance", "8");
        minecraftServer.start("0.0.0.0", port);


    }

}