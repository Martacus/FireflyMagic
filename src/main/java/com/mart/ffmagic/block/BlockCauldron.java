package com.mart.ffmagic.block;

import com.hrznstudio.titanium.api.IFactory;
import com.hrznstudio.titanium.block.BlockTileBase;
import com.hrznstudio.titanium.block.tile.TileActive;
import com.mart.ffmagic.FireflyMagic;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import java.util.List;
import java.util.Objects;

public class BlockCauldron  extends BlockTileBase<BlockCauldron.TileEntityCauldron> {

    public static final BooleanProperty ON = BooleanProperty.create("on");
    public static final BooleanProperty WATER = BooleanProperty.create("water");

    protected static final VoxelShape INSIDE = Block.makeCuboidShape(3.0D, 4.0D, 3.0D, 13.0D, 16.0D, 13.0D);
    protected static final VoxelShape WALLS = VoxelShapes.combineAndSimplify(VoxelShapes.fullCube(), INSIDE, IBooleanFunction.ONLY_FIRST);

    public BlockCauldron() {
        super("cauldron", Properties.create(Material.IRON), TileEntityCauldron.class);
        setDefaultState(this.getStateContainer().getBaseState().with(ON, Boolean.FALSE).with(WATER, Boolean.FALSE));
    }

    @Override
    public boolean onBlockActivated(IBlockState state, World worldIn, BlockPos pos, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        return super.onBlockActivated(state, worldIn, pos, player, hand, side, hitX, hitY, hitZ);
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

    @Override
    public VoxelShape getShape(IBlockState state, IBlockReader worldIn, BlockPos pos) {
        return WALLS;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, IBlockState> builder) {
        builder.add(ON).add(WATER);
    }

    //TileEntity
    public static class TileEntityCauldron extends TileActive {

        public TileEntityCauldron() {
            super((BlockTileBase) ModBlocks.CAULDRON);
        }

        @Override
        public boolean onActivated(EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
            if(playerIn.getHeldItem(hand).getItem() == Items.FLINT_AND_STEEL){
                world.setBlockState(getPos(), getBlockState().with(ON, true));
                return true;
            } else if(playerIn.getHeldItem(hand).getItem() == Items.WATER_BUCKET){
                world.setBlockState(getPos(), getBlockState().with(WATER, true));
                return true;
            }
            return super.onActivated(playerIn, hand, facing, hitX, hitY, hitZ);
        }

        @Override
        public void tick() {
            if(getBlockState().get(WATER)){
                List<EntityItem> items = world.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(
                        getPos().getX(),getPos().getY(),getPos().getZ(),
                        getPos().getX() + 1,getPos().getY() + 1,getPos().getZ() + 1)
                );
                System.out.println(items.size());
            }

            super.tick();
        }
    }

}
