package com.yanspatt.model.mine.packetMine;

import com.google.gson.annotations.JsonAdapter;
import com.yanspatt.adapter.MineBlockAdapter;
import lombok.Data;
import net.minestom.server.instance.block.Block;

@Data
@JsonAdapter(MineBlockAdapter.class)
public class MinedBlock {

    private int x;
    private int y;
    private int z;
    private int stateId;

    public MinedBlock(int x, int y, int z, int stateId) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.stateId = stateId;
    }

    public Block getBlock() {
        return Block.fromStateId(stateId);
    }

    public String asEncoded() {
        return x + "," + y + "," + z + "," + stateId;
    }

    public static MinedBlock fromEncoded(String encoded) {
        String[] parts = encoded.split(",");
        int x = Integer.parseInt(parts[0]);
        int y = Integer.parseInt(parts[1]);
        int z = Integer.parseInt(parts[2]);
        int stateId = Integer.parseInt(parts[3]);

        return new MinedBlock(x,y,z,stateId);
    }

}
