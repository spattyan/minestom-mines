package com.yanspatt.inventory;

import com.yanspatt.MinesServer;
import com.yanspatt.model.pickaxe.PickaxeEnchantment;
import com.yanspatt.util.BigNumbers;
import com.yanspatt.util.ItemBuilder;
import com.yanspatt.util.inventory.ClickableItem;
import com.yanspatt.util.inventory.CustomInventory;
import com.yanspatt.util.inventory.contents.InventoryContents;
import com.yanspatt.util.inventory.contents.InventoryProvider;
import com.yanspatt.util.inventory.contents.SlotPos;
import net.minestom.server.entity.Player;
import net.minestom.server.inventory.click.ClickType;
import net.minestom.server.item.Material;

public class PickaxeUpgradeInventory implements InventoryProvider {

    public static final CustomInventory INVENTORY = CustomInventory.builder()
            .id("pickaxe-upgrade")
            .provider(new PickaxeUpgradeInventory())
            .size(6,9)
            .title("&7Picareta - Encantamentos")
            .build();

    private int[] upgradeSlots = new int[] {
            19, 20, 21, 22,
            28,29,30, 31,
            37,38,39, 40
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
            contents.set(4,6,ClickableItem.of(new ItemBuilder(Material.STONE).name("&bBlocos de Mina").build(), event -> {
                MineBlockInventory.INVENTORY.open(player);
            }));

            contents.set(2,6,ClickableItem.of(user.getPickaxe().getItem(), event -> {
                event.getPlayer().sendMessage("pickaxe!");
            }));

            int i = 0;
            for (PickaxeEnchantment value : PickaxeEnchantment.values()) {
                int slot = upgradeSlots[i];
                int row = slot / 9;
                int column = slot % 9;
                int enchantLevel = user.getPickaxe().getEnchantLevel(value);
                contents.set(row,column,ClickableItem.of(
                        new ItemBuilder(value.getIcon()).name("&a" + value.getName() + " &8[&f" + enchantLevel + " &7➟ &f" + (enchantLevel +1) + "&8]")
                                .lore("&7" + value.getDescription(),
                                        "",
                                        "&f Custo: &c" + BigNumbers.format(value.getPricePerLevel() * (user.getPickaxe().getEnchantLevel(value) == 0 ? 1 : user.getPickaxe().getEnchantLevel(value))) + " Tokens",
                                        "&f Nível: &7" + enchantLevel + "&8/&7" + value.getMaxLevel() + " &8[0%]",
                                        "&f Chance Atual: &7" + (value.getChancePerLevel() *(enchantLevel +1)) + "%",
                                        "",
                                        "&f Botão Esquerdo: &aUpar 1 nível",
                                        "&f Botão Direito: &cZERAR ENCANTAMENTO",
                                        "&f Shift + Clique: &aUpar o possível"
                                        )
                                .build(),
                        event -> {
                            PickaxeEnchantment enchantment = value;

                            if (user.getPickaxe().getEnchantLevel(enchantment) >= enchantment.getMaxLevel()) {
                                //return;
                            }
                            int currentLevel = user.getPickaxe().getEnchantLevel(enchantment) == 0 ? 1 : user.getPickaxe().getEnchantLevel(enchantment);
                            long price = enchantment.getPricePerLevel() * currentLevel;
                            if (!canBuy(price, user.getTokens())) return;

                            if (event.getClickType() == ClickType.LEFT_CLICK) {
                                if (canBuy(price, user.getTokens())) {
                                    user.setTokens(user.getTokens() - price);
                                    user.getPickaxe().addEnchantmentLevel(enchantment, 1);
                                    event.getPlayer().sendMessage("Applied enchantment " + enchantment.getName());
                                    MinesServer.getInstance().getPickaxeFactory().givePickaxe(user, player);
                                    INVENTORY.open(player);
                                }
                            }
                            if (event.getClickType() == ClickType.RIGHT_CLICK) {
                                    user.getPickaxe().removeEnchantment(enchantment);
                                    event.getPlayer().sendMessage("reset " + enchantment.getName());
                                    MinesServer.getInstance().getPickaxeFactory().givePickaxe(user, player);
                                    INVENTORY.open(player);
                            }

                            if (event.getClickType() == ClickType.START_SHIFT_CLICK) {
                                // valor total / vezes que pode comprar
                                while(canBuy(price, user.getTokens())) {
                                     if (user.getPickaxe().getEnchantLevel(value) >= enchantment.getMaxLevel()) {
                                         break;
                                     }
                                    user.setTokens(user.getTokens() - price);
                                    user.getPickaxe().addEnchantmentLevel(enchantment, 1);
                                    price = enchantment.getPricePerLevel() * user.getPickaxe().getEnchantLevel(value);
                                }
                                MinesServer.getInstance().getPickaxeFactory().givePickaxe(user, player);
                                INVENTORY.open(player);
                            }
                        }));
                i++;
            }
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
