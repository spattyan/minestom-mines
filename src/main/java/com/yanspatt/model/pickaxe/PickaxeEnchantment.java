package com.yanspatt.model.pickaxe;

import lombok.Getter;
import net.minestom.server.item.Material;

@Getter
public enum PickaxeEnchantment {

    FORTUNE(Material.DIAMOND, "Fortuna", 100),
    EFFICIENCY(Material.GOLDEN_PICKAXE, "Efiencia", 100),
    LAYER_BREAKER(Material.BARRIER, "Quebrador de Camada", 100),
    TOKENATOR(Material.SUNFLOWER, "Tokenator", 100),
    DRILL(Material.IRON_BARS, "Furadeira", 100),
    NUCLEAR(Material.SLIME_BALL, "Radioativo", 100);



    private Material icon;
    private String name;
    private int maxLevel;

    PickaxeEnchantment(Material icon, String name, int maxLevel) {
        this.icon = icon;
        this.name = name;
        this.maxLevel = maxLevel;
    }
}
