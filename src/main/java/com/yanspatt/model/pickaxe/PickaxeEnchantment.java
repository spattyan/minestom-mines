package com.yanspatt.model.pickaxe;

import lombok.Getter;
import net.minestom.server.item.Material;

@Getter
public enum PickaxeEnchantment {

    FORTUNE(Material.DIAMOND, "Fortuna","Este encantamento faz com que você ganhe mais coins na mineração.", 1,0.1f,100),
    TOKENATOR(Material.SUNFLOWER, "Tokenator","Este encantamento faz com que você ganhe mais tokens na mineração.", 1,0.1f,100),
    EXPERIENCE(Material.EXPERIENCE_BOTTLE, "Experiente","Este encantamento faz com que você ganhe mais XP na mineração.",1,0.1f, 100),
    KEYCHAIN(Material.TRIPWIRE_HOOK, "Chaveiro","Este encantamento faz com que você ganhe chaves na mineração.",1,1f, 100),
    DESTRUCTOR(Material.TNT, "Destruidor","Este encantamento faz com que você quebre uma camada inteira.",1,0.1f, 100),
    ALCHEMIST(Material.GOLD_BLOCK, "Alquimista","Este encantamento faz com que você transforme a mina em ouro",1,0.1f, 100),
    METEOR(Material.FIRE_CHARGE, "Meteoro","Este encantamento faz com que você crie meteoros na área de mina.",1,0.1f, 100),
    NUCLEAR(Material.SLIME_BALL, "Nuclear","Este encantamento faz com que você quebre a mina por completo.",1,0.1f, 100),
    SPEED(Material.SUGAR, "Velocidade","Este encantamento faz com que você ganhe velocidade ao minerar.",10000,100f, 3),
    ACID(Material.EMERALD, "Ácido","Partículas de ácido surgem e destroem uma camada.",100,0.1f, 100);


    private Material icon;
    private String name;
    private String description;
    private long pricePerLevel;
    private float chancePerLevel;
    private int maxLevel;

    PickaxeEnchantment(Material icon, String name, String description, long pricePerLevel, float chancePerLevel, int maxLevel) {
        this.icon = icon;
        this.name = name;
        this.pricePerLevel = pricePerLevel;
        this.chancePerLevel = chancePerLevel;
        this.description = description;
        this.maxLevel = maxLevel;
    }
}
