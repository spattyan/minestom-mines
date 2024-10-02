package com.yanspatt.util.inventory.opener;

import com.google.common.base.Preconditions;
import com.yanspatt.util.inventory.CustomInventory;
import com.yanspatt.util.inventory.InventoryManager;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.minestom.server.entity.Player;
import net.minestom.server.inventory.Inventory;
import net.minestom.server.inventory.InventoryType;

import java.util.Arrays;

public class ChestInventoryOpener implements InventoryOpener {

    @Override
    public Inventory open(CustomInventory inv, Player player) {
        Preconditions.checkArgument(inv.getColumns() == 9,
                "The column count for the chest inventory must be 9, found: %s.", inv.getColumns());
        Preconditions.checkArgument(inv.getRows() >= 1 && inv.getRows() <= 6,
                "The row count for the chest inventory must be between 1 and 6, found: %s", inv.getRows());

        InventoryManager manager = inv.getManager();
        Inventory handle = new Inventory(Arrays.stream(InventoryType.values()).filter(inventory -> inventory.getSize() == inv.getRows() * inv.getColumns()).findFirst().orElse(InventoryType.CHEST_6_ROW), LegacyComponentSerializer.legacyAmpersand().deserialize(inv.getTitle()).decoration(TextDecoration.ITALIC, false));

        fill(handle, manager.getContents(player).get());

        player.openInventory(handle);
        return handle;
    }

    @Override
    public boolean supports(InventoryType type) {
        return type == InventoryType.CHEST_6_ROW || type == InventoryType.CHEST_6_ROW;
    }

}
