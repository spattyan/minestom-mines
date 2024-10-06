package com.yanspatt.model.user;

import com.google.common.collect.Maps;
import com.yanspatt.model.mine.Mine;
import com.yanspatt.model.mine.MineArea;
import com.yanspatt.model.pickaxe.EnchantmentType;
import com.yanspatt.model.pickaxe.PickaxeSkin;
import lombok.Data;

import java.util.Map;

@Data
public class User {

    private String name;

    private int pickaxeLevel;
    private PickaxeSkin pickaxeSkin;
    private Map<EnchantmentType,Integer> enchantments;

    private long blocks;
    private MineArea mineArea;

    private int mineRank;
    private long experience;

    private transient long lastCoins;
    private transient long lastTokens;
    private transient long lastBlocks;

    private transient Mine mine;

    public User(String name) {
       this.name = name;
       this.pickaxeLevel = 0;
       this.pickaxeSkin = PickaxeSkin.DEFAULT;
       this.enchantments = Maps.newHashMap();
       this.blocks = 0;
       this.mineRank = 0;
       this.experience = 0;
       this.lastCoins = 0;
       this.lastTokens = 0;
       this.lastBlocks = 0;

    }

    public void addBlocks(long blocks) {
        this.blocks += blocks;
    }

    public int getEnchantmentLevel(EnchantmentType enchantmentType) {
        if (enchantments.containsKey(enchantmentType)) {
            return enchantments.get(enchantmentType);
        }
        return 0;
    }

    public void addEnchantmentLevel(EnchantmentType enchantmentType, int level) {
        if (enchantments.containsKey(enchantmentType)) {
            enchantments.put(enchantmentType, enchantments.get(enchantmentType) + level);
        } else {
            enchantments.put(enchantmentType, level);
        }
    }

    public void removeEnchantment(EnchantmentType enchantmentType) {
        enchantments.remove(enchantmentType);
    }
}
