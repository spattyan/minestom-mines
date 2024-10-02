package com.yanspatt.factory;

import com.yanspatt.MinesServer;
import com.yanspatt.controller.UserController;
import com.yanspatt.model.pickaxe.Pickaxe;
import com.yanspatt.model.pickaxe.PickaxeSkin;
import com.yanspatt.model.user.User;
import com.yanspatt.util.ItemBuilder;
import net.minestom.server.entity.Player;
import net.minestom.server.item.ItemStack;

public class PickaxeFactory {

    public void givePickaxe(User user, Player player) {
        // Give the player a pickaxe

            Pickaxe pickaxe = user.getPickaxe();
            PickaxeSkin skin = pickaxe.getSkin();
            String baseColor = pickaxe.getSkin().getBaseColor();

            ItemBuilder builder = new ItemBuilder(user.getPickaxe().getSkin().getIcon());
            builder.name(skin.getPrefix() + "&7 (" + user.getBlocksMined() + ")");
            builder.lore(
                    baseColor + "Informações",
                    baseColor + " ▪ &fNível: &7" + pickaxe.getLevel(),
                    baseColor + " ▪ &fBônus: &7" + pickaxe.getLevel() + "%",
                    "",
                    baseColor + "Encantamentos"
            );

            pickaxe.getEnchantments().forEach((enchantment, level) -> {
                builder.addLore(baseColor + " ▪ &f" + enchantment.getName() +": &7" + level);
            });

            ItemStack result = builder.build();
            user.getPickaxe().setItem(result);
            MinesServer.getInstance().getUserController().update(user);

            player.getInventory().setItemStack(4,result);

    }
}
