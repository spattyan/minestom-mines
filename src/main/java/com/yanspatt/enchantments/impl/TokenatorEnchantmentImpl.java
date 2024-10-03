package com.yanspatt.enchantments.impl;

import com.yanspatt.enchantments.BlockHandler;
import com.yanspatt.enchantments.CustomEnchantment;
import com.yanspatt.model.pickaxe.PickaxeEnchantment;
import com.yanspatt.model.user.User;

public class TokenatorEnchantmentImpl extends CustomEnchantment {

    @Override
    public PickaxeEnchantment type() {
        return PickaxeEnchantment.TOKENATOR;
    }

    @Override
    public void blockBreak(User user, BlockHandler handler) {
        long baseValue = 10;
        long win = (long) (baseValue * (type().getChancePerLevel() * user.getPickaxe().getEnchantLevel(type())));
        user.setTokens(user.getTokens() + win);
    }
}
