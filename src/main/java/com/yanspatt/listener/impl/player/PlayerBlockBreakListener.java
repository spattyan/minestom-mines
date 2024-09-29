package com.yanspatt.listener.impl.player;

import com.yanspatt.MinesServer;
import com.yanspatt.controller.UserController;
import com.yanspatt.listener.GenericEventListener;
import com.yanspatt.model.user.User;
import net.minestom.server.event.EventListener;
import net.minestom.server.event.player.PlayerBlockBreakEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class PlayerBlockBreakListener implements GenericEventListener<PlayerBlockBreakEvent> {

    private UserController userController;

    public PlayerBlockBreakListener(UserController userController) {
        this.userController = userController;
    }

    @Override
    public @NotNull EventListener<PlayerBlockBreakEvent> register() {
        return EventListener.builder(PlayerBlockBreakEvent.class)
                .handler(event -> {
                    Optional<User> optionalUser = userController.getUser(event.getPlayer().getUsername());
                    optionalUser.ifPresent(user -> {
                      user.setBlocksMined(user.getBlocksMined() + 1);
                        MinesServer.getInstance().getPickaxeFactory().givePickaxe(user, event.getPlayer());
                    });
                })
                .build();
    }
}