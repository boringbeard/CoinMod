package com.boringbread.item;

import com.boringbread.entity.item.EntityItemCoin;
import com.boringbread.init.CoinMod;
import com.boringbread.init.CoinSounds;
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

    public ItemCoin(String name, int successRate, int chargeUpTicks)
    {
        this.name = name;
        this.successRate = successRate;
        this.chargeUpTicks = chargeUpTicks;

        setRegistryName(this.name);
        setUnlocalizedName(CoinMod.MOD_ID + "_" + this.name);
        setCreativeTab(creativeTabCoinMod);

        this.addPropertyOverride(new ResourceLocation("coinmod:side"), new IItemPropertyGetter()
        {
            @Override
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn)
            {
                final float DRAW_HEADS = 0.0F;
                final float DRAW_TAILS = 1.0F;

                return Utils.getTagCompoundSafe(stack).hasKey("isTails") ? DRAW_TAILS : DRAW_HEADS;
            }
        });
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand)
    {
        ItemStack stack = playerIn.getHeldItem(hand);

        if(!worldIn.isRemote)
        {
            if(stack.getItem() instanceof ItemCoin && playerIn.isSneaking())
            {
                playerIn.setActiveHand(hand);
            }
        }

        return new ActionResult<>(EnumActionResult.FAIL, stack);
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft)
    {
        if(entityLiving.isSneaking())
        {
            if(!worldIn.isRemote)
            {
                ItemStack flippedCoin = stack.splitStack(1);

                if (successRate > Math.random() * 100)
                {
                    Utils.getTagCompoundSafe(flippedCoin).setBoolean("isTails", true);
                }
                else
                {
                    Utils.getTagCompoundSafe(flippedCoin).removeTag("isTails");
                }

                EntityItemCoin coin = new EntityItemCoin(worldIn);
                coin.setItem(flippedCoin);
                coin.setDefaultPickupDelay();
                coin.copyLocationAndAnglesFrom(entityLiving);
                coin.posX += Math.sin( Math.toRadians(entityLiving.rotationYaw) ) * -0.75; //spawns coin in front of players face
                coin.posZ += Math.cos( Math.toRadians(entityLiving.rotationYaw) ) * 0.75;
                coin.posY += entityLiving.getEyeHeight() - 0.4;

                int ticksInUse = getMaxItemUseDuration(stack) - timeLeft;
                float power = (Math.min(ticksInUse, chargeUpTicks) / 15.0F) + 0.1F;

                coin.motionX = entityLiving.motionX;
                coin.motionY = entityLiving.motionY + power;
                coin.motionZ = entityLiving.motionZ;
                worldIn.spawnEntity(coin);
                coin.playSound(CoinSounds.COIN_FLIP_METAL, 1.0F, 1.0F);
            }
        }
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) { return 100; }

    public void onFlipped(boolean isHeads)
    {

    }

    public int getChargeUpTicks()
    {
        return chargeUpTicks;
    }
}