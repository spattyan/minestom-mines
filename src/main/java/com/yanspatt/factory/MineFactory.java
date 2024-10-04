package com.yanspatt.factory;

import com.yanspatt.MinesServer;
import com.yanspatt.model.mine.Mine;
import com.yanspatt.model.mine.packetMine.MinedBlock;
import com.yanspatt.model.mine.packetMine.MiningChunkSection;
import com.yanspatt.model.user.User;
import com.yanspatt.util.PaletteUtils;
import lombok.Getter;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Player;
import net.minestom.server.instance.Chunk;
import net.minestom.server.instance.block.Block;
import net.minestom.server.network.packet.server.play.BlockChangePacket;
import net.minestom.server.network.packet.server.play.MultiBlockChangePacket;
import net.minestom.server.utils.chunk.ChunkUtils;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;

@Getter
public class MineFactory {

    private final Pos origin = new Pos(10.0,42.0,10.0);
    private final int depth = 40;

    public void populateMine(User user, Player player, Block block,boolean resetBlocks) {
        Mine mine = user.getMine();

        if (resetBlocks) {
            mine.getMinedBlocks().clear();
        }
        mine.setTotalBlocks(0);

        for (int x = mine.getPosition1().blockX(); x <= mine.getPosition2().blockX(); ++x) {
            for (int y = mine.getPosition1().blockY(); y <= mine.getPosition2().blockY(); ++y) {
                for (int z = mine.getPosition1().blockZ(); z <= mine.getPosition2().blockZ(); ++z) {

                    //Chunk chunk = player.getInstance().getChunkAt(x, z);
                    int sectionY = ChunkUtils.blockIndexToChunkPositionY(y);
                    MiningChunkSection section = mine.getSection().getChunk(x>>4,z>>4,sectionY);
                    if (section == null) {
                        section = mine.getSection().add(x>>4,z>>4,sectionY);
                    }

                    int relX = x & 0xF;
                    int relY = y & 0xFF;
                    int relZ = z & 0xF;
                    if(x == mine.getPosition2().blockX() || x == mine.getPosition1().blockX() || y == mine.getPosition1().blockY() || z == mine.getPosition2().blockZ() || z == mine.getPosition1().blockZ()) {
                        section.getBlocks().set(relX, relY, relZ, Block.SMOOTH_STONE.stateId());
                    } else {
                        mine.setTotalBlocks(mine.getTotalBlocks() + 1);
                        section.getBlocks().set(relX, relY, relZ, block.stateId());
                    }

                }
            }
        }
    }


    public void sendMine(User user, Player player) {
        Mine mine = user.getMine();
        CompletableFuture.runAsync(() -> {
                    AtomicInteger packetSize = new AtomicInteger();
                    for (MiningChunkSection miningChunkSection : mine.getSection().getChunkSection()) {

                        player.getInstance().loadChunk(miningChunkSection.getChunkX(), miningChunkSection.getChunkZ())
                                .whenComplete((chunk, throwable) -> {
                                    if (chunk != null) {
                                        player.sendChunk(chunk);
                                    }
                                    if (throwable != null) {
                                        throwable.printStackTrace();
                                    }
                                });

                        System.out.println("Send " + packetSize.get() + " packets to player");
                    }
                });

        MinesServer.getInstance().getPickaxeFactory().givePickaxe(user, player);
    }

    public static long encodeBlockInChunkSection(int x, int y, int z, int stateId) {
        int blockX = x & 0xF;
        int blockY = y & 0xF;
        int blockZ = z & 0xF;

        return (((long) stateId & 0xFFFFF) << 12) | (blockX << 8) | (blockZ << 4) | blockY;
    }

}
