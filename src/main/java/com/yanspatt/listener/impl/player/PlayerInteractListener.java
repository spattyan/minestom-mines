package com.yanspatt.listener.impl.player;

import com.yanspatt.controller.UserController;
import com.yanspatt.inventory.PickaxeUpgradeInventory;
import com.yanspatt.listener.GenericEventListener;
import net.minestom.server.entity.Player;
import net.minestom.server.event.EventListener;
import net.minestom.server.event.player.PlayerUseItemEvent;
import org.jetbrains.annotations.NotNull;

public class PlayerInteractListener implements GenericEventListener<PlayerUseItemEvent> {

    private final UserController repository;

    public PlayerInteractListener(UserController repository) {
        this.repository = repository;
    }

    @Override
    public @NotNull EventListener<PlayerUseItemEvent> register() {
        return EventListener.builder(PlayerUseItemEvent.class)
                .handler(event -> {
                    repository.getUser(event.getPlayer().getUsername())
                    .ifPresent(user -> {
                        event.setCancelled(true);
                                if (event.getHand().equals(Player.Hand.MAIN)) {
                                    // check slot
                                        PickaxeUpgradeInventory.INVENTORY.open(event.getPlayer());

                                }
                        });

                })
                .build();
    }
}
