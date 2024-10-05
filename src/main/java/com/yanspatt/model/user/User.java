package com.yanspatt.model.user;

import com.yanspatt.model.mine.Mine;
import com.yanspatt.model.pickaxe.Pickaxe;
import lombok.Data;

@Data
public class User {

    private String username;

    private long blocksMined;
    private long tokens;
    private long xp;

    private Pickaxe pickaxe;
    private Mine mine;

    public User(String username) {
        this.username = username;
        this.blocksMined = 0;
        this.tokens = 0;
        this.xp = 0;
    }
}
