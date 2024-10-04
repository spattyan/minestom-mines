package com.yanspatt.model.mine;

import com.yanspatt.model.mine.packetMine.MiningAreaChunkSection;
import lombok.Data;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.instance.block.Block;

@Data
public class Mine {

    private int size;
    private int depth;
    private Block block;
    private transient Pos origin;

    private Pos position1;
    private Pos position2;

    private MiningAreaChunkSection section;

    public Mine(int depth) {
        this.size = 0;
        this.depth = depth;
        this.block = Block.STONE;
        this.section = new MiningAreaChunkSection();
    }


    public boolean setSize(int size) {
        if (size > 50) return false;
        this.size = size/2;
        Pos pos1 = new Pos((origin.blockX() - ((double) size)),origin.blockY()-depth,(origin.blockZ() - ((double) size)));
        Pos pos2 = new Pos((origin.blockX() + ((double) size)),origin.blockY()-1,(origin.blockZ() + ((double) size)));

        this.position1 = pos1;
        this.position2 = pos2;
        return true;

    }


}
