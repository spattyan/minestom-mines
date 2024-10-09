package com.yanspatt.controller;

import com.yanspatt.MinesServer;
import com.yanspatt.listener.GenericEventListener;
import com.yanspatt.listener.impl.player.*;
import com.yanspatt.listener.impl.PlayerPacketListener;
import lombok.Getter;
import net.minestom.server.MinecraftServer;

import java.util.List;

public class EventController {

    @Getter
    private List<GenericEventListener> eventListeners;

    public EventController(MinesServer minesServer) {
        UserController repository = minesServer.getUserController();
        this.eventListeners = List.of(
                new AsyncPlayerConfigurationListener(),
                new PlayerPacketListener(),
                new PlayerSpawnListener(repository),
                new PlayerBlockBreakListener(repository),
                new PlayerChatListener(repository),
                new PlayerInteractListener(repository),
                new PlayerRenderChunkListener(repository)
        );
    }

    public void registerEvents() {

        var globalEventNode = MinecraftServer.getGlobalEventHandler();
        eventListeners.forEach(listener -> {
            globalEventNode.addListener(listener.register());
        });

        System.out.println("Registered Events: " + eventListeners.size());
    }


}
