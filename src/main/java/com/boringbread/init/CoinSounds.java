package com.boringbread.init;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;

public class CoinSounds
{
    public static final SoundEvent COIN_FLIP_METAL = createSoundEvent("coin_flip_metal");

    public static void registerSoundEvents(RegistryEvent.Register<SoundEvent> event)
    {
        event.getRegistry().register(COIN_FLIP_METAL);
    }

    private static SoundEvent createSoundEvent(String name)
    {
        SoundEvent event = new SoundEvent(new ResourceLocation(CoinMod.MOD_ID, name));
        event.setRegistryName(CoinMod.MOD_ID, name);
        return event;
    }
}
