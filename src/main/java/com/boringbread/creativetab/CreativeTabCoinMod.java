package com.boringbread.creativetab;

import com.boringbread.init.CoinItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CreativeTabCoinMod extends CreativeTabs{
    public CreativeTabCoinMod(String label){
        super(label);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ItemStack getTabIconItem(){
        return new ItemStack(CoinItems.COIN_GOLD);
    }
}
