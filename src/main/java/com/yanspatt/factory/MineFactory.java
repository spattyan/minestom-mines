package com.yanspatt.factory;

import com.yanspatt.MinesServer;
import com.yanspatt.model.mine.Mine;
import com.yanspatt.model.mine.MineArea;
import com.yanspatt.model.mine.packetMine.MiningChunkSection;
import com.yanspatt.model.user.User;
import lombok.Getter;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Player;
import net.minestom.server.instance.block.Block;
import net.minestom.server.utils.chunk.ChunkUtils;

@Getter
public class MineFactory {

    private final Pos origin = new Pos(10.0,42.0,10.0);
    private final int depth = 40;

    public void populateMine(User user, Block block,boolean resetBlocks) {
        MineArea area = user.getMineArea();
        Mine mine = user.getMine();

        for (int x = area.getPosition1().blockX(); x <= area.getPosition2().blockX(); ++x) {
            for (int y = area.getPosition1().blockY(); y <= area.getPosition2().blockY(); ++y) {
                for (int z = area.getPosition1().blockZ(); z <= area.getPosition2().blockZ(); ++z) {
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

        for (MiningChunkSection miningChunkSection : mine.getSection().getChunkSection()) {
            player.getInstance().loadChunk(miningChunkSection.getChunkX(),miningChunkSection.getChunkZ())
                    .whenComplete((chunk, throwable) -> {
                        if (chunk != null) {
                            player.sendChunk(chunk);
                        }
                        if (throwable != null) {
                            throwable.printStackTrace();
                        }
                    });
        }

        MinesServer.getInstance().getPickaxeFactory().givePickaxe(user, player);
    }

}
