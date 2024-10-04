package com.yanspatt.factory;

import com.yanspatt.MinesServer;
import com.yanspatt.model.mine.Mine;
import com.yanspatt.model.mine.packetMine.MiningChunkSection;
import com.yanspatt.model.user.User;
import com.yanspatt.util.PaletteUtils;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Player;
import net.minestom.server.instance.Chunk;
import net.minestom.server.instance.block.Block;
import net.minestom.server.network.packet.server.play.MultiBlockChangePacket;
import net.minestom.server.utils.chunk.ChunkUtils;

import java.util.List;
import java.util.Map;

public class MineFactory {

    private final Pos origin = new Pos(10.0,42.0,10.0);
    private final int depth = 40;

    public void createMine(User user) {
        int size = 30;

        Mine mine = new Mine(depth-1);
        mine.setOrigin(origin);
        mine.setSize(size);

        user.setMine(mine);
        MinesServer.getInstance().getUserController().update(user);
    }

    public void populateMine(User user, Player player, Block block) {
        Mine mine = user.getMine();

        for (int x = mine.getPosition1().blockX(); x <= mine.getPosition2().blockX(); ++x) {
            for (int y = mine.getPosition1().blockY(); y <= mine.getPosition2().blockY(); ++y) {
                for (int z = mine.getPosition1().blockZ(); z <= mine.getPosition2().blockZ(); ++z) {

                    Chunk chunk = player.getInstance().getChunkAt(x, z);
                    int sectionY = ChunkUtils.blockIndexToChunkPositionY(y);
                    MiningChunkSection section = mine.getSection().getChunk(chunk.getChunkX(),chunk.getChunkZ(),sectionY);
                    if (section == null) {
                        section = mine.getSection().add(chunk.getChunkX(),chunk.getChunkZ(),sectionY);
                    }

                    int relX = x & 0xF;
                    int relY = y & 0xFF;
                    int relZ = z & 0xF;
                    if(x == mine.getPosition2().blockX() || x == mine.getPosition1().blockX() || y == mine.getPosition1().blockY() || z == mine.getPosition2().blockZ() || z == mine.getPosition1().blockZ()) {
                        section.getBlocks().set(relX, relY, relZ, Block.BEDROCK.stateId());
                    } else {
                        section.getBlocks().set(relX, relY, relZ, block.stateId());
                    }

                }
            }
        }
    }


    public void sendMine(User user, Player player) {
        Mine mine = user.getMine();

        for (MiningChunkSection miningChunkSection : mine.getSection().getChunkSection()) {
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

            player.getInstance().loadChunk(miningChunkSection.getChunkX(),miningChunkSection.getChunkZ())
                    .whenComplete((chunk, throwable) -> {
                        if (chunk != null) {
                            MultiBlockChangePacket packet = new MultiBlockChangePacket(miningChunkSection.getChunkX(), miningChunkSection.getId(), miningChunkSection.getChunkZ(),blocksArray);
                            player.sendPacket(packet);
                        }

                        if (throwable != null) {
                            throwable.printStackTrace();
                        }
                    });
        }
    }

    public static long encodeBlockInChunkSection(int x, int y, int z, int stateId) {
        int blockX = x & 0xF;
        int blockY = y & 0xF;
        int blockZ = z & 0xF;

        return (((long) stateId & 0xFFFFF) << 12) | (blockX << 8) | (blockZ << 4) | blockY;
    }

}
