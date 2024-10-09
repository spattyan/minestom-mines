package com.yanspatt.listener.impl.player;

import com.yanspatt.controller.UserController;
import com.yanspatt.listener.GenericEventListener;
import net.minestom.server.event.EventListener;
import net.minestom.server.event.player.PlayerChatEvent;
import org.jetbrains.annotations.NotNull;

public class PlayerChatListener implements GenericEventListener<PlayerChatEvent> {

    private UserController repository;

    public PlayerChatListener(UserController repository) {
        this.repository = repository;
    }

    @Override
    public @NotNull EventListener<PlayerChatEvent> register() {
        return EventListener.builder(PlayerChatEvent.class)
                .handler(event -> {
                   repository.getUser(event.getPlayer().getUsername()).ifPresent(user -> {

                       // WIP

                    });
                })
                .build();
    }
}