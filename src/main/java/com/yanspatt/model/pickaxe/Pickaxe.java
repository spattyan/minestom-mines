package com.yanspatt.model.pickaxe;

import com.google.common.collect.Maps;
import lombok.Data;
import net.minestom.server.item.ItemStack;

import java.util.Map;
@Data
public class Pickaxe {

    private PickaxeSkin skin;
    private Map<EnchantmentType,Integer> enchantments;

    private transient ItemStack item;

    private int level;

    public Pickaxe() {
        this.skin = PickaxeSkin.DEFAULT;
        this.level = 0;
        this.enchantments = Maps.newHashMap();
        this.item = null;
    }

    public void addEnchantment(EnchantmentType enchantment, int level) {
        enchantments.put(enchantment, level);
    }

    public void removeEnchantment(EnchantmentType enchantment) {
        enchantments.remove(enchantment);
    }

    public void addEnchantmentLevel(EnchantmentType enchantment, int increment) {
        if (!enchantments.containsKey(enchantment)) {
            enchantments.put(enchantment, increment);
            return;
        }
        enchantments.put(enchantment, enchantments.get(enchantment) + increment);
    }

    public int getEnchantLevel(EnchantmentType enchantment) {
        if (!enchantments.containsKey(enchantment)) {
            return 0;
        }
        return enchantments.get(enchantment);
    }

}
