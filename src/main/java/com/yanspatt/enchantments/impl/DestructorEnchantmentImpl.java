package com.yanspatt.enchantments.impl;

import com.yanspatt.MinesServer;
import com.yanspatt.enchantments.BlockHandler;
import com.yanspatt.enchantments.CustomEnchantment;
import com.yanspatt.model.mine.Mine;
import com.yanspatt.model.mine.packetMine.MinedBlock;
import com.yanspatt.model.mine.packetMine.MinedType;
import com.yanspatt.model.pickaxe.EnchantmentType;
import com.yanspatt.model.pickaxe.PickaxeEnchantment;
import com.yanspatt.model.user.User;
import com.yanspatt.util.Probability;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.instance.block.Block;
import net.minestom.server.network.packet.server.play.BlockChangePacket;

import java.util.List;
import java.util.Random;

public class DestructorEnchantmentImpl extends CustomEnchantment {

    @Override
    public EnchantmentType type() {
        return EnchantmentType.DESTRUCTOR;
    }

   private List<EnchantmentType> whitelist = List.of(
            EnchantmentType.EXPERIENCE,
            EnchantmentType.KEYCHAIN,
            EnchantmentType.TOKENATOR
    );

    @Override
    public void blockBreak(User user, PickaxeEnchantment enchantment, BlockHandler handler) {
        /*
        if (Probability.probability(enchantment.getPercentByLevel() * user.getPickaxe().getEnchantLevel(type()))) {
            Mine mine = user.getMine();

            int y = handler.getPosition().blockY();

            user.getMine().getMinedBlocks().add(
                    new MinedBlock(MinedType.LAYER, y));

            for (int x = mine.getPosition1().blockX()+1; x <= mine.getPosition2().blockX()-1; ++x) {
                for (int z = mine.getPosition1().blockZ()+1; z <= mine.getPosition2().blockZ()-1; ++z) {
                    mine.setBrokenBlocks(mine.getBrokenBlocks()+1);
                    user.getPickaxe().getEnchantments().forEach((key,value) -> {
                        CustomEnchantment enchant = MinesServer.getInstance().getEnchantmentController().getEnchantments().get(key);
                        if (enchant != null && whitelist.contains(enchant.type())) {
                            enchant.blockBreak(user, enchantment, handler);
                        }

                    });
                    BlockChangePacket blockChangePacket = new BlockChangePacket(new Pos(x,y,z),Block.AIR);
                    handler.getPlayer().sendPacket(blockChangePacket);
                    user.setBlocksMined(user.getBlocksMined() + 1);
                }
            }
        }*/
    }
}
