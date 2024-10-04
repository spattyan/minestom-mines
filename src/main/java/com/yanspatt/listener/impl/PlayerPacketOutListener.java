package com.yanspatt.listener.impl;

import com.yanspatt.MinesServer;
import com.yanspatt.listener.GenericEventListener;
import net.minestom.server.event.EventListener;
import net.minestom.server.event.player.PlayerPacketEvent;
import net.minestom.server.event.player.PlayerPacketOutEvent;
import net.minestom.server.network.packet.client.play.ClientPlayerDiggingPacket;
import net.minestom.server.network.packet.server.play.AcknowledgeBlockChangePacket;
import net.minestom.server.network.packet.server.play.BlockBreakAnimationPacket;
import org.jetbrains.annotations.NotNull;

public class PlayerPacketOutListener implements GenericEventListener<PlayerPacketOutEvent> {

    @Override
    public @NotNull EventListener<PlayerPacketOutEvent> register() {
        return EventListener.builder(PlayerPacketOutEvent.class)
                .handler(event -> {


                })
                .build();
    }
}
