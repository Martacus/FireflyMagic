package com.mart.ffmagic.item.scrolls;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.common.IPlantable;

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
                pos = pos.down();
                if(world.getBlockState(pos).canSustainPlant(world, pos, EnumFacing.UP, (IPlantable) Blocks.ACACIA_SAPLING)){
                    System.out.println("true");
                }
            }
        }
        super.action(world, player, hand);
    }
}
