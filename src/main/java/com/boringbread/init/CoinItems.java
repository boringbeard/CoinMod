package com.boringbread.init;

import com.boringbread.item.ItemCoin;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CoinItems
{
    public static final ItemCoin COIN_GOLD = new ItemCoin("coin_gold", 50, 10);
    public static final ItemCoin COIN_IRON = new ItemCoin("coin_iron", 50, 10);

    @SideOnly(Side.CLIENT)
    public static void initModels()
    {
        initModel(COIN_GOLD, COIN_GOLD.name);
        initModel(COIN_IRON, COIN_IRON.name);
    }

    @SideOnly(Side.CLIENT)
    public static void initModel(Item item, String name)
    {
        ModelResourceLocation location = new ModelResourceLocation(CoinMod.MOD_ID + ":" + name, "inventory");
        ModelLoader.setCustomModelResourceLocation(item, 0, location);
    }

    public static void registerItems(RegistryEvent.Register<Item> event)
    {
        event.getRegistry().register(COIN_GOLD);
        event.getRegistry().register(COIN_IRON);
    }
}
