package com.boringbread.client;

import com.boringbread.util.Utils;
import com.boringbread.item.ItemCoin;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod.EventBusSubscriber
public class EventHandler
{
    @SideOnly(Side.CLIENT)
    private static float newFOV = 0.0F;

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void onRenderLivingPre(RenderLivingEvent.Pre event)
    {
        if(event.getEntity() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.getEntity();
            ItemStack stack = player.getHeldItemMainhand();
            Item heldItem = stack.getItem();

            if(player.isHandActive() && heldItem instanceof ItemCoin)
            {
                ModelBiped model = (ModelBiped) event.getRenderer().getMainModel();
                model.rightArmPose = ModelBiped.ArmPose.BOW_AND_ARROW;
            }
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

        ItemCoin activeCoin = (ItemCoin) activeItem;
        final float totalChargeUpZoom = 0.15F;
        final float zoomIncrement = totalChargeUpZoom / activeCoin.getChargeUpTicks();
        int ticksInUse = activeItem.getMaxItemUseDuration(player.getActiveItemStack()) - player.getItemInUseCount();

        if(ticksInUse < activeCoin.getChargeUpTicks()) newFOV -= zoomIncrement;

        event.setNewfov(event.getFov() + newFOV);
    }
}
