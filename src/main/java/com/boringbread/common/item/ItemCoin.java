package com.boringbread.common.item;

import com.boringbread.common.CoinMod;
import com.boringbread.common.Utils;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

import static com.boringbread.common.CreativeTabCoinMod.creativeTabCoinMod;

public class ItemCoin extends Item
{
    public static final ItemCoin INSTANCE = new ItemCoin();

    private static final String NAME = "coin_basic";
    private static final int SUCCESS_RATE = 50;
    private static final int CHARGE_UP_TICKS = 10;
    private static final int FLIPPING_DURATION = 19;

    public ItemCoin()
    {
        setRegistryName(NAME);
        setUnlocalizedName(CoinMod.MOD_ID + "_" + NAME);
        setCreativeTab(creativeTabCoinMod);

        this.addPropertyOverride(new ResourceLocation("coinmod:donefraction"), new IItemPropertyGetter() {
            @Override
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn)
            {
                final float RESTING_INDEX = 2.0F;

                if(entityIn == null || !(stack.getItem() instanceof ItemCoin) || entityIn.getHeldItemMainhand() != stack) return RESTING_INDEX;

                if(worldIn == null) worldIn = entityIn.world;

                if(!isAnimating(entityIn, stack)) return RESTING_INDEX;

                return Utils.getTagCompoundSafe(stack).getInteger("timer") / (float) FLIPPING_DURATION;
            }
        });

        this.addPropertyOverride(new ResourceLocation("coinmod:side"), new IItemPropertyGetter()
        {
            @Override
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn)
            {
                final float STILL_ANIMATING_INDEX = 0.0F;

                if(isAnimating(entityIn, stack)) return STILL_ANIMATING_INDEX;

                final float DRAW_HEADS = 1.0F;
                final float DRAW_TAILS = 2.0F;

                return Utils.getTagCompoundSafe(stack).hasKey("isHeads") ? DRAW_HEADS : DRAW_TAILS;
            }
        });

        this.addPropertyOverride(new ResourceLocation("coinmod:inHand"), new IItemPropertyGetter()
        {
            @Override
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn)
            {
                final float IN_HAND_INDEX = 1.0F;
                final float NOT_IN_HAND_INDEX = 0.0F;
                return entityIn != null && entityIn.getHeldItemMainhand() == stack ? IN_HAND_INDEX : NOT_IN_HAND_INDEX;
            }
        });
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand)
    {
        ItemStack stack = playerIn.getHeldItem(hand);

        if(!worldIn.isRemote)
        {
            if(stack.getItem() instanceof ItemCoin && playerIn.isSneaking() && !isAnimating(playerIn, stack))
            {
                playerIn.setActiveHand(hand);
            }
        }

        return new ActionResult<>(EnumActionResult.FAIL, stack);
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft)
    {
        if(entityLiving.isSneaking() && !isAnimating(entityLiving, stack) && stack.getMaxItemUseDuration() - entityLiving.getItemInUseCount() > CHARGE_UP_TICKS)
        {
            if(!worldIn.isRemote)
            {
                Utils.getTagCompoundSafe(stack).setBoolean("animating", true);

                if (SUCCESS_RATE > Math.random() * 100)
                {
                    Utils.getTagCompoundSafe(stack).setBoolean("isHeads", true);
                }
                else
                {
                    Utils.getTagCompoundSafe(stack).removeTag("isHeads");
                }
            }
        }
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) { return 100; }

    public static int getChargeUpTicks()
    {
        return CHARGE_UP_TICKS;
    }

    public static int getFlippingDuration()
    {
        return FLIPPING_DURATION;
    }

    public static void initModel()
    {
        ModelResourceLocation itemModelResourceLocation = new ModelResourceLocation("coinmod:coin_basic", "inventory");
        ModelLoader.setCustomModelResourceLocation(INSTANCE, 0, itemModelResourceLocation);
    }

    public boolean isAnimating(EntityLivingBase entityIn, ItemStack stack)
    {
        if(entityIn == null || entityIn.getHeldItemMainhand() != stack) return false;

        return Utils.getTagCompoundSafe(stack).hasKey("animating");
    }
}