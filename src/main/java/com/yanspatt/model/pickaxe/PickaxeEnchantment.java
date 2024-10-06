package com.yanspatt.model.pickaxe;

import lombok.Data;
import net.minestom.server.item.Material;

@Data
public class PickaxeEnchantment {

    private EnchantmentType type;
    private String name;
    private Material icon;
    private String description;

    private long defaultLevel;
    private long maxLevel;
    private float percentByLevel;

    private long defaultPrice;
    private long perLevelPrice;
    private float multiplierPerLevel;

}
