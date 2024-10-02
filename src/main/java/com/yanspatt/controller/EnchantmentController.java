package com.yanspatt.controller;

import com.google.common.collect.Maps;
import com.yanspatt.enchantments.CustomEnchantment;
import com.yanspatt.enchantments.impl.TokenatorEnchantment;
import com.yanspatt.model.pickaxe.PickaxeEnchantment;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EnchantmentController {

    private Map<PickaxeEnchantment, CustomEnchantment> enchantments;

    public EnchantmentController() {
        enchantments = Maps.newHashMap();
        enchantments.put(PickaxeEnchantment.TOKENATOR,new TokenatorEnchantment());
    }

    public Map<PickaxeEnchantment, CustomEnchantment> getEnchantments() {
        return enchantments;
    }

    public List<CustomEnchantment> getEnchantmentsList() {
        return getEnchantments().values().stream().map(enchantments::get).collect(Collectors.toList());
    }
}
