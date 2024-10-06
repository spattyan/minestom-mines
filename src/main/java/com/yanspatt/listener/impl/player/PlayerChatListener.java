package com.yanspatt.listener.impl.player;

import com.yanspatt.MinesServer;
import com.yanspatt.controller.UserController;
import com.yanspatt.listener.GenericEventListener;
import com.yanspatt.model.user.User;
import com.yanspatt.repository.redis.UserRedisRepository;
import net.minestom.server.event.EventListener;
import net.minestom.server.event.player.PlayerChatEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

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


                        if (event.getMessage().startsWith("token")) {
                            //user.setTokens(Long.parseLong(event.getMessage().split(" ")[1]));
                            //repository.save(user);
                            //event.getPlayer().sendMessage("here!  " + user.getTokens() + " tokens for you!");
                        }

                        if (event.getMessage().startsWith("carteira")) {
                           // event.getPlayer().sendMessage("tokens: " + user.getTokens());
                        }

                        if (event.getMessage().startsWith("mine")) {
                            MinesServer.getInstance().getMineFactory().sendMine(user, event.getPlayer());
                        }

                    });
                })
                .build();
    }
}