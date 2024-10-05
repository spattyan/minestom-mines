package com.yanspatt.listener.impl.player;

import com.yanspatt.MinesServer;
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
                    event.setSpawningInstance(MinesServer.getInstance().getInstanceController().getInstanceContainer());
                    event.getPlayer().setRespawnPoint(new Pos(-62.0, 45, 11.0,-90,-0));
                })
                .build();
    }
}
