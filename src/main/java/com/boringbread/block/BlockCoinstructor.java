package com.boringbread.block;

import com.boringbread.init.CoinMod;
import com.boringbread.network.CoinPacketHandler;
import com.boringbread.network.MessageTileEntitySync;
import com.boringbread.tileentity.TileEntityCoinstructor;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class BlockCoinstructor extends BlockHorizontal
{
    public final String name = "coinstructor";

    public BlockCoinstructor()
    {
        super(Material.ROCK);
        this.setRegistryName(new ResourceLocation(CoinMod.MOD_ID, name));
        this.setUnlocalizedName(CoinMod.MOD_ID + "_" + name);
        this.setCreativeTab(CoinMod.creativeTabCoinMod);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
        this.setLightOpacity(0);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if (worldIn.isRemote) return true;

        TileEntity te = worldIn.getTileEntity(pos);

        if(te instanceof TileEntityCoinstructor)
        {
            TileEntityCoinstructor tec = (TileEntityCoinstructor) te;
            IItemHandler itemHandler = tec.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing);
            EnumFacing blockDir = state.getValue(BlockHorizontal.FACING);

            Vec3d hitPos = new Vec3d(hitX, 0, hitZ);
            hitPos.rotateYaw(-blockDir.getHorizontalAngle());

            int i = hitPos.x > 0.5 ? TileEntityCoinstructor.LEFT_SLOT : TileEntityCoinstructor.RIGHT_SLOT;

            if(itemHandler.getStackInSlot(TileEntityCoinstructor.MIDDLE_SLOT).isEmpty() != playerIn.getHeldItem(hand).isEmpty())
            {
                i = TileEntityCoinstructor.MIDDLE_SLOT;
            }

            playerIn.setHeldItem(hand, playerIn.getHeldItem(hand).isEmpty() ? itemHandler.extractItem(i, itemHandler.getStackInSlot(i).getCount(), false) : itemHandler.insertItem(i, playerIn.getHeldItem(hand), false)); // fix logic
            CoinPacketHandler.NETWORK_WRAPPER.sendToAllTracking(new MessageTileEntitySync(tec.getUpdateTag()), new NetworkRegistry.TargetPoint(worldIn.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 0));
            return true;
        }

        return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
    }

    @Override
    public void onNeighborChange(IBlockAccess world, BlockPos pos, BlockPos neighbor)
    {
        if(neighbor.down().equals(pos)
        && !world.getBlockState(neighbor).getBlock().isAir(world.getBlockState(neighbor), world, neighbor)) {
            System.out.println("stamp");
            TileEntity te = world.getTileEntity(pos);
            if(te instanceof TileEntityCoinstructor){
                ((TileEntityCoinstructor) te).stamp();
            }
        }
        super.onNeighborChange(world, pos, neighbor);
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, FACING);
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand)
    {
        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }

    @Override
    public boolean hasTileEntity(IBlockState state)
    {
        return true;
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state)
    {
        return new TileEntityCoinstructor();
    }

    public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
    {
        TileEntity te = worldIn.getTileEntity(pos);

        if (te instanceof TileEntityCoinstructor)
        {
            TileEntityCoinstructor tec = (TileEntityCoinstructor) te;
            for (int i = 0; i < tec.getItemStackHandler().getSlots(); i++)
            {
                InventoryHelper.spawnItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), tec.getItemStackHandler().getStackInSlot(i));
            }
            worldIn.updateComparatorOutputLevel(pos, this);
        }

        super.breakBlock(worldIn, pos, state);
    }

    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(FACING).getIndex();
    }
}
