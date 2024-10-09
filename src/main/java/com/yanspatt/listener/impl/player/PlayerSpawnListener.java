package com.yanspatt.listener.impl.player;

import com.yanspatt.MinesServer;
import com.yanspatt.controller.UserController;
import com.yanspatt.listener.GenericEventListener;
import com.yanspatt.model.user.User;
import net.minestom.server.entity.GameMode;
import net.minestom.server.entity.attribute.Attribute;
import net.minestom.server.event.EventListener;
import net.minestom.server.event.player.PlayerSpawnEvent;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.atomic.AtomicReference;

public class PlayerSpawnListener implements GenericEventListener<PlayerSpawnEvent> {

    private UserController repository;

    public PlayerSpawnListener(UserController repository) {
        this.repository = repository;
    }

    @Override
    public @NotNull EventListener<PlayerSpawnEvent> register() {
        return EventListener.builder(PlayerSpawnEvent.class)
                .handler(event -> {

                    AtomicReference<User> getUser = new AtomicReference<>();
                    repository.getUser(event.getPlayer().getUsername())
                            .ifPresentOrElse((user -> {
                                event.getPlayer().sendMessage("Olá novamente " + event.getPlayer().getUsername());
                                getUser.set(user);
                            }),() -> {
                                getUser.set(new User(event.getPlayer().getUsername()));
                                repository.saveUser(getUser.get());
                                event.getPlayer().sendMessage("Olá " + event.getPlayer().getUsername());
                            });

                    event.getPlayer().setGameMode(GameMode.ADVENTURE);
                    event.getPlayer().setAllowFlying(true);
                    event.getPlayer().setFlying(true);
                    event.getPlayer().getAttribute(Attribute.PLAYER_BLOCK_BREAK_SPEED).setBaseValue((double) 999);
                    MinesServer.getInstance().getUserController().spawnPlayer(getUser.get(),event.getPlayer());

                    //MinesServer.getInstance().getScoreboard().addSidebar(event.getPlayer());
                    //MinesServer.getInstance().getMineFactory().populateMine(user.get(),user.get().getMine().getBlock(),false);
                    //MinesServer.getInstance().getMineFactory().sendMine(user.get(),event.getPlayer());

                })
                .build();
    }
}
