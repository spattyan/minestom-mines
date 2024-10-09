package com.yanspatt.listener.impl.player;

import com.yanspatt.MinesServer;
import com.yanspatt.controller.UserController;
import com.yanspatt.enchantments.BlockHandler;
import com.yanspatt.enchantments.CustomEnchantment;
import com.yanspatt.listener.GenericEventListener;
import com.yanspatt.model.mine.packetMine.MiningChunkSection;
import net.minestom.server.coordinate.BlockVec;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Player;
import net.minestom.server.event.EventListener;
import net.minestom.server.event.player.PlayerBlockBreakEvent;
import net.minestom.server.instance.block.Block;
import net.minestom.server.network.packet.server.play.BlockChangePacket;
import net.minestom.server.utils.chunk.ChunkUtils;
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
                           BlockVec position = event.getBlockPosition();

                           int sectionY = ChunkUtils.blockIndexToChunkPositionY(position.blockY());
                           MiningChunkSection section = user.getMine().getSection().getChunk(position.blockX()>>4,position.blockZ()>>4,sectionY);

                           int relX = position.blockX() & 0xF;
                           int relY = position.blockY()  & 0xFF;
                           int relZ = position.blockZ()  & 0xF;

                           section.setBlock(relX, relY, relZ, Block.BARRIER);

                           user.getEnchantments().forEach((key,value) -> {
                               CustomEnchantment enchant = MinesServer.getInstance().getEnchantmentController().getEnchantments().get(key);
                               if (enchant != null) {
                                   enchant.blockBreak(user, MinesServer.getInstance().getEnchantmentService().getEnchantment(enchant.type()),new BlockHandler(null,new Pos(event.getBlockPosition().blockX(),event.getBlockPosition().blockY(),event.getBlockPosition().blockZ()),event.getPlayer()));
                               }
                           });

                       }
                    });

                })
                .build();
    }
}