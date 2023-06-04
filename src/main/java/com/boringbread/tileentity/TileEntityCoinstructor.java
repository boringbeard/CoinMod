package com.boringbread.tileentity;

import com.boringbread.init.CoinItems;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;

public class TileEntityCoinstructor extends TileEntity implements ITickable, ICapabilityProvider
{
    public static final int MIDDLE_SLOT = 0;
    public static final int LEFT_SLOT = 1;
    public static final int RIGHT_SLOT = 2;
    private final ItemStackHandler itemStackHandler = new ItemStackHandler(3)
    {
        @Override
        protected void onContentsChanged(int slot)
        {
            super.onContentsChanged(slot);
            markDirty();
        }

        @Override
        public int getSlotLimit(int slot)
        {
            return 1;
        }
    };

    @Override
    public void update()
    {

    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        compound.setTag("inventory", itemStackHandler.serializeNBT());
        return super.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        itemStackHandler.deserializeNBT(compound.getCompoundTag("inventory"));
    }

    @Override
    public NBTTagCompound getUpdateTag()
    {
        NBTTagCompound tag = super.getUpdateTag();
        tag.setTag("inventory", itemStackHandler.serializeNBT());
        return tag;
    }

    @Override
    public void handleUpdateTag(NBTTagCompound tag)
    {
        super.handleUpdateTag(tag);
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing)
    {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return true;
        }
        return super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing)
    {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return (T) itemStackHandler;
        }
        return super.getCapability(capability, facing);
    }

    public ItemStackHandler getItemStackHandler()
    {
        return itemStackHandler;
    }

    public void stamp(){
        System.out.println("testamp");
        if (!itemStackHandler.getStackInSlot(MIDDLE_SLOT).isEmpty()){
            itemStackHandler.setStackInSlot(MIDDLE_SLOT, recipeOutput(itemStackHandler));
        }
    }

    public static ItemStack recipeOutput(ItemStackHandler handler) {
        if (handler.getStackInSlot(MIDDLE_SLOT).getItem() == Items.GOLD_INGOT)
        {
            return new ItemStack(CoinItems.COIN_GOLD, 3);
        }
        if (handler.getStackInSlot(MIDDLE_SLOT).getItem() == Items.IRON_INGOT)
        {
            return new ItemStack(CoinItems.COIN_IRON, 3);
        }
        return handler.getStackInSlot(MIDDLE_SLOT);
    }
}
