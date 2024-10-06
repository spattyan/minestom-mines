package com.yanspatt;

import com.yanspatt.benchmark.ServerBenchmark;
import com.yanspatt.loader.EnchantmentConfigLoader;
import lombok.Getter;
import net.minestom.server.MinecraftServer;
import net.minestom.server.extras.velocity.VelocityProxy;
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

        });
        System.setProperty("minestom.chunk-view-distance", "8");
        System.setProperty("minestom.entity-view-distance", "8");
        minecraftServer.start("0.0.0.0", port);


    }

    public static long encodeBlock(int chunkX, int chunkZ, int section, int x, int y, int z) {
        return (((long) chunkX & 0xFFFF) << 48) |  // 16 bits para chunkX
                (((long) chunkZ & 0xFFFF) << 32) |  // 16 bits para chunkZ
                (((long) section & 0xFF) << 24) |   // 8 bits para section
                (((long) x & 0xF) << 20) |         // 4 bits para x
                (((long) z & 0xF) << 16) |         // 4 bits para z
                (((long) y & 0xFF) << 8);           // 8 bits para y
    }

}