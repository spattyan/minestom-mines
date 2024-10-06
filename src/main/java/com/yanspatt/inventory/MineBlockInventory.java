package com.yanspatt.inventory;

import com.google.common.collect.Lists;
import com.yanspatt.MinesServer;
import com.yanspatt.util.ItemBuilder;
import com.yanspatt.util.inventory.ClickableItem;
import com.yanspatt.util.inventory.CustomInventory;
import com.yanspatt.util.inventory.contents.InventoryContents;
import com.yanspatt.util.inventory.contents.InventoryProvider;
import net.minestom.server.entity.Player;
import net.minestom.server.instance.block.Block;
import net.minestom.server.item.Material;

import java.util.List;

public class MineBlockInventory implements InventoryProvider {

    public static final CustomInventory INVENTORY = CustomInventory.builder()
            .id("mine-block")
            .provider(new MineBlockInventory())
            .size(6,9)
            .title("&7Mina - Blocks")
            .build();

    private int[] slots = new int[] {
            10,11,12,13,14,15,16,
            19,20, 21,22,23,24,25,
            28,29,30,31,32,33,34,
            37,38,39,40,41,42,43
    } ;

    @Override
    public void init(Player player, InventoryContents contents) {
        MinesServer.getInstance().getUserRedisRepository().get(player.getUsername()).ifPresent(user -> {
            contents.set(5,0, ClickableItem.of(new ItemBuilder(Material.ARROW).name("&aVoltar").build(), event -> {
                PickaxeUpgradeInventory.INVENTORY.open(player);
            }));


            List<Block> blocks = Lists.newArrayList(
                    Block.STONE,
                    Block.GRANITE,
                    Block.DIORITE,
                    Block.ANDESITE,
                    Block.COAL_ORE,
                    Block.IRON_ORE,
                    Block.GOLD_ORE,
                    Block.LAPIS_ORE,
                    Block.REDSTONE_ORE,
                    Block.DIAMOND_ORE,
                    Block.EMERALD_ORE,
                    Block.COAL_BLOCK,
                    Block.IRON_BLOCK,
                    Block.GOLD_BLOCK,
                    Block.LAPIS_BLOCK,
                    Block.REDSTONE_BLOCK,
                    Block.DIAMOND_BLOCK,
                    Block.EMERALD_BLOCK,
                    Block.SPONGE,
                    Block.COBBLESTONE,
                    Block.COARSE_DIRT,
                    Block.ACACIA_LOG,
                    Block.JUNGLE_LOG,
                    Block.OAK_LOG,
                    Block.BEACON,
                    Block.OBSIDIAN,
                    Block.ICE

            );

            int index = 0;

            for (Block block : blocks) {
                int slot = slots[index];
                int row = slot / 9;
                int column = slot % 9;
                contents.set(row,column,ClickableItem.of(new ItemBuilder(Material.PAPER).name("&fBloco: &a" +block.name()).build(), event -> {
                    //user.getMine().setBlock(block);
                    MinesServer.getInstance().getMineFactory().populateMine(user,block,true);
                    INVENTORY.open(player);
                }));
                index++;
            }

            contents.set(5,4, ClickableItem.of(new ItemBuilder(Material.BARRIER).name("&aResetar").lore("","&fBloco atual: &a").build(), event -> {
                /*if (user.getMine().getBlock() == null) {
                    player.sendMessage("ESCOLHA UM BLOCO ANTES!!");
                } else {
                    MinesServer.getInstance().getMineFactory().sendMine(user,player);
                    player.closeInventory();
                }*/

            }));

            contents.set(5,3, ClickableItem.of(new ItemBuilder(Material.RED_TERRACOTTA).name("&cDiminuir tamanho").build(), event -> {
                /*user.getMine().setSize(user.getMine().getSize()-1);
                MinesServer.getInstance().getMineFactory().populateMine(user,user.getMine().getBlock(),true);
*/
            }));

            contents.set(5,5, ClickableItem.of(new ItemBuilder(Material.GREEN_TERRACOTTA).name("&aAumentar tamanho").build(), event -> {
                /*boolean result =user.getMine().setSize(user.getMine().getSize()+1);
                if (result) {
                    MinesServer.getInstance().getMineFactory().populateMine(user,user.getMine().getBlock(),true);
                }*/

            }));
        });
    }

    public boolean canBuy(long price, long balance) {
        return balance >= price;
    }

    @Override
    public void update(Player player, InventoryContents contents) {
        InventoryProvider.super.update(player, contents);
    }

}
