package com.yanspatt.util.inventory.contents;

import com.yanspatt.util.inventory.ClickableItem;
import com.yanspatt.util.inventory.CustomInventory;

import java.util.Optional;

public interface InventoryContents {

    CustomInventory inventory();

    ClickableItem[][] all();

    Optional<ClickableItem> get(int row, int column);
    Optional<ClickableItem> get(SlotPos slotPos);

    InventoryContents set(int row, int column, ClickableItem item);
    InventoryContents set(SlotPos slotPos, ClickableItem item);

    InventoryContents add(ClickableItem item);

    class Impl implements InventoryContents {

        private CustomInventory inv;
        private String player;

        private ClickableItem[][] contents;

        public Impl(CustomInventory inv, String player) {
            this.inv = inv;
            this.player = player;
            this.contents = new ClickableItem[inv.getRows()][inv.getColumns()];
        }

        @Override
        public CustomInventory inventory() { return inv; }


        @Override
        public ClickableItem[][] all() { return contents; }

        @Override
        public Optional<ClickableItem> get(int row, int column) {
            if(row >= contents.length)
                return Optional.empty();
            if(column >= contents[row].length)
                return Optional.empty();

            return Optional.ofNullable(contents[row][column]);
        }

        @Override
        public Optional<ClickableItem> get(SlotPos slotPos) {
            return get(slotPos.getRow(), slotPos.getColumn());
        }

        @Override
        public InventoryContents set(int row, int column, ClickableItem item) {
            if(row >= contents.length)
                return this;
            if(column >= contents[row].length)
                return this;

            contents[row][column] = item;
            return this;
        }

        @Override
        public InventoryContents set(SlotPos slotPos, ClickableItem item) {
            return set(slotPos.getRow(), slotPos.getColumn(), item);
        }

        @Override
        public InventoryContents add(ClickableItem item) {
            for(int row = 0; row < contents.length; row++) {
                for(int column = 0; column < contents[0].length; column++) {
                    if(contents[row][column] == null) {
                        set(row, column, item);
                        return this;
                    }
                }
            }

            return this;
        }

    }
}
