package com.yanspatt.controller;

import com.yanspatt.MinesServer;
import com.yanspatt.model.mine.Mine;
import com.yanspatt.model.mine.MineArea;
import com.yanspatt.model.user.User;
import com.yanspatt.service.UserService;
import net.minestom.server.entity.Player;
import net.minestom.server.instance.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    public Optional<User> createUser(String username) {
        if (getUser(username).isPresent()) {
            return userService.getUser(username);
        }
        User user = new User(username);
        Mine mine = new Mine();
        mine.setOrigin(MinesServer.getInstance().getMineFactory().getOrigin());
        mine.setDepth(MinesServer.getInstance().getMineFactory().getDepth());
        mine.setSize(15);

        userService.saveUser(user);
        return Optional.of(user);
    }

    public Optional<User> getUser(String username) {
        Optional<User> userOptional = userService.getUser(username);

        return userOptional;
    }

    public void saveUser(User user) {
        userService.saveUser(user);
    }

    public void update(User user) {
        userService.updateCache(user);
    }

    public void spawnPlayer(User user, @NotNull Player player) {

        Mine mine = new Mine();
        mine.setOrigin(MinesServer.getInstance().getMineFactory().getOrigin());
        mine.setDepth(MinesServer.getInstance().getMineFactory().getDepth());
        mine.setSize(40);
        user.setMine(mine);
        user.setMineArea(new MineArea(mine.getPosition1(),mine.getPosition2()));
        MinesServer.getInstance().getMineFactory().populateMine(user, Block.DIAMOND_BLOCK,true);



        MinesServer.getInstance().getPickaxeFactory().givePickaxe(user,player);

        update(user);
    }
}
