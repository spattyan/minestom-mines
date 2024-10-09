package com.yanspatt.factory;

import com.google.common.base.Stopwatch;
import com.yanspatt.MinesServer;
import com.yanspatt.model.pickaxe.PickaxeSkin;
import com.yanspatt.model.user.User;
import com.yanspatt.util.BigNumbers;
import com.yanspatt.util.ItemBuilder;
import net.minestom.server.entity.Player;
import net.minestom.server.item.ItemStack;

import java.util.concurrent.TimeUnit;

public class PickaxeFactory {

        public void givePickaxe(User user, Player player) {
        // Give the player a pickaxe
            Stopwatch stopwatch = Stopwatch.createStarted();
            PickaxeSkin skin = user.getPickaxeSkin();

            String baseColor = skin.getBaseColor();

            ItemBuilder builder = new ItemBuilder(skin.getIcon());
            builder.name(skin.getPrefix() + "&7 (" + BigNumbers.format(user.getBlocks()) + ")");
            builder.lore(
                    baseColor + "Informações",
                    baseColor + " ▪ &fNível: &7" + user.getPickaxeLevel(),
                    baseColor + " ▪ &fBônus: &7" + 0 + "%",
                    "",
                    baseColor + "Encantamentos"
            );

            builder.instaBreak();
            //builder.canBreak(user.getMine().getBlock(), Block.GOLD_BLOCK);
            user.getEnchantments().forEach((enchantment, level) -> {
                builder.addLore(baseColor + " ▪ &f" + MinesServer.getInstance().getEnchantmentService().getEnchantment(enchantment).getName() +": &7" + level);
            });

            ItemStack result = builder.build();
            stopwatch.stop();
            long elapsed = stopwatch.elapsed(TimeUnit.MILLISECONDS);
            if (elapsed > 0) {
                System.out.println("Time elapsed (BUILD PICKAXE): " + stopwatch.elapsed(TimeUnit.MILLISECONDS) + "ms");
            }

            player.getInventory().setItemStack(4,result);

    }
}
