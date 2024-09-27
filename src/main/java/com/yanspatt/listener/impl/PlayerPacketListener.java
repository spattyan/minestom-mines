package com.yanspatt.listener.impl;

import com.yanspatt.listener.GenericEventListener;
import net.minestom.server.event.EventListener;
import net.minestom.server.event.player.PlayerPacketEvent;
import net.minestom.server.network.packet.client.play.ClientPlayerDiggingPacket;
import org.jetbrains.annotations.NotNull;

public class PlayerPacketListener implements GenericEventListener<PlayerPacketEvent> {

    @Override
    public @NotNull EventListener<PlayerPacketEvent> register() {
        return EventListener.builder(PlayerPacketEvent.class)
                .handler(event -> {

                    if (event.getPacket() instanceof ClientPlayerDiggingPacket) {
                        ClientPlayerDiggingPacket packet = (ClientPlayerDiggingPacket) event.getPacket();
                        System.out.println("Player " + event.getPlayer().getUsername() + " is digging at " + packet.blockPosition());
                    }

                })
                .build();
    }
}
