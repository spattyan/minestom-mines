package com.yanspatt.listener.impl;

import com.yanspatt.MinesServer;
import com.yanspatt.enchantments.BlockHandler;
import com.yanspatt.enchantments.CustomEnchantment;
import com.yanspatt.listener.GenericEventListener;
import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.event.EventListener;
import net.minestom.server.event.player.PlayerBlockBreakEvent;
import net.minestom.server.event.player.PlayerPacketEvent;
import net.minestom.server.network.packet.client.play.ClientPlayerDiggingPacket;
import net.minestom.server.potion.Potion;
import net.minestom.server.potion.PotionEffect;
import org.jetbrains.annotations.NotNull;

public class PlayerPacketListener implements GenericEventListener<PlayerPacketEvent> {

    @Override
    public @NotNull EventListener<PlayerPacketEvent> register() {
        return EventListener.builder(PlayerPacketEvent.class)
                .handler(event -> {

                    if (event.getPacket() instanceof ClientPlayerDiggingPacket) {

                        ClientPlayerDiggingPacket packet = (ClientPlayerDiggingPacket) event.getPacket();
                        if (packet.status() == ClientPlayerDiggingPacket.Status.STARTED_DIGGING) {
                           event.getInstance().breakBlock(event.getPlayer(),packet.blockPosition(),packet.blockFace());
                        }
                    }

                })
                .build();
    }
}
