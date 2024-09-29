package com.yanspatt.model.pickaxe;

import lombok.Getter;
import net.minestom.server.item.Material;

@Getter
public enum PickaxeEnchantment {

    FORTUNE(Material.DIAMOND, "Fortune", 100),
    EFFICIENCY(Material.GOLDEN_PICKAXE, "Efficiency", 100),
    LAYER_BREAKER(Material.BARRIER, "Layer Breaker", 100),
    TOKENATOR(Material.SUNFLOWER, "Tokenator", 100);



    private Material icon;
    private String name;
    private int maxLevel;

    PickaxeEnchantment(Material icon, String name, int maxLevel) {
        this.icon = icon;
        this.name = name;
        this.maxLevel = maxLevel;
    }
}
