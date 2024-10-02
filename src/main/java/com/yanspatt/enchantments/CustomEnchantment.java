package com.yanspatt.enchantments;

import com.yanspatt.model.pickaxe.PickaxeEnchantment;
import com.yanspatt.model.user.User;

public abstract class CustomEnchantment {

    public abstract PickaxeEnchantment type();

    public abstract void blockBreak(User user, BlockHandler handler);
}
