package com.yanspatt.listener.impl.player;

import com.yanspatt.controller.UserController;
import com.yanspatt.listener.GenericEventListener;
import com.yanspatt.model.user.User;
import net.minestom.server.event.EventListener;
import net.minestom.server.event.player.PlayerSpawnEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class PlayerSpawnListener implements GenericEventListener<PlayerSpawnEvent> {

    private UserController userController;

    public PlayerSpawnListener(UserController userController) {
        this.userController = userController;
    }

    @Override
    public @NotNull EventListener<PlayerSpawnEvent> register() {
        return EventListener.builder(PlayerSpawnEvent.class)
                .handler(event -> {

                    Optional<User> user = userController.getUser(event.getPlayer().getUsername());

                    if (user.isPresent()) {
                        event.getPlayer().sendMessage("Welcome back, " + event.getPlayer().getUsername());
                    } else {
                        event.getPlayer().sendMessage("Welcome, " + event.getPlayer().getUsername());
                    }
                })
                .build();
    }
}
