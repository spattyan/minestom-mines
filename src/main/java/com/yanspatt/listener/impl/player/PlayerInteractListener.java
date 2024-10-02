package com.yanspatt.listener.impl.player;

import com.yanspatt.MinesServer;
import com.yanspatt.controller.UserController;
import com.yanspatt.inventory.PickaxeUpgradeInventory;
import com.yanspatt.listener.GenericEventListener;
import com.yanspatt.model.user.User;
import com.yanspatt.util.ItemBuilder;
import net.minestom.server.entity.Player;
import net.minestom.server.event.EventListener;
import net.minestom.server.event.player.PlayerBlockInteractEvent;
import net.minestom.server.event.player.PlayerUseItemEvent;
import net.minestom.server.item.ItemComponent;
import net.minestom.server.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class PlayerInteractListener implements GenericEventListener<PlayerUseItemEvent> {

    private UserController userController;

    public PlayerInteractListener(UserController userController) {
        this.userController = userController;
    }

    @Override
    public @NotNull EventListener<PlayerUseItemEvent> register() {
        return EventListener.builder(PlayerUseItemEvent.class)
                .handler(event -> {
                    userController.getUser(event.getPlayer().getUsername())
                    .ifPresent(user -> {
                                if (event.getHand().equals(Player.Hand.MAIN)) {
                                    if (event.getItemStack().isSimilar(user.getPickaxe().getItem())) {
                                        PickaxeUpgradeInventory.INVENTORY.open(event.getPlayer());
                                    }
                                }
                        });

                })
                .build();
    }
}
