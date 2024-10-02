package com.yanspatt.inventory;

import com.yanspatt.MinesServer;
import com.yanspatt.model.pickaxe.PickaxeEnchantment;
import com.yanspatt.util.ItemBuilder;
import com.yanspatt.util.inventory.ClickableItem;
import com.yanspatt.util.inventory.CustomInventory;
import com.yanspatt.util.inventory.contents.InventoryContents;
import com.yanspatt.util.inventory.contents.InventoryProvider;
import net.minestom.server.entity.Player;
import net.minestom.server.item.Material;

public class PickaxeUpgradeInventory implements InventoryProvider {

    public static final CustomInventory INVENTORY = CustomInventory.builder()
            .id("pickaxe-upgrade")
            .provider(new PickaxeUpgradeInventory())
            .size(6,9)
            .title("&7Picareta - Encantamentos")
            .build();

    private int[] upgradeSlots = new int[] {
            19, 20, 21,
            28,29,30,
            37,38,89
    } ;

    @Override
    public void init(Player player, InventoryContents contents) {
        MinesServer.getInstance().getUserController().getUser(player.getUsername()).ifPresent(user -> {
            contents.set(0,4, ClickableItem.of(new ItemBuilder(Material.PLAYER_HEAD).skullOwner(player).name("&aSeu Perfil").build(), event -> {
                event.getPlayer().sendMessage("Seu Perfil");
            }));

            contents.set(3,6,ClickableItem.of(new ItemBuilder(Material.NAME_TAG).name("&bSkins de Picareta").build(), event -> {
               PickaxeSkinInventory.INVENTORY.open(player);
            }));

            int i = 0;
            for (PickaxeEnchantment value : PickaxeEnchantment.values()) {
                int slot = upgradeSlots[i];
                int row = slot / 9;
                int column = slot % 9;
                contents.set(row,column,ClickableItem.of(
                        new ItemBuilder(value.getIcon()).name(value.getName()).build(),
                        event -> {
                            PickaxeEnchantment enchantment = value;
                            user.getPickaxe().addEnchantmentLevel(enchantment, 1);
                            event.getPlayer().sendMessage("Applied enchantment " + enchantment.getName());
                            MinesServer.getInstance().getPickaxeFactory().givePickaxe(user,player);
                            init(player, contents);
                        }));
                i++;
            }

            contents.set(2,6,ClickableItem.of(user.getPickaxe().getItem(), event -> {
                event.getPlayer().sendMessage("pickaxe!");
            }));
        });
    }

    @Override
    public void update(Player player, InventoryContents contents) {
        InventoryProvider.super.update(player, contents);
    }

}
