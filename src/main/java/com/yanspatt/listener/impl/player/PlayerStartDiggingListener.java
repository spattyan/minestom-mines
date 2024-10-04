package com.yanspatt.listener.impl.player;

import com.yanspatt.MinesServer;
import com.yanspatt.controller.UserController;
import com.yanspatt.enchantments.BlockHandler;
import com.yanspatt.enchantments.CustomEnchantment;
import com.yanspatt.listener.GenericEventListener;
import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.event.EventListener;
import net.minestom.server.event.player.PlayerBlockBreakEvent;
import net.minestom.server.event.player.PlayerStartDiggingEvent;
import net.minestom.server.instance.block.Block;
import net.minestom.server.network.packet.server.play.BlockBreakAnimationPacket;
import net.minestom.server.network.packet.server.play.BlockChangePacket;
import net.minestom.server.potion.Potion;
import net.minestom.server.potion.PotionEffect;
import org.jetbrains.annotations.NotNull;

public class PlayerStartDiggingListener implements GenericEventListener<PlayerStartDiggingEvent> {

    private UserController userController;

    public PlayerStartDiggingListener(UserController userController) {
        this.userController = userController;
    }

    @Override
    public @NotNull EventListener<PlayerStartDiggingEvent> register() {
        return EventListener.builder(PlayerStartDiggingEvent.class)
                .handler(event -> {
                   // System.out.println("started");
                   /* MinecraftServer.getGlobalEventHandler().call(
                            new PlayerBlockBreakEvent(
                                    event.getPlayer(),event.getBlock(),event.getBlock(),event.getBlockPosition(),event.getBlockFace()));*/
                })
                .build();
    }
}