package com.mart.ffmagic.item.scrolls;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class ItemScrollThunder extends ItemScroll {

    public ItemScrollThunder(String name) {
        super(name);
    }

    @Override
    protected void action(World world, EntityPlayer player, EnumHand hand) {
        if(world.isRemote){
            return;
        }
        int maxEntities = getScrollLevel(player.getHeldItem(hand)) * 2;
        BlockPos pos = player.getPosition();
        List<EntityLiving> entityLivingList = world.getEntitiesWithinAABB(EntityLiving.class, new AxisAlignedBB(pos.getX() + -10, pos.getY() + -5, pos.getZ() + -10, pos.getX() + 10, pos.getY() + 10, pos.getZ() + 10));
        int entitiesHit = 0;
        for (EntityLiving living : entityLivingList){
            if(entitiesHit >= maxEntities){
                break;
            }
            world.addWeatherEffect(new EntityLightningBolt(world, living.getPosition().getX(), living.getPosition().getY(), living.getPosition().getZ(), false));
            //EntityType.LIGHTNING_BOLT.spawn(world, player.getHeldItem(hand), player, living.getPosition(), false, false);
            entitiesHit++;
        }
        super.action(world, player, hand);
    }
}
