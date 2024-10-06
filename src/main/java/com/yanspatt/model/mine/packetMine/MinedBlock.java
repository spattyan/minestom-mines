package com.yanspatt.model.mine.packetMine;

import lombok.Data;

@Data
public class MinedBlock {

    private int x;
    private int y;
    private int z;
    private MinedType type;

    public MinedBlock(MinedType type,int x, int y, int z) {
        this.type = type;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public MinedBlock(MinedType type, int y) {
        this.type = type;
        this.y = y;
    }


    public String asEncoded() {
        return x + "," + y + "," + z;
    }

    public static MinedBlock fromEncoded(String encoded) {
        String[] parts = encoded.split(",");
        int x = Integer.parseInt(parts[0]);
        int y = Integer.parseInt(parts[1]);
        int z = Integer.parseInt(parts[2]);


        return new MinedBlock(MinedType.BLOCK,x,y,z);
    }

}
