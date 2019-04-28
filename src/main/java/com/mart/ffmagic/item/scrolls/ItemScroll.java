package com.mart.ffmagic.item.scrolls;

import com.hrznstudio.titanium.item.ItemBase;
import com.mart.ffmagic.FireflyMagic;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class ItemScroll extends ItemBase {

    public ItemScroll(String name) {
        super(name, new Item.Properties().group(FireflyMagic.GROUP));
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        if(player.isSneaking() && player.abilities.isCreativeMode){
            setScrollLevel(player.getHeldItem(hand), getScrollLevel(player.getHeldItem(hand)) + 1);
            return super.onItemRightClick(world, player, hand);
        }

        action(world, player, hand);

        return super.onItemRightClick(world, player, hand);
    }

    protected void removeScrol(EntityPlayer player, EnumHand hand){
        if(!player.abilities.isCreativeMode){
            player.getHeldItem(hand).shrink(1);
        }
    }

    protected void action(World world, EntityPlayer player, EnumHand hand){
        removeScrol(player, hand);
    }

    public static void setScrollLevel(ItemStack stack, int level){
        NBTTagCompound compound = stack.getOrCreateChildTag("levelTag");
        compound.putInt("level", level);
    }

    public static int getScrollLevel(ItemStack stack){
        NBTTagCompound compound = stack.getOrCreateChildTag("levelTag");
        return compound.getInt("level");
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add(new TextComponentString("Level: " + getScrollLevel(stack)));
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }
}
