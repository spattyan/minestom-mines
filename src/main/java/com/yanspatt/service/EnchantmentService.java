package com.yanspatt.service;

import com.yanspatt.MinesServer;
import com.yanspatt.loader.EnchantmentConfigLoader;
import com.yanspatt.model.pickaxe.EnchantmentType;
import com.yanspatt.model.pickaxe.PickaxeEnchantment;

import java.io.IOException;
import java.util.List;

public class EnchantmentService {

    private static final String ENCHANTMENTS_FILE = "config/enchantments.toml";

    private final List<PickaxeEnchantment> enchantments;

    public EnchantmentService() throws IOException {
        this.enchantments = MinesServer.getInstance().getConfigManager().getConfig("enchantments", new EnchantmentConfigLoader(), ENCHANTMENTS_FILE);
    }

    public PickaxeEnchantment getEnchantment(String name) {
        return enchantments.stream()
                .filter(e -> e.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    public PickaxeEnchantment getEnchantment(EnchantmentType type) {
        return enchantments.stream()
                .filter(e -> e.getType() == type)
                .findFirst()
                .orElse(null);
    }
}
