package com.yanspatt.enchantments.impl;

import com.yanspatt.enchantments.BlockHandler;
import com.yanspatt.enchantments.CustomEnchantment;
import com.yanspatt.model.pickaxe.PickaxeEnchantment;
import com.yanspatt.model.user.User;

public class TokenatorEnchantment extends CustomEnchantment {

    @Override
    public PickaxeEnchantment type() {
        return PickaxeEnchantment.TOKENATOR;
    }

    @Override
    public void blockBreak(User user, BlockHandler handler) {
        user.setTokens(user.getTokens() + (2L *user.getPickaxe().getEnchantLevel(type())));
        handler.getPlayer().sendMessage("Saldo: " + user.getTokens());
    }
}
