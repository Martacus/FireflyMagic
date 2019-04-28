package com.mart.ffmagic.item.scrolls;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSapling;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.common.IPlantable;

import java.util.Random;
import java.util.TreeMap;

public class ItemScrollDruid extends ItemScroll {

    final TreeMap<Integer, Block> blockChances = new TreeMap<>();

    public ItemScrollDruid(String name) {
        super(name);
        blockChances.put(90, Blocks.OAK_SAPLING);
        blockChances.put(70, Blocks.POPPY);
        blockChances.put(60, Blocks.DANDELION);
        blockChances.put(50, Blocks.BLUE_ORCHID);
        blockChances.put(40, Blocks.OXEYE_DAISY);
        blockChances.put(0, Blocks.TALL_GRASS);
    }

    @Override
    protected void action(World world, EntityPlayer player, EnumHand hand) {
        int radius = getScrollLevel(player.getHeldItem(hand)) * 2;
        Random rand = new Random();

        for(int x = -radius; x <= radius; x++){
            for(int z = -radius; z <= radius; z++){
                BlockPos pos = world.getHeight(Heightmap.Type.WORLD_SURFACE, new BlockPos(player.getPosition().getX() + x, 0, player.getPosition().getZ() + z));
                if(world.getBlockState(pos.down()).canSustainPlant(world, pos.down(), EnumFacing.UP, (IPlantable) Blocks.ACACIA_SAPLING)){
                    world.setBlockState(pos, blockChances.floorEntry(rand.nextInt(100) + 1).getValue().getDefaultState());
                    if(world.getBlockState(pos) == Blocks.OAK_SAPLING){
                        BlockSapling sapling = (BlockSapling) Blocks.OAK_SAPLING;
                        sapling.grow(world, pos, world.getBlockState(pos), rand);
                        sapling.grow(world, pos, world.getBlockState(pos), rand);
                    }
                }
            }
        }
        super.action(world, player, hand);
    }
}
