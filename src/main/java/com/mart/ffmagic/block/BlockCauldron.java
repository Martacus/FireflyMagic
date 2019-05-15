package com.mart.ffmagic.block;

import com.hrznstudio.titanium.annotation.Save;
import com.hrznstudio.titanium.api.IFactory;
import com.hrznstudio.titanium.block.BlockTileBase;
import com.hrznstudio.titanium.block.tile.TileActive;
import com.hrznstudio.titanium.block.tile.inventory.PosInvHandler;
import com.mart.ffmagic.FireflyMagic;
import com.mart.ffmagic.item.ItemFireflyJar;
import com.mart.ffmagic.recipe.ModRecipes;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;

import java.util.Objects;

public class BlockCauldron  extends BlockTileBase<BlockCauldron.TileEntityCauldron> {

    public static final BlockCauldron BLOCK = new BlockCauldron();

    public BlockCauldron() {
        super("cauldron", Properties.create(Material.IRON), TileEntityCauldron.class);
    }

    @Override
    public IFactory<ItemBlock> getItemBlockFactory() {
        return () -> (ItemBlock) new ItemBlock(this, new Item.Properties().group(FireflyMagic.GROUP)).setRegistryName(Objects.requireNonNull(getRegistryName()));
    }

    @Override
    public IFactory<TileEntityCauldron> getTileEntityFactory() {
        return TileEntityCauldron::new;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    //TileEntity
    public static class TileEntityCauldron extends TileActive {

        public TileEntityCauldron() {
            super(BLOCK);

        }

    }

}
