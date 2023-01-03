package com.boringbread.init;

import com.boringbread.creativetab.CreativeTabCoinMod;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import org.apache.logging.log4j.Logger;

@Mod(
        modid = CoinMod.MOD_ID,
        name = CoinMod.NAME,
        version = CoinMod.VERSION
)
@Mod.EventBusSubscriber
public class CoinMod {
    public static final String MOD_ID = "coinmod";
    public static final String NAME = "Boring Coin Mod";
    public static final String VERSION = "0.0.2.1";

    public static int entityID = 0;
    public static int packetID = 0;

    public static CreativeTabCoinMod creativeTabCoinMod;

    @Instance(CoinMod.MOD_ID)
    public static CoinMod instance;

    public static Logger logger;

    @SidedProxy(clientSide = "com.boringbread.client.ClientProxy", serverSide = "com.boringbread.init.ServerProxy")
    public static IProxy proxy;

    @EventHandler
    public static void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
        proxy.preInit();
        creativeTabCoinMod = new CreativeTabCoinMod("coinmod_creative_tab");
    }

    @EventHandler
    public static void init(FMLInitializationEvent event)
    {
        proxy.init();
    }

    @EventHandler
    public static void postInit(FMLPostInitializationEvent event)
    {
        proxy.postInit();
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event)
    {
        CoinBlocks.registerBlocks(event);
        CoinTileEntities.registerTileEntities();
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event)
    {
        CoinItems.registerItems(event);
        CoinBlocks.registerItemBlocks(event);
    }

    @SubscribeEvent
    public static void registerEntities(RegistryEvent.Register<EntityEntry> event)
    {
        CoinEntities.registerEntities(event);
    }

    @SubscribeEvent
    public static void registerSoundEvents(RegistryEvent.Register<SoundEvent> event)
    {
        CoinSounds.registerSoundEvents(event);
    }
}
