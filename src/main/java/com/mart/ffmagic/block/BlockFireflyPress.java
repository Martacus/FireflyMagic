package com.mart.ffmagic.block;

import com.hrznstudio.titanium.annotation.Save;
import com.hrznstudio.titanium.api.IFactory;
import com.hrznstudio.titanium.api.IItemStackQuery;
import com.hrznstudio.titanium.block.BlockTileBase;
import com.hrznstudio.titanium.block.tile.TileActive;
import com.hrznstudio.titanium.block.tile.inventory.PosInvHandler;
import com.mart.ffmagic.FireflyMagic;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;

import javax.annotation.Nullable;
import java.util.Objects;

public class BlockFireflyPress extends BlockTileBase<BlockFireflyPress.TileFireflyPress> {

    public static final BlockFireflyPress BLOCK = new BlockFireflyPress();

    public BlockFireflyPress() {
        super("firefly_press", Properties.create(Material.IRON), TileFireflyPress.class);
    }

    @Override
    public IFactory<ItemBlock> getItemBlockFactory() {
        return () -> (ItemBlock) new ItemBlock(this, new Item.Properties().group(FireflyMagic.GROUP)).setRegistryName(Objects.requireNonNull(getRegistryName()));
    }

    @Override
    public boolean canConnectRedstone(IBlockState state, IBlockReader world, BlockPos pos, @Nullable EnumFacing side) {
        return true;
    }

    @Override
    public boolean shouldCheckWeakPower(IBlockState state, IWorldReader world, BlockPos pos, EnumFacing side) {
        return false;
    }

    @Override
    public IFactory<TileFireflyPress> getTileEntityFactory() {
        return TileFireflyPress::new;
    }

    public static class TileFireflyPress extends TileActive {

        @Save
        private PosInvHandler first;

        public TileFireflyPress() {
            super(BLOCK);
            this.addInventory(first = new PosInvHandler("press_slot", 80, 40, 1).setTile(this).setInputFilter((stack, integer) -> IItemStackQuery.ANYTHING.test(stack)));
        }

        @Override
        public boolean onActivated(EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
            if (!super.onActivated(playerIn, hand, facing, hitX, hitY, hitZ)){
                openGui(playerIn);
                return true;
            }
            return false;
        }
    }

}
