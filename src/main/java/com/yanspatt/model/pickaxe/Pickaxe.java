package com.yanspatt.model.pickaxe;

import com.google.common.collect.Maps;
import lombok.Data;

import java.util.Map;
import java.util.UUID;

@Data
public class Pickaxe {

    private String owner;

    private UUID uuid;

    private PickaxeSkin skin;
    private Map<PickaxeEnchantment,Integer> enchantments;

    private int level;

    public Pickaxe() {
        this.skin = PickaxeSkin.DEFAULT;
        this.level = 0;
        this.uuid = UUID.randomUUID();
        this.enchantments = Maps.newHashMap();
    }

    public void addEnchantment(PickaxeEnchantment enchantment, int level) {
        enchantments.put(enchantment, level);
    }

    public void removeEnchantment(PickaxeEnchantment enchantment) {
        enchantments.remove(enchantment);
    }

    public void addEnchantmentLevel(PickaxeEnchantment enchantment, int increment) {
        if (!enchantments.containsKey(enchantment)) {
            enchantments.put(enchantment, increment);
            return;
        }
        enchantments.compute(enchantment, (k, level) -> level + increment);
    }

    public int getEnchantLevel(PickaxeEnchantment enchantment) {
        if (!enchantments.containsKey(enchantment)) {
            return 0;
        }
        return enchantments.get(enchantment);
    }
}
