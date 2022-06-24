package com.boringbread.init;

import com.boringbread.tileentity.TileEntityCoinstructor;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CoinTileEntities
{
    public static void registerTileEntities()
    {
        GameRegistry.registerTileEntity(TileEntityCoinstructor.class, new ResourceLocation(CoinMod.MOD_ID, CoinBlocks.COINSTRUCTOR.name));
    }
}
