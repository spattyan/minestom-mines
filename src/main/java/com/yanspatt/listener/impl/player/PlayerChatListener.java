package com.yanspatt.listener.impl.player;

import com.yanspatt.MinesServer;
import com.yanspatt.controller.UserController;
import com.yanspatt.listener.GenericEventListener;
import com.yanspatt.model.pickaxe.PickaxeEnchantment;
import com.yanspatt.model.pickaxe.PickaxeSkin;
import com.yanspatt.model.user.User;
import net.minestom.server.event.EventListener;
import net.minestom.server.event.player.PlayerBlockBreakEvent;
import net.minestom.server.event.player.PlayerChatEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class PlayerChatListener implements GenericEventListener<PlayerChatEvent> {

    private UserController userController;

    public PlayerChatListener(UserController userController) {
        this.userController = userController;
    }

    @Override
    public @NotNull EventListener<PlayerChatEvent> register() {
        return EventListener.builder(PlayerChatEvent.class)
                .handler(event -> {
                    Optional<User> optionalUser = userController.getUser(event.getPlayer().getUsername());
                    optionalUser.ifPresent(user -> {


                        if (event.getMessage().startsWith("skin")) {
                            user.getPickaxe().setSkin(PickaxeSkin.valueOf(event.getMessage().split(" ")[1].toUpperCase()));
                            event.getPlayer().sendMessage("Skin set to " + user.getPickaxe().getSkin().name());
                        }

                        if (event.getMessage().startsWith("enchantment")) {
                            PickaxeEnchantment enchantment = PickaxeEnchantment.valueOf(event.getMessage().split(" ")[1].toUpperCase());
                            user.getPickaxe().addEnchantment(enchantment, Integer.parseInt(event.getMessage().split(" ")[2]));
                            event.getPlayer().sendMessage("Applied enchantment " + enchantment.getName() + " level " + event.getMessage().split(" ")[2]);
                        }
                        if (event.getMessage().startsWith("-enchantment")) {
                            PickaxeEnchantment enchantment = PickaxeEnchantment.valueOf(event.getMessage().split(" ")[1].toUpperCase());
                            user.getPickaxe().removeEnchantment(enchantment);
                            event.getPlayer().sendMessage("Removed enchantment " + enchantment.getName());
                        }

                        MinesServer.getInstance().getPickaxeFactory().givePickaxe(user, event.getPlayer());
                    });
                })
                .build();
    }
}