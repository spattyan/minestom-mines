package com.yanspatt.listener.impl.player;

import com.yanspatt.controller.UserController;
import com.yanspatt.listener.GenericEventListener;
import com.yanspatt.model.mine.packetMine.MinedBlock;
import com.yanspatt.model.mine.packetMine.MiningChunkSection;
import com.yanspatt.util.PaletteUtils;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.event.EventListener;
import net.minestom.server.event.player.PlayerChunkLoadEvent;
import net.minestom.server.event.player.PlayerChunkUnloadEvent;
import net.minestom.server.instance.block.Block;
import net.minestom.server.network.packet.server.play.BlockChangePacket;
import net.minestom.server.network.packet.server.play.MultiBlockChangePacket;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

public class PlayerUnloadChunkListener implements GenericEventListener<PlayerChunkUnloadEvent> {

    private UserController userController;

    public PlayerUnloadChunkListener(UserController userController) {
        this.userController = userController;
    }

    @Override
    public @NotNull EventListener<PlayerChunkUnloadEvent> register() {
        return EventListener.builder(PlayerChunkUnloadEvent.class)
                .handler(event -> {
                    userController.getUser(event.getPlayer().getUsername()).ifPresent(user -> {
                        List<MiningChunkSection> sectionsToLoad = user.getMine().getSection().getChunkSection().stream().filter(sec ->
                                sec.getChunkX() == event.getChunkX() && sec.getChunkZ() == event.getChunkZ()
                        ).toList();
                        for (MiningChunkSection miningChunkSection : sectionsToLoad) {
                            miningChunkSection.setLoaded(false);
                        }

                    });
                })
                .build();

    }
    public static long encodeBlockInChunkSection(int x, int y, int z, int stateId) {
        int blockX = x & 0xF;
        int blockY = y & 0xF;
        int blockZ = z & 0xF;

        return (((long) stateId & 0xFFFFF) << 12) | (blockX << 8) | (blockZ << 4) | blockY;
    }
}