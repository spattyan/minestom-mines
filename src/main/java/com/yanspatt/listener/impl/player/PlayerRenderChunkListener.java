package com.yanspatt.listener.impl.player;

import com.yanspatt.MinesServer;
import com.yanspatt.controller.UserController;
import com.yanspatt.listener.GenericEventListener;
import com.yanspatt.model.mine.packetMine.MinedBlock;
import com.yanspatt.model.mine.packetMine.MiningChunkSection;
import com.yanspatt.model.user.User;
import com.yanspatt.util.PaletteUtils;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.event.EventListener;
import net.minestom.server.event.player.PlayerChatEvent;
import net.minestom.server.event.player.PlayerChunkLoadEvent;
import net.minestom.server.instance.block.Block;
import net.minestom.server.network.packet.server.play.BlockChangePacket;
import net.minestom.server.network.packet.server.play.MultiBlockChangePacket;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class PlayerRenderChunkListener implements GenericEventListener<PlayerChunkLoadEvent> {

    private UserController userController;

    public PlayerRenderChunkListener(UserController userController) {
        this.userController = userController;
    }

    @Override
    public @NotNull EventListener<PlayerChunkLoadEvent> register() {
        return EventListener.builder(PlayerChunkLoadEvent.class)
                .handler(event -> {
                    userController.getUser(event.getPlayer().getUsername()).ifPresent(user -> {
                        List<MiningChunkSection> sectionsToLoad = user.getMine().getSection().getChunkSection().stream().filter(sec ->
                                sec.getChunkX() == event.getChunkX() && sec.getChunkZ() == event.getChunkZ()
                        ).toList();

                        for (MiningChunkSection miningChunkSection : sectionsToLoad) {
                            List<Map<String, Integer>> blocksList = PaletteUtils.getBlocksInPalette(miningChunkSection.getBlocks());

                            long[] blocksArray = new long[blocksList.size()];

                            for (int i = 0; i < blocksList.size(); i++) {
                                Map<String, Integer> block = blocksList.get(i);
                                int x = block.get("x");
                                int y = block.get("y");
                                int z = block.get("z");
                                int stateId = block.get("stateId");

                                long blockLong = encodeBlockInChunkSection(x, y, z, stateId);
                                blocksArray[i] = blockLong;
                            }

                            MultiBlockChangePacket packet = new MultiBlockChangePacket(miningChunkSection.getChunkX(), miningChunkSection.getId(), miningChunkSection.getChunkZ(),blocksArray);
                            event.getPlayer().sendPacket(packet);
                        }

                        for (MinedBlock minedBlock : user.getMine().getMinedBlocks()) {

                            Pos loc = new Pos(minedBlock.getX(), minedBlock.getY(), minedBlock.getZ());
                            BlockChangePacket packet = new BlockChangePacket(loc, Block.AIR);
                            event.getPlayer().sendPacket(packet);
                            //player.teleport(loc);
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