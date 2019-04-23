package com.mart.ffmagic.block;

import com.hrznstudio.titanium.annotation.Save;
import com.hrznstudio.titanium.api.IFactory;
import com.hrznstudio.titanium.api.IItemStackQuery;
import com.hrznstudio.titanium.api.client.AssetTypes;
import com.hrznstudio.titanium.api.client.IGuiAddon;
import com.hrznstudio.titanium.block.BlockTileBase;
import com.hrznstudio.titanium.block.tile.TileActive;
import com.hrznstudio.titanium.block.tile.button.PosButton;
import com.hrznstudio.titanium.block.tile.inventory.PosInvHandler;
import com.hrznstudio.titanium.client.gui.addon.StateButtonAddon;
import com.hrznstudio.titanium.client.gui.addon.StateButtonInfo;
import com.mart.ffmagic.FireflyMagic;
import com.mart.ffmagic.item.ModItems;
import com.mart.ffmagic.item.scrolls.ItemFireflyJuice;
import com.mart.ffmagic.item.scrolls.ItemScroll;
import com.mart.ffmagic.recipe.ModRecipes;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class BlockScrollWriter extends BlockTileBase<BlockScrollWriter.TileScrollWriter> {

    private AxisAlignedBB aabb = new AxisAlignedBB(0, 0, 0, 1, 0.875, 1);

    public static final BlockScrollWriter BLOCK = new BlockScrollWriter();

    public BlockScrollWriter() {
        super("scroll_writer", Properties.create(Material.IRON), TileScrollWriter.class);
    }

    @Override
    public VoxelShape getShape(IBlockState state, IBlockReader worldIn, BlockPos pos) {
        return VoxelShapes.create(aabb);
    }

    @Override
    public VoxelShape getCollisionShape(IBlockState state, IBlockReader worldIn, BlockPos pos) {
        return super.getCollisionShape(state, worldIn, pos);
    }

    @Override
    public IFactory<ItemBlock> getItemBlockFactory() {
        return () -> (ItemBlock) new ItemBlock(this, new Item.Properties().group(FireflyMagic.GROUP))
                .setRegistryName(Objects.requireNonNull(getRegistryName()));
    }

    @Override
    public IFactory<TileScrollWriter> getTileEntityFactory() {
        return TileScrollWriter::new;
    }

    //TileEntity
    public static class TileScrollWriter extends TileActive {

        @Save
        private PosInvHandler juice;
        @Save
        private PosInvHandler paper;
        @Save
        private PosInvHandler output;

        private PosButton button;

        public TileScrollWriter() {
            super(BLOCK);

            this.addInventory(juice = new PosInvHandler("jar_slot", 30, 30, 1)
                    .setTile(this).setInputFilter((stack, integer) -> stack.getItem() instanceof ItemFireflyJuice));

            this.addInventory(paper = new PosInvHandler("paper_slot", 30, 50, 1).setTile(this)
                    .setInputFilter((stack, integer) -> stack.getItem() == Items.PAPER));

            this.addInventory(output = new PosInvHandler("output_slot", 130, 40, 1).setTile(this)
                    .setInputFilter((stack, integer) -> IItemStackQuery.NOTHING.test(stack)));

            this.addButton(button = new PosButton(80, 80, 14, 14) {
                @Override
                public List<IFactory<? extends IGuiAddon>> getGuiAddons() {
                    return Collections.singletonList(() -> new StateButtonAddon(button, new StateButtonInfo(0, AssetTypes.BUTTON_SIDENESS_ENABLED, "Write Scroll")) {
                        @Override
                        public int getState() {
                            return 0;
                        }
                    });
                }
            }.setId(0).setPredicate(nbtTagCompound -> {
                markForUpdate();
                activate();
            }));

        }

        public void activate(){
            if(output.getStackInSlot(0) != ItemStack.EMPTY){
                return;
            }
            ItemStack juice = this.juice.getStackInSlot(0);
            ItemStack paper = this.paper.getStackInSlot(0);
            if(juice == ItemStack.EMPTY || paper == ItemStack.EMPTY){
                return;
            }

            Item outputItem = ModRecipes.getScrollRecipeOutput(juice.getItem());
            if(outputItem == null){
                return;
            }

            int level = juice.getCount();
            if(level > 10){
                level = 10;
            }

            juice.shrink(level);
            paper.shrink(1);
            ItemStack outputItemStack = new ItemStack(outputItem);
            ItemScroll.setScrollLevel(outputItemStack, level);
            output.setStackInSlot(0, outputItemStack);
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

