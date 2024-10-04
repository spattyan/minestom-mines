package com.yanspatt.factory;

import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import com.yanspatt.MinesServer;
import com.yanspatt.controller.UserController;
import com.yanspatt.model.pickaxe.Pickaxe;
import com.yanspatt.model.pickaxe.PickaxeSkin;
import com.yanspatt.model.user.User;
import com.yanspatt.util.BigNumbers;
import com.yanspatt.util.ItemBuilder;
import net.kyori.adventure.text.Component;
import net.minestom.server.entity.Player;
import net.minestom.server.instance.block.Block;
import net.minestom.server.item.ItemComponent;
import net.minestom.server.item.ItemStack;

import java.util.concurrent.TimeUnit;

public class PickaxeFactory {

        public void givePickaxe(User user, Player player) {
        // Give the player a pickaxe
            Stopwatch stopwatch = Stopwatch.createStarted();
            Pickaxe pickaxe = user.getPickaxe();
            PickaxeSkin skin = pickaxe.getSkin();

            String baseColor = pickaxe.getSkin().getBaseColor();

            ItemBuilder builder = new ItemBuilder(user.getPickaxe().getSkin().getIcon());
            builder.name(skin.getPrefix() + "&7 (" + BigNumbers.format(user.getBlocksMined()) + ")");
            builder.lore(
                    baseColor + "Informações",
                    baseColor + " ▪ &fNível: &7" + pickaxe.getLevel(),
                    baseColor + " ▪ &fBônus: &7" + pickaxe.getLevel() + "%",
                    "",
                    baseColor + "Encantamentos"
            );

            builder.instaBreak();
            builder.canBreak(user.getMine().getBlock(), Block.GOLD_BLOCK);
            pickaxe.getEnchantments().forEach((enchantment, level) -> {
                builder.addLore(baseColor + " ▪ &f" + enchantment.getName() +": &7" + level);
            });

            ItemStack result = builder.build();
            stopwatch.stop();
            long elapsed = stopwatch.elapsed(TimeUnit.MILLISECONDS);
            if (elapsed > 0) {
                System.out.println("Time elapsed (BUILD PICKAXE): " + stopwatch.elapsed(TimeUnit.MILLISECONDS) + "ms");
            }
            if (user.getPickaxe().getItem() != null && user.getPickaxe().getItem().isSimilar(result)) {
                return;
            }

            user.getPickaxe().setItem(result);
            MinesServer.getInstance().getUserController().update(user);

            player.getInventory().setItemStack(4,result);

    }
}
