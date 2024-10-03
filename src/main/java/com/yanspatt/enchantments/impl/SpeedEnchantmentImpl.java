package com.yanspatt.enchantments.impl;

import com.yanspatt.enchantments.BlockHandler;
import com.yanspatt.enchantments.CustomEnchantment;
import com.yanspatt.model.pickaxe.PickaxeEnchantment;
import com.yanspatt.model.user.User;
import net.minestom.server.entity.attribute.Attribute;

public class SpeedEnchantmentImpl extends CustomEnchantment {

    @Override
    public PickaxeEnchantment type() {
        return PickaxeEnchantment.SPEED;
    }

    @Override
    public void blockBreak(User user, BlockHandler handler) {
        long speedMultiplier = user.getPickaxe().getEnchantLevel(type());
        //handler.getPlayer().getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(speedMultiplier == 0 ? 0.1 : speedMultiplier);
    }
}
