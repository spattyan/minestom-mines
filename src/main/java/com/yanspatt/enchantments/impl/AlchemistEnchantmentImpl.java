package com.yanspatt.enchantments.impl;

import com.yanspatt.MinesServer;
import com.yanspatt.enchantments.BlockHandler;
import com.yanspatt.enchantments.CustomEnchantment;
import com.yanspatt.model.pickaxe.PickaxeEnchantment;
import com.yanspatt.model.user.User;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.instance.block.Block;

import java.util.Random;

public class AlchemistEnchantmentImpl extends CustomEnchantment {

    @Override
    public PickaxeEnchantment type() {
        return PickaxeEnchantment.ALCHEMIST;
    }

    @Override
    public void blockBreak(User user, BlockHandler handler) {
        final Random random = new Random();
        int randomNumber = random.nextInt(10000) + 1;

        if (randomNumber <= ((type().getChancePerLevel() * user.getPickaxe().getEnchantLevel(type())) * 100)) {
            handler.getPlayer().sendMessage(Component.text("A MINA VIROU OURO!").color(NamedTextColor.GOLD));
            MinesServer.getInstance().getMineFactory().populateMine(user, Block.GOLD_BLOCK,false);
            MinesServer.getInstance().getMineFactory().sendMine(user, handler.getPlayer());
        }
    }
}
