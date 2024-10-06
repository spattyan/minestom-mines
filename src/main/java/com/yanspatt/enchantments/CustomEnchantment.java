package com.yanspatt.enchantments;

import com.yanspatt.model.pickaxe.EnchantmentType;
import com.yanspatt.model.pickaxe.PickaxeEnchantment;
import com.yanspatt.model.user.User;

public abstract class CustomEnchantment {

    public abstract EnchantmentType type();

    public abstract void blockBreak(User user, PickaxeEnchantment enchantment, BlockHandler handler);
}
