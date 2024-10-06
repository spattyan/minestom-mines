package com.yanspatt.listener.impl.player;

import com.yanspatt.controller.UserController;
import com.yanspatt.listener.GenericEventListener;
import com.yanspatt.repository.redis.UserRedisRepository;
import net.minestom.server.entity.Player;
import net.minestom.server.event.EventListener;
import net.minestom.server.event.player.PlayerBlockBreakEvent;
import net.minestom.server.instance.block.Block;
import net.minestom.server.network.packet.server.play.BlockChangePacket;
import org.jetbrains.annotations.NotNull;

public class PlayerBlockBreakListener implements GenericEventListener<PlayerBlockBreakEvent> {

    private UserController repository;

    public PlayerBlockBreakListener(UserController repository) {
        this.repository = repository;
    }

    @Override
    public @NotNull EventListener<PlayerBlockBreakEvent> register() {
        return EventListener.builder(PlayerBlockBreakEvent.class)
                .handler(event -> {
                    event.setCancelled(true);

                   repository.getUser(event.getPlayer().getUsername()).ifPresent(user -> {
                       if (user.getMineArea().isInside(event.getBlockPosition())) {
                           final Player player = event.getPlayer();
                           user.addBlocks(1);

                           // Send AIR block packet to player
                           BlockChangePacket packet = new BlockChangePacket(event.getBlockPosition(), Block.AIR);
                           player.sendPacket(packet);


                       }
                        /*
                        if (user.getMine().isInside(event.getBlockPosition())) {
                            BlockChangePacket packet = new BlockChangePacket(event.getBlockPosition(), Block.AIR);
                            event.getPlayer().sendPacket(packet);
                            user.getMine().getMinedBlocks().add(
                                    new MinedBlock(MinedType.BLOCK,
                                            event.getBlockPosition().blockX(),
                                            event.getBlockPosition().blockY(),
                                            event.getBlockPosition().blockZ()));
                            user.setBlocksMined(user.getBlocksMined() + 1);
                            user.getMine().setBrokenBlocks(user.getMine().getBrokenBlocks()+1);
                            user.getPickaxe().getEnchantments().forEach((key,value) -> {
                                CustomEnchantment enchant = MinesServer.getInstance().getEnchantmentController().getEnchantments().get(key);
                                if (enchant != null) {
                                    enchant.blockBreak(user, MinesServer.getInstance().getEnchantmentService().getEnchantment(enchant.type()),new BlockHandler(null,new Pos(event.getBlockPosition().blockX(),event.getBlockPosition().blockY(),event.getBlockPosition().blockZ()),event.getPlayer()));
                                }
                            });

                            if (user.getMine().getBrokenBlocks() >= user.getMine().getTotalBlocks()/2) {
                                MinesServer.getInstance().getMineFactory().populateMine(user,user.getMine().getBlock(),true);
                                MinesServer.getInstance().getMineFactory().sendMine(user,event.getPlayer());
                                event.getPlayer().teleport(new Pos(-62.0, 45, 11.0,-90,-0));
                            }
                            MinesServer.getInstance().getPickaxeFactory().givePickaxe(user, event.getPlayer());
                            event.getPlayer().getInventory().update();


                        }*/
                    });

                })
                .build();
    }
}