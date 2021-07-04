package com.boringbread.client;

import com.boringbread.util.Utils;
import com.boringbread.item.ItemCoin;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod.EventBusSubscriber
public class EventHandler
{
    private static float newFOV = 0.0F;

    @SubscribeEvent
    public static void onLivingUpdate(LivingUpdateEvent event)
    {
        EntityLivingBase entity = event.getEntityLiving();
        ItemStack stack =  entity.getHeldItemMainhand();
        Item heldItem = stack.getItem();

        if(!(heldItem instanceof ItemCoin) || !((ItemCoin) heldItem).isAnimating(entity, stack) || entity.world.isRemote) return;

        Utils.getTagCompoundSafe(stack).setInteger("timer", Utils.getTagCompoundSafe(stack).getInteger("timer") + 1);

        if(Utils.getTagCompoundSafe(stack).getInteger("timer") >= ItemCoin.getFlippingDuration())
        {
            Utils.getTagCompoundSafe(stack).removeTag("timer");
            Utils.getTagCompoundSafe(stack).removeTag("animating");
        }
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void onRenderPlayerPre(RenderPlayerEvent.Pre event)
    {
        AbstractClientPlayer clientPlayer = (AbstractClientPlayer)event.getEntityPlayer();
        ItemStack stack = clientPlayer.getHeldItemMainhand();
        Item heldItem = stack.getItem();
        boolean smallArms = (clientPlayer.getSkinType() == "slim");

        if(!(heldItem instanceof ItemCoin)) return;

        if(clientPlayer.isHandActive() || ((ItemCoin) heldItem).isAnimating(clientPlayer, stack))
        {
            event.setCanceled(true);
            RenderPlayerHoldingCoin renderPlayerHoldingCoin = new RenderPlayerHoldingCoin(Minecraft.getMinecraft().getRenderManager(), smallArms);
            renderPlayerHoldingCoin.doRender(clientPlayer, event.getX(), event.getY(), event.getZ(), clientPlayer.rotationYaw, event.getPartialRenderTick());
        }
        else
        {
            event.setCanceled(false);
        }
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void onUpdateFov(FOVUpdateEvent event)
    {
        EntityPlayer player = event.getEntity();
        Item activeItem = player.getActiveItemStack().getItem();

        if(!(activeItem instanceof ItemCoin))
        {
            newFOV = 0.0F;
            return;
        }

        final float totalChargeUpZoom = 0.15F;
        final float zoomIncrement = totalChargeUpZoom / ItemCoin.getChargeUpTicks();
        int ticksInUse = activeItem.getMaxItemUseDuration(player.getActiveItemStack()) - player.getItemInUseCount();

        if(ticksInUse < ItemCoin.getChargeUpTicks()) newFOV -= zoomIncrement;

        event.setNewfov(event.getFov() + newFOV);
    }
}
