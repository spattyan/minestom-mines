package com.yanspatt.inventory;

import com.yanspatt.MinesServer;
import com.yanspatt.model.pickaxe.PickaxeSkin;
import com.yanspatt.util.ItemBuilder;
import com.yanspatt.util.inventory.ClickableItem;
import com.yanspatt.util.inventory.CustomInventory;
import com.yanspatt.util.inventory.contents.InventoryContents;
import com.yanspatt.util.inventory.contents.InventoryProvider;
import net.minestom.server.entity.Player;
import net.minestom.server.inventory.InventoryType;
import net.minestom.server.item.Material;

import java.util.List;

public class PickaxeSkinInventory implements InventoryProvider {

    public static final CustomInventory INVENTORY = CustomInventory.builder()
            .id("pickaxe-skin")
            .provider(new PickaxeSkinInventory())
            .size(3,9)
            .title("&7Picareta - Skins")
            .build();

    private int[] skinsSlots = new int[] {
            10, 11, 12,
            13,14,15,
            16
    };

    @Override
    public void init(Player player, InventoryContents contents) {
        MinesServer.getInstance().getUserController().getUser(player.getUsername()).ifPresent(user -> {

            contents.set(2,0, ClickableItem.of(new ItemBuilder(Material.ARROW).name("&aVoltar").build(), event -> {
               PickaxeUpgradeInventory.INVENTORY.open(player);
            }));

            int i = 0;
            for (PickaxeSkin value : PickaxeSkin.values()) {
                int slot = skinsSlots[i];
                int row = slot / 9;
                int column = slot % 9;

                contents.set(row,column,ClickableItem.of(

                        new ItemBuilder(value.getIcon()).name("&7Skin: " + value.getPrefix()).build(),
                        event -> {
                            PickaxeSkin skin = value;
                            user.getPickaxe().setSkin(skin);
                            event.getPlayer().sendMessage("Applied skin " + user.getPickaxe().getSkin());
                            MinesServer.getInstance().getPickaxeFactory().givePickaxe(user,player);
                            PickaxeUpgradeInventory.INVENTORY.open(player);
                        }));
                i++;
            }


        });
    }
}
