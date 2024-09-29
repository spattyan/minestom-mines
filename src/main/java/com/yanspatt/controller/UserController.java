package com.yanspatt.controller;

import com.yanspatt.model.pickaxe.Pickaxe;
import com.yanspatt.model.user.User;
import com.yanspatt.service.UserService;

import java.util.Optional;

public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    public User createUser(String username) {
        User user = new User(username);
        Pickaxe pickaxe = new Pickaxe();

        user.setPickaxe(pickaxe);


        userService.saveUser(user);return user;
    }

    public Optional<User> getUser(String username) {
        Optional<User> userOptional = userService.getUser(username);
        return userOptional;
    }

    public void saveUser(User user) {
        userService.saveUser(user);}

}
