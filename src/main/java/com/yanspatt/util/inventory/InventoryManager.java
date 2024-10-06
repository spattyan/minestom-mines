package com.yanspatt.util.inventory;

import com.yanspatt.MinesServer;
import com.yanspatt.util.inventory.contents.InventoryContents;
import com.yanspatt.util.inventory.opener.ChestInventoryOpener;
import com.yanspatt.util.inventory.opener.InventoryOpener;
import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Player;
import net.minestom.server.event.EventFilter;
import net.minestom.server.event.EventNode;
import net.minestom.server.event.inventory.*;
import net.minestom.server.event.player.PlayerDisconnectEvent;
import net.minestom.server.event.trait.EntityEvent;
import net.minestom.server.event.trait.InventoryEvent;
import net.minestom.server.event.trait.PlayerEvent;
import net.minestom.server.inventory.InventoryType;

import java.util.*;

public class InventoryManager {

    private MinesServer plugin;

    private Map<String, CustomInventory> inventories;
    private Map<String, InventoryContents> contents;

    private List<InventoryOpener> defaultOpeners;
    private List<InventoryOpener> openers;

    public InventoryManager(MinesServer plugin) {
        this.plugin = plugin;

        this.inventories = new HashMap<>();
        this.contents = new HashMap<>();

        this.defaultOpeners = Arrays.asList(
                new ChestInventoryOpener()
        );

        this.openers = new ArrayList<>();
    }

    public void init() {
        events();
    }

    public Optional<InventoryOpener> findOpener(InventoryType type) {
        Optional<InventoryOpener> opInv = this.openers.stream()
                .filter(opener -> opener.supports(type))
                .findAny();

        if (!opInv.isPresent()) {
            opInv = this.defaultOpeners.stream()
                    .filter(opener -> opener.supports(type))
                    .findAny();
        }

        return opInv;
    }

    public void registerOpeners(InventoryOpener... openers) {
        this.openers.addAll(Arrays.asList(openers));
    }

    public List<Player> getOpenedPlayers(CustomInventory inv) {
        List<Player> list = new ArrayList<>();

        this.inventories.forEach((player, playerInv) -> {
            if (inv.equals(playerInv))
                list.add(MinecraftServer.getConnectionManager().getOnlinePlayerByUsername(player));
        });

        return list;
    }

    public Optional<CustomInventory> getInventory(Player player) {
        return Optional.ofNullable(this.inventories.get(player.getUsername().toLowerCase()));
    }

    protected void setInventory(Player player, CustomInventory inv) {
        if (inv == null)
            this.inventories.remove(player.getUsername().toLowerCase());
        else
            this.inventories.put(player.getUsername().toLowerCase(), inv);
    }

    public Optional<InventoryContents> getContents(Player player) {
        return Optional.ofNullable(this.contents.get(player.getUsername().toLowerCase()));
    }

    protected void setContents(Player player, InventoryContents contents) {
        if (contents == null)
            this.contents.remove(player.getUsername().toLowerCase());
        else
            this.contents.put(player.getUsername().toLowerCase(), contents);
    }

    public void handleInventoryOpenError(CustomInventory inventory, Player player, Exception exception) {
        inventory.close(player);

        System.out.println(("Error while opening SmartInventory:" + exception));
        exception.printStackTrace();
    }

    public void handleInventoryUpdateError(CustomInventory inventory, Player player, Exception exception) {
        inventory.close(player);

        System.out.println(("Error while updating SmartInventory:" + exception));
    }

    public void events() {

        var handler = MinecraftServer.getGlobalEventHandler();
        EventNode<PlayerEvent> inventoryPlayerEvents = EventNode.type("player-events-inventory-custom", EventFilter.PLAYER);
        EventNode<InventoryEvent> inventoryEvents = EventNode.type("inventory-custom", EventFilter.INVENTORY);
        inventoryEvents.addListener(InventoryPreClickEvent.class, event -> {
            event.setCancelled(true);
           Player player = (Player) event.getPlayer();

           if (!inventories.containsKey(player.getUsername().toLowerCase())) {
               return;
           }

           if (event.getInventory() == null) {
               event.setCancelled(true);
               return;
           }

                int row = event.getSlot() / 9;
                int column = event.getSlot() % 9;

                if (row < 0 || column < 0)
                    return;

                CustomInventory inv = inventories.get(player.getUsername().toLowerCase());

                if (row >= inv.getRows() || column >= inv.getColumns())
                    return;

                inv.getListeners().stream()
                        .filter(listener -> listener.getType() == InventoryPreClickEvent.class)
                        .forEach(listener -> ((InventoryListener<InventoryPreClickEvent>) listener).accept(event));

                contents.get(player.getUsername().toLowerCase()).get(row, column).ifPresent(item -> item.run(event));

                if (player.getOpenInventory() != null)  player.getOpenInventory().update();

        });

        inventoryEvents.addListener(InventoryOpenEvent.class, event -> {
            Player player = (Player) event.getPlayer();

            if (!inventories.containsKey(player.getUsername().toLowerCase())) {
                return;
            }

            CustomInventory inv = inventories.get(player.getUsername().toLowerCase());

            inv.getListeners().stream()
                    .filter(listener -> listener.getType() == InventoryOpenEvent.class)
                    .forEach(listener -> ((InventoryListener<InventoryOpenEvent>) listener).accept(event));

        });

        inventoryEvents.addListener(InventoryCloseEvent.class, event -> {
            Player player = (Player) event.getPlayer();

            if (!inventories.containsKey(player.getUsername().toLowerCase()))
                return;

            CustomInventory inv = inventories.get(player.getUsername().toLowerCase());

            inv.getListeners().stream()
                    .filter(listener -> listener.getType() == InventoryCloseEvent.class)
                    .forEach(listener -> ((InventoryListener<InventoryCloseEvent>) listener).accept(event));

            if (inv.isCloseable()) {
                event.getInventory().clear();

                inventories.remove(player.getUsername().toLowerCase());
                contents.remove(player.getUsername().toLowerCase());
            } else
                player.openInventory(event.getInventory());
        });

        inventoryPlayerEvents.addListener(PlayerDisconnectEvent.class, event -> {
            Player player = (Player) event.getPlayer();

            if (!inventories.containsKey(player.getUsername().toLowerCase()))
                return;

            CustomInventory inv = inventories.get(player.getUsername().toLowerCase());

            inv.getListeners().stream()
                    .filter(listener -> listener.getType() == PlayerDisconnectEvent.class)
                    .forEach(listener -> ((InventoryListener<PlayerDisconnectEvent>) listener).accept(event));

            inventories.remove(player.getUsername().toLowerCase());
            contents.remove(player.getUsername().toLowerCase());

        });

        handler.addChild(inventoryEvents);
        handler.addChild(inventoryPlayerEvents);


    }
}
