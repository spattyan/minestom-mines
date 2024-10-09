package com.yanspatt.model.mine;

import lombok.Data;
import net.minestom.server.instance.block.Block;

@Data
public class MineLevel {

    private int level;
    private Block block;
    private int requiredLevel;


}
