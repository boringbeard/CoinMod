package com.boringbread.init;

import com.boringbread.block.BlockCoinstructor;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CoinBlocks
{
    public static final BlockCoinstructor COINSTRUCTOR = new BlockCoinstructor();

    public static final Item ITEM_COINSTRUCTOR = createItemBlock(COINSTRUCTOR, 64);

    public static void registerBlocks(RegistryEvent.Register<Block> event)
    {
        event.getRegistry().register(COINSTRUCTOR);
    }

    public static void registerItemBlocks(RegistryEvent.Register<Item> event)
    {
        event.getRegistry().register(ITEM_COINSTRUCTOR);
    }

    @SideOnly(Side.CLIENT)
    public static void initModels()
    {
        CoinItems.initModel(ITEM_COINSTRUCTOR, COINSTRUCTOR.name);
    }

    private static Item createItemBlock(Block block, int stackSize)
    {
        Item itemBlock = new ItemBlock(block);
        assert block.getRegistryName() != null;
        itemBlock.setRegistryName(block.getRegistryName());
        itemBlock.setMaxStackSize(stackSize);
        return itemBlock;
    }
}
