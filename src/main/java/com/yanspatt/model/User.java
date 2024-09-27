package com.yanspatt.model;

import lombok.Data;

@Data
public class User {

    private String username;

    private long blocksMined;
    private long tokens;
    private long level;

    public User(String username) {
        this.username = username;
        this.blocksMined = 0;
        this.tokens = 0;
        this.level = 0;
    }
}
