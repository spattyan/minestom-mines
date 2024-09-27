package com.yanspatt.listener.impl.player;

import com.yanspatt.controller.UserController;
import com.yanspatt.listener.GenericEventListener;
import com.yanspatt.model.user.User;
import net.minestom.server.event.EventListener;
import net.minestom.server.event.player.PlayerDisconnectEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class PlayerDisconnectListener  implements GenericEventListener<PlayerDisconnectEvent> {

    private UserController userController;

    public PlayerDisconnectListener(UserController userController) {
        this.userController = userController;
    }

    @Override
    public @NotNull EventListener<PlayerDisconnectEvent> register() {
        return EventListener.builder(PlayerDisconnectEvent.class)
                .handler(event -> {
                    Optional<User> user = userController.getUser(event.getPlayer().getUsername());

                    user.ifPresent(value -> userController.saveUser(value));
                })
                .build();
    }
}