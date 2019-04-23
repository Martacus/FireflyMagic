package com.mart.ffmagic.item;

import com.hrznstudio.titanium.item.ItemBase;
import com.mart.ffmagic.FireflyMagic;
import com.mart.ffmagic.entity.EntityFirefly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemFireflyJar extends ItemBase {

    private final EntityFirefly.FireflyType type;

    public ItemFireflyJar(String name, EntityFirefly.FireflyType type) {
        super(name, new Item.Properties().maxStackSize(1).group(FireflyMagic.GROUP));
        this.type = type;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand hand) {
        if(!worldIn.isRemote && player.isSneaking()){
            Entity firefly = FireflyMagic.FIREFLY.spawn(worldIn, ItemStack.EMPTY, player, player.getPosition(), false, false);
            firefly.getDataManager().set(EntityFirefly.TYPE, type.name());
            if(!player.abilities.isCreativeMode){
                player.getHeldItem(hand).shrink(1);
            }
        }

        return super.onItemRightClick(worldIn, player, hand);
    }
}
