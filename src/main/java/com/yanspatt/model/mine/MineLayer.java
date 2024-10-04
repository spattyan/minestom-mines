package com.yanspatt.model.mine;

import com.google.common.collect.Lists;
import lombok.Data;
import net.minestom.server.instance.block.Block;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class MineLayer {

    private int number;
    private Map<Long, List<Long>> blocks;

    public MineLayer(int number) {
        this.number = number;
        this.blocks = new HashMap<>();
    }

    public void addBlock(Long chunk, Block block, int x, int y, int z) {
        List<Long> blocks = this.blocks.get(chunk);
        if (blocks == null) {
            blocks = Lists.newArrayList();
        }

        int blockX = x & 0xF; // Posição X dentro da seção (0-15)
        int blockY = y & 0xF; // Posição Y dentro da seção (0-15)
        int blockZ = z & 0xF;

        long data = (((long) block.stateId() & 0xFFFFF) << 12) | ((long) blockX << 8) | ((long) blockZ << 4) | blockY;

        blocks.add(data);
        this.blocks.put(chunk, blocks);
       // System.out.println("added block " + blockX + " " + blockY + " " + blockZ + " to chunk " + chunk);

    }

}
