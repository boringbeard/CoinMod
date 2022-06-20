package com.boringbread.init;

import com.boringbread.entity.item.EntityItemCoin;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;

public class CoinEntities
{
    public static final EntityEntry COIN = EntityEntryBuilder.create()
            .entity(EntityItemCoin.class)
            .id(new ResourceLocation(CoinMod.MOD_ID, "coin"), CoinMod.entityID++)
            .name("coin")
            .tracker(64, 20, true)
            .build();

    public static void registerEntities(RegistryEvent.Register<EntityEntry> event)
    {
        event.getRegistry().register(COIN);
    }
}
