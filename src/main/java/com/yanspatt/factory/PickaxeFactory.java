package com.yanspatt.factory;

import com.google.common.collect.Lists;
import com.yanspatt.model.pickaxe.Pickaxe;
import com.yanspatt.model.pickaxe.PickaxeSkin;
import com.yanspatt.model.user.User;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.minestom.server.entity.Player;
import net.minestom.server.item.ItemStack;

import java.util.List;

public class PickaxeFactory {

    public void givePickaxe(User user, Player player) {
        // Give the player a pickaxe

        Pickaxe pickaxe = user.getPickaxe();
        PickaxeSkin skin = pickaxe.getSkin();

        Component prefix = LegacyComponentSerializer.legacyAmpersand().deserialize(skin.getPrefix() + "&7 (" + user.getBlocksMined() + ")");

        ItemStack item = ItemStack.of(user.getPickaxe().getSkin().getIcon())
                .withCustomName(prefix);

        List<Component> lore = Lists.newArrayList();
        String baseColor = user.getPickaxe().getSkin().getBaseColor();
        lore.add(Component.empty());
        lore.add(LegacyComponentSerializer.legacyAmpersand().deserialize(baseColor + "Informações"));
        lore.add(LegacyComponentSerializer.legacyAmpersand().deserialize(baseColor + " ▪ &fNível: &7" + user.getPickaxe().getLevel()));
        lore.add(LegacyComponentSerializer.legacyAmpersand().deserialize(baseColor + " ▪ &fBônus: &7" + user.getPickaxe().getLevel() + "%"));
        lore.add(Component.empty());
        lore.add(LegacyComponentSerializer.legacyAmpersand().deserialize(baseColor + "Encantamentos"));

        user.getPickaxe().getEnchantments().forEach((enchantment, level) -> {
            lore.add(LegacyComponentSerializer.legacyAmpersand().deserialize(baseColor + " ▪ &f" + enchantment.getName() +": &7" + level));
        });
        lore.forEach(component -> component.decoration(TextDecoration.ITALIC, false));

        item = item.withLore(lore);

        player.getInventory().setItemStack(4,item);
    }
}
