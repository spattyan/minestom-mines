package com.yanspatt.controller;

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
        userService.saveUser(user); // Salva o usuário
        return user;
    }

    public Optional<User> getUser(String username) {
        Optional<User> userOptional = userService.getUser(username);
        if (userOptional.isPresent()) {
           // User user = userOptional.get();

            // TODO setar picareta e mina no jogador by services
            //Optional<Pickaxe> pickaxeOptional = pickaxeService.getPickaxe(user.getUsername());
            //pickaxeOptional.ifPresent(user::setPickaxe);
        }
        return userOptional;
    }

    public void saveUser(User user) {
        userService.saveUser(user);
    }

}
