package com.yanspatt.loader;

import com.yanspatt.model.pickaxe.EnchantmentType;
import com.yanspatt.model.pickaxe.PickaxeEnchantment;
import com.yanspatt.util.TomlUtils;
import net.minestom.server.item.Material;
import org.tomlj.TomlParseResult;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EnchantmentConfigLoader implements ConfigLoader<List<PickaxeEnchantment>> {

    @Override
    public List<PickaxeEnchantment> loadConfig(String filePath) throws IOException {
        TomlParseResult toml = TomlUtils.loadTomlFile(filePath);

        if (!toml.errors().isEmpty()) return List.of();

        Map<String, PickaxeEnchantment> enchantments = new HashMap<>();

        toml.entryPathSet(true).forEach(entry -> {
            List<String> keys = entry.getKey();
            Object value = entry.getValue();


            if (keys.size() > 1) {
                String enchantmentName = keys.get(0);

                if (!enchantments.containsKey(enchantmentName)) {
                    enchantments.put(enchantmentName, new PickaxeEnchantment());
                }
                PickaxeEnchantment enchantment = enchantments.get(enchantmentName);
                enchantment.setType(EnchantmentType.valueOf(enchantmentName.toUpperCase()));

                String keypath = String.join(".",keys.subList(1, keys.size()));
                System.out.println(keypath + " -> " + value);
                switch (keypath) {
                    case "name":
                        enchantment.setName((String) value);
                        break;
                    case "icon":
                        enchantment.setIcon(Material.fromNamespaceId((String) value));
                        break;
                    case "description":
                        enchantment.setDescription((String) value);
                        break;
                    case "level.default":
                        enchantment.setDefaultLevel(Long.parseLong(value.toString()));
                        break;
                    case "level.maxLevel":
                        enchantment.setMaxLevel(Long.parseLong(value.toString()));
                        break;
                    case "level.percentPerLevel":
                        enchantment.setPercentByLevel(Float.parseFloat(value.toString()));
                        break;
                    case "price.default":
                        enchantment.setDefaultPrice(Long.parseLong(value.toString()));
                        break;
                    case "price.perLevel":
                        enchantment.setPerLevelPrice(Long.parseLong(value.toString()));
                        break;
                    case "price.multiplier":
                        enchantment.setMultiplierPerLevel(Float.parseFloat(value.toString()));
                        break;
                }
            }
        });

        return enchantments.values().stream().toList();
    }
}
