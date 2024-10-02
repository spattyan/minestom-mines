package com.yanspatt.util.inventory.opener;

import com.yanspatt.util.inventory.ClickableItem;
import com.yanspatt.util.inventory.CustomInventory;
import com.yanspatt.util.inventory.contents.InventoryContents;
import net.minestom.server.entity.Player;
import net.minestom.server.inventory.Inventory;
import net.minestom.server.inventory.InventoryType;

public interface InventoryOpener {

    Inventory open(CustomInventory inv, Player player);
    boolean supports(InventoryType type);

    default void fill(Inventory handle, InventoryContents contents) {
        ClickableItem[][] items = contents.all();

        for(int row = 0; row < items.length; row++) {
            for(int column = 0; column < items[row].length; column++) {
                if(items[row][column] != null)
                    handle.setItemStack(9 * row + column, items[row][column].getItem());
            }
        }
    }

}
