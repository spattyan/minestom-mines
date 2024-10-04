package com.yanspatt.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.minestom.server.entity.Player;
import net.minestom.server.entity.PlayerSkin;
import net.minestom.server.instance.block.Block;
import net.minestom.server.instance.block.predicate.BlockTypeFilter;
import net.minestom.server.item.ItemComponent;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;
import net.minestom.server.item.component.HeadProfile;
import net.minestom.server.item.component.Tool;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class ItemBuilder {

    private ItemStack itemStack;
    private List<Component> loreList;

    public ItemBuilder(Material material) {
        this.itemStack = ItemStack.of(material);
        this.loreList = new ArrayList<>();
    }

    public ItemBuilder(ItemStack item) {
        this.itemStack = item;
        this.loreList = new ArrayList<>();
    }

    public static ItemBuilder of(ItemStack item) {
        return new ItemBuilder(item);
    }

    public ItemBuilder name(String name) {
        itemStack =  itemStack.withCustomName(LegacyComponentSerializer.legacyAmpersand().deserialize(name).decoration(TextDecoration.ITALIC, false));
        return this;
    }


    public ItemBuilder lore(String... lore) {
        loreList.clear();
        for (String s : lore) {
            loreList.add(LegacyComponentSerializer.legacyAmpersand().deserialize(s).decoration(TextDecoration.ITALIC, false));
        }
        itemStack = itemStack.withLore(loreList);
        return this;
    }

    public ItemBuilder lore(List<String> lore) {
        loreList.clear();
        for (String s : lore) {
            loreList.add(LegacyComponentSerializer.legacyAmpersand().deserialize(s).decoration(TextDecoration.ITALIC, false));
        }
        itemStack = itemStack.withLore(loreList);
        return this;
    }

    public ItemBuilder addLore(String lore) {
        loreList.add(LegacyComponentSerializer.legacyAmpersand().deserialize(lore).decoration(TextDecoration.ITALIC, false));
        itemStack = itemStack.withLore(loreList);
        return this;
    }

    public ItemBuilder amount(int amount) {
        itemStack = itemStack.withAmount(amount);
        return this;
    }

    public ItemBuilder instaBreak() {
        itemStack = itemStack.with(ItemComponent.TOOL,
                new Tool(
                        List.of(new Tool.Rule(
                                new BlockTypeFilter.Blocks(
                                        Block.DIRT
                                ), 999f, true)
                        ), 999f, 9999)
                );
        return this;
    }

    public ItemBuilder skullOwner(Player owner) {
        itemStack = itemStack.with(ItemComponent.PROFILE, new HeadProfile(new PlayerSkin(owner.getUsername(), owner.getUuid().toString())));
        return this;
    }


    public ItemBuilder skullTexture(String texture) {
        if (texture == null) throw new IllegalArgumentException("Expected a valid head texture but instead found null. Report to developer");
        HeadProfile profile = new HeadProfile(new PlayerSkin(texture, UUID.randomUUID().toString()));
        itemStack = itemStack.with(ItemComponent.PROFILE, profile);
        return this;
    }

    public ItemStack build() {
        return itemStack;
    }



}
