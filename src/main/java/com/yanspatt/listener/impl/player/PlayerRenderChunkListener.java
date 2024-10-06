package com.yanspatt.listener.impl.player;

import com.google.common.collect.Lists;
import com.yanspatt.controller.UserController;
import com.yanspatt.listener.GenericEventListener;
import com.yanspatt.model.mine.packetMine.MinedBlock;
import com.yanspatt.model.mine.packetMine.MinedType;
import com.yanspatt.model.mine.packetMine.MiningChunkSection;
import com.yanspatt.repository.redis.UserRedisRepository;
import com.yanspatt.util.PaletteUtils;
import net.minestom.server.event.EventListener;
import net.minestom.server.event.player.PlayerChunkLoadEvent;
import net.minestom.server.instance.block.Block;
import net.minestom.server.network.packet.server.play.MultiBlockChangePacket;
import net.minestom.server.utils.chunk.ChunkUtils;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

public class PlayerRenderChunkListener implements GenericEventListener<PlayerChunkLoadEvent> {

    private UserController repository;

    public PlayerRenderChunkListener(UserController repository) {
        this.repository = repository;
    }

    @Override
    public @NotNull EventListener<PlayerChunkLoadEvent> register() {
        return EventListener.builder(PlayerChunkLoadEvent.class)
                .handler(event -> {
                    repository.getUser(event.getPlayer().getUsername()).ifPresent(user -> {
                        if (user.getMine() == null) return;
                        List<MiningChunkSection> sectionsToLoad = user.getMine().getSection().getChunkSection().stream().filter(sec ->
                                sec.getChunkX() == event.getChunkX() && sec.getChunkZ() == event.getChunkZ()
                        ).toList();

                        List<Integer> dontLoad = Lists.newArrayList();
                        List<MinedBlock> minedBlocks = user.getMine().getMinedBlocks().stream().filter(e -> e.getType().equals(MinedType.LAYER)).toList();
                        minedBlocks.forEach(block -> {
                            if (!dontLoad.contains(block.getY())) {
                                dontLoad.add(block.getY());
                            }
                        });

                        for (MiningChunkSection miningChunkSection : sectionsToLoad) {
                            List<Map<String, Integer>> blocksList = PaletteUtils.getBlocksInPalette(miningChunkSection.getBlocks());

                            List<MinedBlock> atChunk = user.getMine().getMinedBlocks().stream().filter(block -> (block.getX() >> 4)== miningChunkSection.getChunkX() && (block.getZ() >> 4) == miningChunkSection.getChunkZ() && ChunkUtils.blockIndexToChunkPositionY(block.getY()) == miningChunkSection.getId() && block.getType() == MinedType.BLOCK).toList();
                            blocksList.addAll(PaletteUtils.getBlocksInList(atChunk));
                            long[] blocksArray = new long[blocksList.size()];

                            for (int i = 0; i < blocksList.size(); i++) {
                                Map<String, Integer> block = blocksList.get(i);
                                int x = block.get("x");
                                int y = block.get("y");
                                int z = block.get("z");
                                int stateId = block.get("stateId");
                                if (dontLoad.contains(block.get("y") + (miningChunkSection.getId() * 16)) && stateId != Block.SMOOTH_STONE.stateId()) {
                                    stateId = 0;
                                }
                                long blockLong = encodeBlockInChunkSection(x, y, z, stateId);
                                blocksArray[i] = blockLong;
                            }

                            MultiBlockChangePacket packet = new MultiBlockChangePacket(miningChunkSection.getChunkX(), miningChunkSection.getId(), miningChunkSection.getChunkZ(),blocksArray);
                            event.getPlayer().sendPacket(packet);
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