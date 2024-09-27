package com.yanspatt.controller;

import com.yanspatt.Main;
import com.yanspatt.listener.GenericEventListener;
import com.yanspatt.listener.impl.player.AsyncPlayerConfigurationListener;
import com.yanspatt.listener.impl.PlayerPacketListener;
import com.yanspatt.listener.impl.PlayerPacketOutListener;
import com.yanspatt.listener.impl.player.PlayerDisconnectListener;
import com.yanspatt.listener.impl.player.PlayerSpawnListener;
import lombok.Getter;
import net.minestom.server.MinecraftServer;

import java.util.List;

public class EventController {

    @Getter
    private List<GenericEventListener> eventListeners;

    public EventController() {
        this.eventListeners = List.of(
                new AsyncPlayerConfigurationListener(),
                new PlayerPacketListener(),
                new PlayerPacketOutListener(),
                new PlayerSpawnListener(Main.getUserController()),
                new PlayerDisconnectListener(Main.getUserController())
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
