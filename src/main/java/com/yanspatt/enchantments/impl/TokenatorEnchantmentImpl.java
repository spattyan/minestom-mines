package com.yanspatt.enchantments.impl;

import com.yanspatt.MinesServer;
import com.yanspatt.enchantments.BlockHandler;
import com.yanspatt.enchantments.CustomEnchantment;
import com.yanspatt.model.pickaxe.EnchantmentType;
import com.yanspatt.model.pickaxe.PickaxeEnchantment;
import com.yanspatt.model.user.User;

public class TokenatorEnchantmentImpl extends CustomEnchantment {

    @Override
    public EnchantmentType type() {
        return EnchantmentType.TOKENATOR;
    }

    @Override
    public void blockBreak(User user, PickaxeEnchantment enchantment, BlockHandler handler) {
        /*long baseValue = 10;
        long win = (long) (baseValue * (MinesServer.getInstance().getEnchantmentService().getEnchantment(type()).getMultiplierPerLevel() * user.getPickaxe().getEnchantLevel(type())));
        user.setTokens(user.getTokens() + win);*/
    }
}
