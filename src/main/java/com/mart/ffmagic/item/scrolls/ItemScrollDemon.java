package com.mart.ffmagic.item.scrolls;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ItemScrollDemon extends ItemScroll {

    public ItemScrollDemon(String name) {
        super(name);
    }

    @Override
    protected void action(World world, EntityPlayer player, EnumHand hand) {
        if(world.isRemote){
            return;
        }
        BlockPos pos = player.getPosition();
        Vec3d look = player.getLookVec();
        EntityLargeFireball fireball2 = new EntityLargeFireball(world);
        fireball2.explosionPower = 2;
        fireball2.setPosition(pos.getX() + look.x * 5, pos.getY() + look.y * 5, pos.getZ() + look.z * 5);
        fireball2.accelerationX = look.x * 0.4;
        fireball2.accelerationY = look.y * 0.4;
        fireball2.accelerationZ = look.z * 0.4;
        world.spawnEntity(fireball2);

        setScrollLevel(player.getHeldItem(hand), getScrollLevel(player.getHeldItem(hand)) - 1);
        if(getScrollLevel(player.getHeldItem(hand)) <= 0){
            removeScrol(player, hand);
        }
    }
}
