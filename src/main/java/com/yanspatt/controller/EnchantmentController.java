package com.yanspatt.controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yanspatt.enchantments.CustomEnchantment;
import com.yanspatt.enchantments.impl.*;
import com.yanspatt.model.pickaxe.PickaxeEnchantment;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EnchantmentController {

    private Map<PickaxeEnchantment, CustomEnchantment> enchantments;

    public EnchantmentController() {
        enchantments = Maps.newHashMap();
        List<CustomEnchantment> enchants = Lists.newArrayList(
                new AcidEnchantmentImpl(),
                new AlchemistEnchantmentImpl(),
                new DestructorEnchantmentImpl(),
                new ExperienceEnchantmentImpl(),
                new KeychainEnchantmentImpl(),
                new MeteorEnchantmentImpl(),
                new NuclearEnchantmentImpl(),
                new SpeedEnchantmentImpl(),
                new TokenatorEnchantmentImpl()
        );

        enchants.forEach(enchantment -> {enchantments.put(enchantment.type(),enchantment);});
    }

    public Map<PickaxeEnchantment, CustomEnchantment> getEnchantments() {
        return enchantments;
    }

    public List<CustomEnchantment> getEnchantmentsList() {
        return getEnchantments().values().stream().map(enchantments::get).collect(Collectors.toList());
    }
}
