package com.yanspatt.enchantments.impl;

import com.yanspatt.MinesServer;
import com.yanspatt.enchantments.BlockHandler;
import com.yanspatt.enchantments.CustomEnchantment;
import com.yanspatt.model.mine.Mine;
import com.yanspatt.model.mine.packetMine.MiningChunkSection;
import com.yanspatt.model.pickaxe.PickaxeEnchantment;
import com.yanspatt.model.user.User;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.instance.Chunk;
import net.minestom.server.instance.block.Block;
import net.minestom.server.network.packet.server.play.BlockChangePacket;
import net.minestom.server.network.packet.server.play.ChunkDataPacket;
import net.minestom.server.network.packet.server.play.MultiBlockChangePacket;
import net.minestom.server.utils.chunk.ChunkUtils;

import java.util.Random;
import java.util.function.IntUnaryOperator;

public class DestructorEnchantmentImpl extends CustomEnchantment {

    @Override
    public PickaxeEnchantment type() {
        return PickaxeEnchantment.DESTRUCTOR;
    }

    @Override
    public void blockBreak(User user, BlockHandler handler) {
        final Random random = new Random();
        int randomNumber = random.nextInt(10000) + 1;

        if (randomNumber <= ((type().getChancePerLevel() * user.getPickaxe().getEnchantLevel(type())) * 100)) {
            Mine mine = user.getMine();

            int y = handler.getPosition().blockY();
            for (int x = mine.getPosition1().blockX()+1; x <= mine.getPosition2().blockX()-1; ++x) {
                for (int z = mine.getPosition1().blockZ()+1; z <= mine.getPosition2().blockZ()-1; ++z) {

                    BlockChangePacket blockChangePacket = new BlockChangePacket(new Pos(x,y,z),Block.AIR);
                    handler.getPlayer().sendPacket(blockChangePacket);
                    user.setBlocksMined(user.getBlocksMined() + 1);
                }
            }

        }
    }
}
