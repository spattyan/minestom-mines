package com.yanspatt.model.mine;

import lombok.AllArgsConstructor;
import lombok.Data;
import net.minestom.server.coordinate.Point;
import net.minestom.server.coordinate.Pos;

@Data
@AllArgsConstructor
public class MineArea {

    private Pos position1;
    private Pos position2;


    public boolean isInside(Point location) {
        return location.blockX() >= position1.blockX()+1 && location.blockX() <= position2.blockX()-1 && location.blockZ() >= position1.blockZ()+1 && location.blockZ() <= position2.blockZ()-1 && location.blockY() > position1.blockY();
    }
}
