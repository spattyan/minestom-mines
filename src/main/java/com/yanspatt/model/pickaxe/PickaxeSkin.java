package com.yanspatt.model.pickaxe;

import lombok.Getter;
import net.minestom.server.item.Material;

@Getter
public enum PickaxeSkin {

    DEFAULT("default_skin","Skin Padrão", Material.WOODEN_PICKAXE),
    IRON("iron_skin","Skin Ferro", Material.IRON_PICKAXE),
    DIAMOND("diamond_skin","Skin Diamante", Material.DIAMOND_PICKAXE),
    BOB("bob_skin","Skin BOB", Material.PLAYER_HEAD),

    ;



    private String name;
    private String prefix;
    private Material icon;

    PickaxeSkin(String name, String prefix, Material icon) {
        this.icon = icon;
        this.name = name;
        this.prefix = prefix;
    }
}
