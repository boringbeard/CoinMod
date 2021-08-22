package com.boringbread.client;

import com.boringbread.init.CoinItems;
import com.boringbread.init.CommonProxy;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(Side.CLIENT)
public class ClientProxy extends CommonProxy
{
    public void preInit()
    {
        super.preInit();
    }

    public void init() {
        super.init();
    }

    public void postInit() {
        super.postInit();
    }

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event)
    {
        CoinItems.initModels();
    }
}
