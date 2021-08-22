package com.boringbread.item;

import com.boringbread.init.CoinMod;
import com.boringbread.util.Utils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

import static com.boringbread.creativetab.CreativeTabCoinMod.creativeTabCoinMod;

public class ItemCoin extends Item
{
    public final String name;
    private final int successRate;
    private final int chargeUpTicks;
    private final int flippingDuration;

    public ItemCoin(String name, int successRate, int chargeUpTicks, int flippingDuration)
    {
        this.name = name;
        this.successRate = successRate;
        this.chargeUpTicks = chargeUpTicks;
        this.flippingDuration = flippingDuration;

        setRegistryName(this.name);
        setUnlocalizedName(CoinMod.MOD_ID + "_" + this.name);
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

                return Utils.getTagCompoundSafe(stack).getInteger("timer") / (float) ItemCoin.this.flippingDuration;
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
        if(entityLiving.isSneaking() && !isAnimating(entityLiving, stack) && stack.getMaxItemUseDuration() - entityLiving.getItemInUseCount() > chargeUpTicks)
        {
            if(!worldIn.isRemote)
            {
                Utils.getTagCompoundSafe(stack).setBoolean("animating", true);

                if (successRate > Math.random() * 100)
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

    public int getChargeUpTicks()
    {
        return chargeUpTicks;
    }

    public int getFlippingDuration()
    {
        return flippingDuration;
    }

    public boolean isAnimating(EntityLivingBase entityIn, ItemStack stack)
    {
        if(entityIn == null || entityIn.getHeldItemMainhand() != stack) return false;

        return Utils.getTagCompoundSafe(stack).hasKey("animating");
    }
}