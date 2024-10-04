package com.yanspatt.model.mine.packetMine;

import lombok.Getter;
import net.minestom.server.instance.block.Block;
import net.minestom.server.instance.palette.Palette;

import java.util.ArrayList;
import java.util.List;

// Store 16x16x16 area

@Getter
public class MiningChunkSection {

    private int id;
    private int chunkX;
    private int chunkZ;
    private Palette blocks;

    public MiningChunkSection(int id, int chunkX, int chunkZ) {
        this.id = id;
        this.chunkX = chunkX;
        this.chunkZ = chunkZ;
        blocks = Palette.blocks();
    }


    public void setBlock(int x, int y, int z, Block block) {
        blocks.set(x,y,z,block.stateId());
    }

    public Block getBlock(int x, int y, int z) {
        return Block.fromBlockId(blocks.get(x,y,z));
    }

    public boolean isEqual(int x, int section, int z) {
        return x == this.chunkX && z == this.chunkZ && this.id == section;
    }
}
