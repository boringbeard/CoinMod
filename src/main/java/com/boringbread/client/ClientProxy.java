package com.boringbread.client;

import com.boringbread.client.renderer.entity.RenderEntityItemCoin;
import com.boringbread.client.renderer.tileentity.TileEntityCoinstructorRenderer;
import com.boringbread.entity.item.EntityItemCoin;
import com.boringbread.init.CoinBlocks;
import com.boringbread.init.CoinItems;
import com.boringbread.init.CoinMod;
import com.boringbread.init.IProxy;
import com.boringbread.network.CoinPacketHandler;
import com.boringbread.network.MessageTileEntitySync;
import com.boringbread.tileentity.TileEntityCoinstructor;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(Side.CLIENT)
public class ClientProxy implements IProxy
{
    @Override
    public void preInit()
    {
        RenderingRegistry.registerEntityRenderingHandler(EntityItemCoin.class, RenderEntityItemCoin::new);
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCoinstructor.class, new TileEntityCoinstructorRenderer());
        CoinPacketHandler.NETWORK_WRAPPER.registerMessage(MessageTileEntitySync.Handler.class, MessageTileEntitySync.class, CoinMod.packetID++, Side.CLIENT);
    }

    @Override
    public void init() {
    }

    @Override
    public void postInit() {
    }

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event)
    {
        CoinItems.initModels();
        CoinBlocks.initModels();
    }
}
