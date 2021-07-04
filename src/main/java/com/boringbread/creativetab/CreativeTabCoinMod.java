package com.boringbread.creativetab;

import com.boringbread.item.ItemCoin;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CreativeTabCoinMod extends CreativeTabs{
    public static CreativeTabCoinMod creativeTabCoinMod;

    public CreativeTabCoinMod(String label){
        super(label);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ItemStack getTabIconItem(){
        return new ItemStack(ItemCoin.INSTANCE);
    }

    public static void preInitCommon(){
        creativeTabCoinMod = new CreativeTabCoinMod("coinmod_creative_tab");
    }
}
