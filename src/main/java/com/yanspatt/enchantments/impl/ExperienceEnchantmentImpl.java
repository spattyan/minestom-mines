package com.yanspatt.enchantments.impl;

import com.yanspatt.enchantments.BlockHandler;
import com.yanspatt.enchantments.CustomEnchantment;
import com.yanspatt.model.pickaxe.PickaxeEnchantment;
import com.yanspatt.model.user.User;

public class ExperienceEnchantmentImpl extends CustomEnchantment {

    @Override
    public PickaxeEnchantment type() {
        return PickaxeEnchantment.EXPERIENCE;
    }

    @Override
    public void blockBreak(User user, BlockHandler handler) {
        long baseValue = 1;
        long increment = (long) ((type().getChancePerLevel()*user.getPickaxe().getEnchantLevel(type())) / baseValue);
        user.setXp(user.getXp() + (baseValue + increment));
    }
}
