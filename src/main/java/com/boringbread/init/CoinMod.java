package com.boringbread.init;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(
        modid = CoinMod.MOD_ID,
        name = CoinMod.NAME,
        version = CoinMod.VERSION
)
public class CoinMod {
    public static final String MOD_ID = "coinmod";
    public static final String NAME = "Boring Coin Mod";
    public static final String VERSION = "0.0.2";

    @Instance(CoinMod.MOD_ID)
    public static CoinMod instance;

    public static Logger logger;

    @SidedProxy(clientSide = "com.boringbread.client.ClientProxy", serverSide = "com.boringbread.init.DedicatedServerProxy")
    public static CommonProxy proxy;

    @EventHandler
    public static void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
        proxy.preInit();
    }

    @EventHandler
    public static void init(FMLInitializationEvent event){}

    @EventHandler
    public static void postInit(FMLPostInitializationEvent event){}
}
