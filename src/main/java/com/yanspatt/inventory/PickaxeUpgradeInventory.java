package com.yanspatt.inventory;

import com.yanspatt.MinesServer;
import com.yanspatt.model.pickaxe.EnchantmentType;
import com.yanspatt.model.pickaxe.PickaxeEnchantment;
import com.yanspatt.util.BigNumbers;
import com.yanspatt.util.ItemBuilder;
import com.yanspatt.util.inventory.ClickableItem;
import com.yanspatt.util.inventory.CustomInventory;
import com.yanspatt.util.inventory.contents.InventoryContents;
import com.yanspatt.util.inventory.contents.InventoryProvider;
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
            // Retornar a picareta
            //contents.set(2,6,ClickableItem.empty(user.getPickaxe().getItem()));

            int i = 0;
            for (EnchantmentType value : EnchantmentType.values()) {
                int slot = upgradeSlots[i];
                int row = slot / 9;
                int column = slot % 9;
                PickaxeEnchantment enchantment = MinesServer.getInstance().getEnchantmentService().getEnchantment(value);
                int enchantLevel = user.getEnchantmentLevel(enchantment.getType());

                long currentPrice = enchantLevel == 0 ? enchantment.getDefaultPrice() : enchantment.getPerLevelPrice() * enchantLevel;
                String next = (enchantLevel +1) > enchantment.getMaxLevel() ? "MAX." : ""+(enchantLevel +1);
                contents.set(row,column,ClickableItem.of(
                        new ItemBuilder(enchantment.getIcon()).name("&a" + enchantment.getName() + " &8[&f" + enchantLevel + " &7➟ &f" + (next) + "&8]")
                                .lore("&7" + enchantment.getDescription(),
                                        "",
                                        "&f Custo: &c" + BigNumbers.format(currentPrice) + " Tokens",
                                        "&f Nível: &7" + enchantLevel + "&8/&7" + enchantment.getMaxLevel() + " &8[0%]",
                                        "&f Chance Atual: &7" + (enchantment.getPercentByLevel() * enchantLevel) + "%",
                                        "",
                                        "&f Botão Esquerdo: &aUpar 1 nível",
                                        "&f Botão Direito: &cZERAR ENCANTAMENTO",
                                        "&f Shift + Clique: &aUpar o possível"
                                        )
                                .build(),
                        event -> {

                            if (enchantment.getMaxLevel() > 0 && enchantLevel >= enchantment.getMaxLevel()) {
                                return;
                            }
                            int currentLevel = enchantLevel == 0 ? 1 : enchantLevel;
                            long price = currentLevel == 0 ? enchantment.getDefaultPrice() : enchantment.getPerLevelPrice() * currentLevel;
                            // TODO Implement economy

                            //if (!canBuy(price, user.getTokens())) return;

                            if (event.getClickType() == ClickType.LEFT_CLICK) {
                                    user.addEnchantmentLevel(value, 1);
                                    event.getPlayer().sendMessage("Applied enchantment " + enchantment.getName());
                                    MinesServer.getInstance().getPickaxeFactory().givePickaxe(user, player);
                                    INVENTORY.open(player);
                            }
                            if (event.getClickType() == ClickType.RIGHT_CLICK) {
                                    user.removeEnchantment(value);
                                    event.getPlayer().sendMessage("reset " + enchantment.getName());
                                    MinesServer.getInstance().getPickaxeFactory().givePickaxe(user, player);
                                    INVENTORY.open(player);
                            }

                            if (event.getClickType() == ClickType.START_SHIFT_CLICK) {
                                // valor total / vezes que pode comprar
                                /*
                                while(canBuy(price, user.getTokens())) {
                                     if (user.getPickaxe().getEnchantLevel(value) >= enchantment.getMaxLevel()) {
                                         break;
                                     }
                                    user.setTokens(user.getTokens() - price);
                                    user.getPickaxe().addEnchantmentLevel(value, 1);
                                    price = enchantment.getPerLevelPrice() * user.getPickaxe().getEnchantLevel(value);
                                }*/
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
