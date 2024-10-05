package com.yanspatt.listener.impl.player;

import com.google.common.base.Stopwatch;
import com.yanspatt.MinesServer;
import com.yanspatt.controller.UserController;
import com.yanspatt.enchantments.BlockHandler;
import com.yanspatt.enchantments.CustomEnchantment;
import com.yanspatt.listener.GenericEventListener;
import com.yanspatt.model.mine.packetMine.MinedBlock;
import com.yanspatt.model.mine.packetMine.MinedType;
import com.yanspatt.model.user.User;
import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.event.EventListener;
import net.minestom.server.event.player.PlayerBlockBreakEvent;
import net.minestom.server.event.player.PlayerFinishDiggingEvent;
import net.minestom.server.instance.block.Block;
import net.minestom.server.network.packet.server.play.BlockBreakAnimationPacket;
import net.minestom.server.network.packet.server.play.BlockChangePacket;
import net.minestom.server.utils.chunk.ChunkUtils;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class PlayerBlockBreakListener implements GenericEventListener<PlayerBlockBreakEvent> {

    private UserController userController;

    public PlayerBlockBreakListener(UserController userController) {
        this.userController = userController;
    }

    @Override
    public @NotNull EventListener<PlayerBlockBreakEvent> register() {
        return EventListener.builder(PlayerBlockBreakEvent.class)
                .handler(event -> {
                    event.setCancelled(true);
                    Stopwatch stopwatch = Stopwatch.createStarted();

                    MinesServer.getInstance().getUserController().getUser(event.getPlayer().getUsername()).ifPresent(user -> {
                        if (user.getMine().isInside(event.getBlockPosition())) {
                            BlockChangePacket packet = new BlockChangePacket(event.getBlockPosition(), Block.AIR);
                            event.getPlayer().sendPacket(packet);
                            user.getMine().getMinedBlocks().add(
                                    new MinedBlock(MinedType.BLOCK,
                                            event.getBlockPosition().blockX(),
                                            event.getBlockPosition().blockY(),
                                            event.getBlockPosition().blockZ()));
                            user.setBlocksMined(user.getBlocksMined() + 1);
                            user.getPickaxe().getEnchantments().forEach((key,value) -> {
                                CustomEnchantment enchant = MinesServer.getInstance().getEnchantmentController().getEnchantments().get(key);
                                if (enchant != null) {
                                    enchant.blockBreak(user, new BlockHandler(null,new Pos(event.getBlockPosition().blockX(),event.getBlockPosition().blockY(),event.getBlockPosition().blockZ()),event.getPlayer()));
                                }
                            });

                            if (user.getMine().getMinedBlocks().size() >= user.getMine().getTotalBlocks()/2) {
                                MinesServer.getInstance().getMineFactory().populateMine(user,event.getPlayer(),user.getMine().getBlock(),true);
                                MinesServer.getInstance().getMineFactory().sendMine(user,event.getPlayer());
                                event.getPlayer().teleport(new Pos(-62.0, 45, 11.0,-90,-0));
                            }
                            MinesServer.getInstance().getPickaxeFactory().givePickaxe(user, event.getPlayer());
                            event.getPlayer().getInventory().update();
                        }
                    });
                    stopwatch.stop();
                    long elapsed = stopwatch.elapsed(TimeUnit.MILLISECONDS);
                    if (elapsed > 1) {
                        System.out.println("Time elapsed (PlayerBlockBreakEvent): " + stopwatch.elapsed(TimeUnit.MILLISECONDS) + "ms");
                    }
                })
                .build();
    }
}