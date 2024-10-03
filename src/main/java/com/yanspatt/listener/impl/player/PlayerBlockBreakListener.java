package com.yanspatt.listener.impl.player;

import com.yanspatt.MinesServer;
import com.yanspatt.controller.UserController;
import com.yanspatt.enchantments.BlockHandler;
import com.yanspatt.enchantments.CustomEnchantment;
import com.yanspatt.listener.GenericEventListener;
import com.yanspatt.model.user.User;
import net.minestom.server.coordinate.Pos;
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
                        user.getPickaxe().getEnchantments().forEach((key,value) -> {
                            CustomEnchantment enchant = MinesServer.getInstance().getEnchantmentController().getEnchantments().get(key);
                            if (enchant != null) {
                                enchant.blockBreak(user, new BlockHandler(event.getBlock(),new Pos(event.getBlockPosition().blockX(),event.getBlockPosition().blockY(),event.getBlockPosition().blockZ()),event.getPlayer()));
                            }

                        });
                    });
                })
                .build();
    }
}