package com.mart.ffmagic.item.scrolls;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;
import java.util.TreeMap;

public class ItemScrollMiner extends ItemScroll {

    final TreeMap<Integer, Block> blockChances = new TreeMap<>();

    public ItemScrollMiner(String name) {
        super(name);
        blockChances.put(99, Blocks.DIAMOND_ORE);
        blockChances.put(95, Blocks.EMERALD_ORE);
        blockChances.put(90, Blocks.LAPIS_ORE);
        blockChances.put(80, Blocks.GOLD_ORE);
        blockChances.put(70, Blocks.REDSTONE_ORE);
        blockChances.put(50, Blocks.IRON_ORE);
        blockChances.put(20, Blocks.COAL_ORE);
        blockChances.put(0, Blocks.STONE);
    }

    @Override
    protected void action(World world, EntityPlayer player, EnumHand hand) {
        if(world.isRemote){
           return;
        }
        int radius = getScrollLevel(player.getHeldItem(hand)) * 2;
        Random rand = new Random();

        for(int x = -radius; x <= radius; x++){
            for(int z = -radius; z <= radius; z++){
                for(int y = -radius; y <= radius; y++){
                    BlockPos pos = new BlockPos(player.getPosition().getX() + x, player.getPosition().getY() + y, player.getPosition().getZ() + z);
                    if(rand.nextInt(2) == 0){
                        continue;
                    }
                    if(world.getBlockState(pos).getBlock() == Blocks.STONE){
                        world.setBlockState(pos, blockChances.floorEntry(rand.nextInt(100) + 1).getValue().getDefaultState());
                    }
                }

            }
        }

        super.action(world, player, hand);
    }
}
