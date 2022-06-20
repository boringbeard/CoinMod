package com.boringbread.init;

import com.boringbread.item.ItemCoin;
import com.boringbread.creativetab.CreativeTabCoinMod;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;

@Mod.EventBusSubscriber
public class CommonProxy {
    public void preInit()
    {
        CreativeTabCoinMod.preInitCommon();
    }

    public void init()
    {

    }

    public void postInit()
    {

    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event)
    {

    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event)
    {
        CoinItems.registerItems(event);
    }

    @SubscribeEvent
    public static void registerEntities(RegistryEvent.Register<EntityEntry> event)
    {
        CoinEntities.registerEntities(event);
    }
}
