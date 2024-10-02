package com.yanspatt.enchantments;

import lombok.Getter;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Player;
import net.minestom.server.instance.block.Block;

@Getter
public class BlockHandler {

    private Block block;
    private Pos position;
    private Player player;

    public BlockHandler(Block block, Pos position, Player player) {
        this.block = block;
        this.position = position;
        this.player = player;
    }
}
