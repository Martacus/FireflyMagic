package com.mart.ffmagic.item.scrolls;

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

public class ItemScrollDruid extends ItemScroll {

    public ItemScrollDruid(String name) {
        super(name);
    }

    @Override
    protected void action(World world, EntityPlayer player, EnumHand hand) {
        int radius = getScrollLevel(player.getHeldItem(hand)) * 2;

        for(int x = -radius; x <= radius; x++){
            for(int z = -radius; z <= radius; z++){
                BlockPos pos = world.getHeight(Heightmap.Type.WORLD_SURFACE, new BlockPos(player.getPosition().getX() + x, 0, player.getPosition().getZ() + z));
                if(world.getBlockState(pos.down()).canSustainPlant(world, pos.down(), EnumFacing.UP, (IPlantable) Blocks.ACACIA_SAPLING)){
                    Random rand = new Random();
                    int chance = rand.nextInt(100) + 1;
                    if(chance > 90){
                        world.setBlockState(pos, Blocks.OAK_SAPLING.getDefaultState());
                        BlockSapling sapling = (BlockSapling) Blocks.OAK_SAPLING;
                        sapling.grow(world, pos, world.getBlockState(pos), rand);
                        sapling.grow(world, pos, world.getBlockState(pos), rand);
                    }
                    else if(chance > 40){
                        if(chance > 70){
                            world.setBlockState(pos, Blocks.POPPY.getDefaultState());
                        }
                        else if(chance > 60){
                            world.setBlockState(pos, Blocks.DANDELION.getDefaultState());
                        }
                        else if(chance > 50){
                            world.setBlockState(pos, Blocks.BLUE_ORCHID.getDefaultState());
                        }
                        else{
                            world.setBlockState(pos, Blocks.OXEYE_DAISY.getDefaultState());
                        }
                    }
                    else{
                        world.setBlockState(pos, Blocks.TALL_GRASS.getDefaultState());
                        //todo: place grass
                    }
                }
            }
        }
        super.action(world, player, hand);
    }
}
