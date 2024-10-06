package com.yanspatt.enchantments.impl;

import com.yanspatt.MinesServer;
import com.yanspatt.enchantments.BlockHandler;
import com.yanspatt.enchantments.CustomEnchantment;
import com.yanspatt.model.pickaxe.EnchantmentType;
import com.yanspatt.model.pickaxe.PickaxeEnchantment;
import com.yanspatt.model.user.User;
import com.yanspatt.util.Probability;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.minestom.server.instance.block.Block;

import java.util.Random;

public class AlchemistEnchantmentImpl extends CustomEnchantment {

    @Override
    public EnchantmentType type() {
        return EnchantmentType.ALCHEMIST;
    }

    @Override
    public void blockBreak(User user, PickaxeEnchantment enchantment, BlockHandler handler) {
        /*
        if (Probability.probability(enchantment.getPercentByLevel() * user.getPickaxe().getEnchantLevel(type()))) {
            handler.getPlayer().sendMessage(Component.text("A MINA VIROU OURO!").color(NamedTextColor.GOLD));
            MinesServer.getInstance().getMineFactory().populateMine(user, Block.GOLD_BLOCK,false);
            MinesServer.getInstance().getMineFactory().sendMine(user, handler.getPlayer());
        }*/
    }
}
