package com.yanspatt.listener.impl.player;

import com.yanspatt.MinesServer;
import com.yanspatt.controller.UserController;
import com.yanspatt.enchantments.BlockHandler;
import com.yanspatt.enchantments.CustomEnchantment;
import com.yanspatt.listener.GenericEventListener;
import com.yanspatt.model.user.User;
import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.event.EventListener;
import net.minestom.server.event.player.PlayerBlockBreakEvent;
import net.minestom.server.event.player.PlayerFinishDiggingEvent;
import net.minestom.server.instance.block.Block;
import net.minestom.server.network.packet.server.play.BlockBreakAnimationPacket;
import net.minestom.server.network.packet.server.play.BlockChangePacket;
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
                    if (event.getBlock().equals(Block.BEDROCK)) return;
                    event.setCancelled(true);
                    MinesServer.getInstance().getUserController().getUser(event.getPlayer().getUsername()).ifPresent(user -> {
                        if (MinesServer.getInstance().getMineController().blockIsInMine(user, event.getBlockPosition())) {
                            BlockChangePacket packet = new BlockChangePacket(event.getBlockPosition(), Block.AIR);
                            event.getPlayer().sendPacket(packet);

                            user.setBlocksMined(user.getBlocksMined() + 1);
                            MinesServer.getInstance().getPickaxeFactory().givePickaxe(user, event.getPlayer());
                            user.getPickaxe().getEnchantments().forEach((key,value) -> {
                                CustomEnchantment enchant = MinesServer.getInstance().getEnchantmentController().getEnchantments().get(key);
                                if (enchant != null) {
                                    enchant.blockBreak(user, new BlockHandler(null,new Pos(packet.blockPosition().blockX(),packet.blockPosition().blockY(),packet.blockPosition().blockZ()),event.getPlayer()));
                                }

                            });


                        }
                    });
                })
                .build();
    }
}