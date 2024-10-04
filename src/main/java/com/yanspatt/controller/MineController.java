package com.yanspatt.controller;

import com.yanspatt.model.mine.Mine;
import com.yanspatt.model.pickaxe.Pickaxe;
import com.yanspatt.model.user.User;
import com.yanspatt.service.UserService;
import net.minestom.server.coordinate.Point;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.instance.block.Block;

import java.util.Optional;

public class MineController {

    private final UserService userService;

    public MineController(UserService userService) {
        this.userService = userService;
    }

    public boolean blockIsInMine(User user, Point location) {
        Mine mine = user.getMine();

        return location.blockX() >= mine.getPosition1().blockX()+1 && location.blockX() <= mine.getPosition2().blockX()-1 && location.blockZ() >= mine.getPosition1().blockZ()+1 && location.blockZ() <= mine.getPosition2().blockZ()-1 && location.blockY() > mine.getPosition1().blockY();
    }

}
