package com.yanspatt.enchantments.impl;

import com.yanspatt.enchantments.BlockHandler;
import com.yanspatt.enchantments.CustomEnchantment;
import com.yanspatt.model.pickaxe.EnchantmentType;
import com.yanspatt.model.pickaxe.PickaxeEnchantment;
import com.yanspatt.model.user.User;
import net.minestom.server.potion.Potion;
import net.minestom.server.potion.PotionEffect;

public class SpeedEnchantmentImpl extends CustomEnchantment {

    @Override
    public EnchantmentType type() {
        return EnchantmentType.SPEED;
    }

    @Override
    public void blockBreak(User user, PickaxeEnchantment enchantment, BlockHandler handler) {
        long speedMultiplier = user.getEnchantments().get(type());
        handler.getPlayer().addEffect(new Potion(PotionEffect.SPEED,(byte) speedMultiplier,100));
        //handler.getPlayer().getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(speedMultiplier == 0 ? 0.1 : speedMultiplier);
    }
}
