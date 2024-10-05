package com.yanspatt.controller;

import com.yanspatt.Main;
import com.yanspatt.MinesServer;
import com.yanspatt.listener.GenericEventListener;
import com.yanspatt.listener.impl.player.*;
import com.yanspatt.listener.impl.PlayerPacketListener;
import com.yanspatt.listener.impl.PlayerPacketOutListener;
import lombok.Getter;
import net.minestom.server.MinecraftServer;

import java.util.List;

public class EventController {

    @Getter
    private List<GenericEventListener> eventListeners;

    public EventController(MinesServer minesServer) {
        this.eventListeners = List.of(
                new AsyncPlayerConfigurationListener(),
                new PlayerPacketListener(),
                new PlayerPacketOutListener(),
                new PlayerSpawnListener(minesServer.getUserController()),
                new PlayerDisconnectListener(minesServer.getUserController()),
                new PlayerBlockBreakListener(minesServer.getUserController()),
                new PlayerChatListener(minesServer.getUserController()),
                new PlayerInteractListener(minesServer.getUserController()),
                new PlayerStartDiggingListener(minesServer.getUserController()),
                new PlayerRenderChunkListener(minesServer.getUserController()),
                new PlayerUnloadChunkListener(minesServer.getUserController())
        );
    }

    public void registerEvents() {


        var globalEventNode = MinecraftServer.getGlobalEventHandler();
        eventListeners.forEach(listener -> {
            globalEventNode.addListener(listener.register());
            System.out.println("Registered Event: " + listener.getClass().getSimpleName());
        });
    }


}
