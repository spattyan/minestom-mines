package com.yanspatt.listener.impl.player;

import com.yanspatt.MinesServer;
import com.yanspatt.controller.UserController;
import com.yanspatt.factory.MineFactory;
import com.yanspatt.listener.GenericEventListener;
import com.yanspatt.model.mine.Mine;
import com.yanspatt.model.user.User;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.GameMode;
import net.minestom.server.entity.attribute.Attribute;
import net.minestom.server.event.EventListener;
import net.minestom.server.event.player.PlayerSpawnEvent;
import net.minestom.server.potion.Potion;
import net.minestom.server.potion.PotionEffect;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

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
                        user = userController.createUser(event.getPlayer().getUsername());
                    }

                    event.getPlayer().setGameMode(GameMode.ADVENTURE);
                    event.getPlayer().setAllowFlying(true);
                    event.getPlayer().setFlying(true);
                    event.getPlayer().getAttribute(Attribute.PLAYER_BLOCK_BREAK_SPEED).setBaseValue((double) 999);
                    //MinesServer.getInstance().getScoreboard().addSidebar(event.getPlayer());
                    MinesServer.getInstance().getMineFactory().populateMine(user.get(),user.get().getMine().getBlock(),false);
                    MinesServer.getInstance().getMineFactory().sendMine(user.get(),event.getPlayer());

                })
                .build();
    }
}
