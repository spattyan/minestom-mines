package com.yanspatt.model.pickaxe;

import lombok.Getter;
import net.kyori.adventure.text.format.NamedTextColor;
import net.minestom.server.item.Material;

@Getter
public enum PickaxeSkin {

    DEFAULT("default_skin","&a", "&a&lPICARETA DE MADEIRA", Material.WOODEN_PICKAXE),
    STONE("stone_skin","&8", "&8&lPICARETA DE PEDRA", Material.STONE_PICKAXE),
    IRON("iron_skin","&f", "&f&lPICARETA DE FERRO", Material.IRON_PICKAXE),
    GOLD("iron_skin","&e", "&e&lPICARETA DE OURO", Material.GOLDEN_PICKAXE),
    DIAMOND("diamond_skin","&b", "&b&lPICARETA DE DIAMANTE", Material.DIAMOND_PICKAXE),
    BOB("bob_skin","&6", "&6&lPICARETA LENDARIA", Material.PLAYER_HEAD),

    ;


    private String name;
    private String prefix;
    private String baseColor;
    private Material icon;


    PickaxeSkin(String name,String baseColor, String prefix, Material icon) {
        this.icon = icon;
        this.name = name;
        this.prefix = prefix;
        this.baseColor = baseColor;
    }
}
