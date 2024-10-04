package com.yanspatt.controller;

import com.yanspatt.MinesServer;
import com.yanspatt.model.mine.Mine;
import com.yanspatt.model.pickaxe.Pickaxe;
import com.yanspatt.model.user.User;
import com.yanspatt.service.UserService;

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
        Pickaxe pickaxe = new Pickaxe();
        Mine mine = new Mine();
        mine.setOrigin(MinesServer.getInstance().getMineFactory().getOrigin());
        mine.setDepth(MinesServer.getInstance().getMineFactory().getDepth());
        mine.setSize(15);

        user.setPickaxe(pickaxe);
        user.setMine(mine);

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

}
