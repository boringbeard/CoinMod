package com.boringbread.common;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class Utils {
    public static NBTTagCompound getTagCompoundSafe(ItemStack stack)
    {
        NBTTagCompound tagCompound = stack.getTagCompound();

        if (tagCompound == null)
        {
            tagCompound = new NBTTagCompound();
            stack.setTagCompound(tagCompound);
        }

        return tagCompound;
    }
}
