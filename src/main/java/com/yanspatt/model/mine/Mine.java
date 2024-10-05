package com.yanspatt.model.mine;

import com.yanspatt.MinesServer;
import com.yanspatt.model.mine.packetMine.MinedBlock;
import com.yanspatt.model.mine.packetMine.MiningAreaChunkSection;
import lombok.Data;
import net.minestom.server.coordinate.Point;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.instance.block.Block;

import java.util.ArrayList;
import java.util.List;

@Data
public class Mine {

    private int level;
    private long xp;

    private int size;
    private int depth;

    private int totalBlocks;
    private int brokenBlocks;
    private Block block;
    private transient Pos origin;

    private Pos position1;
    private Pos position2;

    private transient MiningAreaChunkSection section;
    private List<MinedBlock> minedBlocks;

    public Mine() {
        this.size = 15;
        this.depth = 40;
        this.totalBlocks = 0;
        this.brokenBlocks = 0;
        this.block = Block.STONE;
        this.section = new MiningAreaChunkSection();
        this.minedBlocks = new ArrayList<>();
        this.position1 = new Pos(0.0,0.0,0.0);
        this.position2 = new Pos(0.0,0.0,0.0);
        this.origin = MinesServer.getInstance().getMineFactory().getOrigin();
    }

    public boolean isInside(Point location) {
        return location.blockX() >= position1.blockX()+1 && location.blockX() <= position2.blockX()-1 && location.blockZ() >= position1.blockZ()+1 && location.blockZ() <= position2.blockZ()-1 && location.blockY() > position1.blockY();
    }


    public boolean setSize(int size) {
        if (size > 50) return false;
        this.size = size;
        Pos pos1 = new Pos((origin.blockX() - ((double) size/2)),origin.blockY()-depth,(origin.blockZ() - ((double) size/2)));
        Pos pos2 = new Pos((origin.blockX() + ((double) size/2)),origin.blockY()-1,(origin.blockZ() + ((double) size/2)));

        this.position1 = pos1;
        this.position2 = pos2;
        return true;

    }

}
