package com.yanspatt.benchmark;

import com.yanspatt.Main;
import lombok.val;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.minestom.server.MinecraftServer;
import net.minestom.server.adventure.audience.Audiences;
import net.minestom.server.event.server.ServerTickMonitorEvent;
import net.minestom.server.monitoring.TickMonitor;
import net.minestom.server.timer.TaskSchedule;
import net.minestom.server.utils.MathUtils;

import java.awt.*;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

public class ServerBenchmark {

    public void setup() {
        var benchmarkManager = MinecraftServer.getBenchmarkManager();
        benchmarkManager.enable(Duration.ofMillis(500));

        var lastTick = new AtomicReference<TickMonitor>();
        var globalEventHandler = MinecraftServer.getGlobalEventHandler();

        globalEventHandler.addListener(ServerTickMonitorEvent.class, event -> {
            lastTick.set(event.getTickMonitor());
                }
        );
        UUID uuid = UUID.randomUUID();
        Map<String,String> stats = new HashMap<>();
        stats.put("server",uuid.toString());

        MinecraftServer.getSchedulerManager().scheduleTask(() ->{
            val runtime = Runtime.getRuntime();
            val tickMonitor = lastTick.get();

            val ramUsage = (runtime.totalMemory() - runtime.freeMemory()) / (1024 * 1024);

            Component component = Component.text()
                    .content("Tick Time: ")
                    .color(NamedTextColor.GREEN)
                    .append(Component.text(MathUtils.round(tickMonitor.getTickTime(), 2) + "ms"))
                    .append(Component.text(" RAM: "))
                    .append(Component.text(ramUsage))
                    .append(Component.text("mb"))
                    .build();

            Audiences.all().sendActionBar(component);

            stats.put("ram", ramUsage + "mb");
            stats.put("tickTime", MathUtils.round(tickMonitor.getTickTime(), 2) + "ms");


        }, TaskSchedule.tick(10), TaskSchedule.tick(10));



    }
}
