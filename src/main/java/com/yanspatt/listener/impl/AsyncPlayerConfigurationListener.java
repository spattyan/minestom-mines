package com.yanspatt.listener.impl;

import com.yanspatt.Main;
import com.yanspatt.listener.GenericEventListener;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.event.EventListener;
import net.minestom.server.event.player.AsyncPlayerConfigurationEvent;
import org.jetbrains.annotations.NotNull;

public class AsyncPlayerConfigurationListener implements GenericEventListener<AsyncPlayerConfigurationEvent> {

    @Override
    public @NotNull EventListener<AsyncPlayerConfigurationEvent> register() {
        return EventListener.builder(AsyncPlayerConfigurationEvent.class)
                .handler(event -> {
                    event.setSpawningInstance(Main.getInstanceController().getInstanceContainer());
                    event.getPlayer().setRespawnPoint(new Pos(0, 40, 0));
                })
                .build();
    }
}
