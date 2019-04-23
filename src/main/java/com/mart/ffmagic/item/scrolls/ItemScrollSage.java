package com.mart.ffmagic.item.scrolls;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemScrollSage extends ItemScroll {

    public ItemScrollSage(String name) {
        super(name);
    }

    @Override
    protected void action(World world, EntityPlayer player, EnumHand hand) {
        player.giveExperiencePoints(getScrollLevel(player.getHeldItem(hand)) * 30);

    }
}
