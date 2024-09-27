package com.yanspatt.controller;

import com.yanspatt.listener.GenericEventListener;
import com.yanspatt.listener.impl.AsyncPlayerConfigurationListener;
import com.yanspatt.listener.impl.PlayerPacketListener;
import com.yanspatt.listener.impl.PlayerPacketOutListener;
import lombok.Getter;
import net.minestom.server.MinecraftServer;

import java.util.ArrayList;
import java.util.List;

public class EventController {

    @Getter
    private List<GenericEventListener> eventListeners;

    public EventController() {
        this.eventListeners = List.of(
                new AsyncPlayerConfigurationListener(),
                new PlayerPacketListener(),
                new PlayerPacketOutListener()
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
