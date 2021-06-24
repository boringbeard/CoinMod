package com.boringbread.client;

import com.boringbread.common.CommonProxy;
import com.boringbread.common.item.ItemCoin;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(Side.CLIENT)
public class ClientProxy extends CommonProxy
{
    public void preInit()
    {
        super.preInit();
        ItemCoin.preInitClient();
    }

    public void init() {
        super.init();
    }

    public void postInit() {
        super.postInit();
    }
}
