package com.boringbread.client.renderer.tileentity;

import com.boringbread.tileentity.TileEntityCoinstructor;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;

public class TileEntityCoinstructorRenderer extends TileEntitySpecialRenderer<TileEntityCoinstructor>
{
    @Override
    public void render(TileEntityCoinstructor te, double x, double y, double z, float partialTicks, int destroyStage, float alpha)
    {
        super.render(te, x, y, z, partialTicks, destroyStage, alpha);

        IBlockState blockState = getWorld().getBlockState(te.getPos());

        GlStateManager.enableRescaleNormal();
        GlStateManager.pushMatrix();
        RenderHelper.enableStandardItemLighting();

        GlStateManager.translate(x + 0.5F, y + 1.0625F, z + 0.5F);
        GlStateManager.rotate(blockState.getValue(BlockHorizontal.FACING).getHorizontalAngle(), 0.0F, -1.0F, 0.0F);
        GlStateManager.rotate(-90.0F, 1.0F, 0.0F, 0.0F);

        ItemStack mainSlot = te.getItemStackHandler().getStackInSlot(TileEntityCoinstructor.MIDDLE_SLOT);
        ItemStack leftSlot = te.getItemStackHandler().getStackInSlot(TileEntityCoinstructor.LEFT_SLOT);
        ItemStack rightSlot = te.getItemStackHandler().getStackInSlot(TileEntityCoinstructor.RIGHT_SLOT);

        if (!mainSlot.isEmpty())
        {
            GlStateManager.pushMatrix();
            GlStateManager.translate(0.0F, -0.125F, 0.0F);
            Minecraft.getMinecraft().getRenderItem().renderItem(mainSlot, ItemCameraTransforms.TransformType.GROUND);
            GlStateManager.popMatrix();
        }
        if (!leftSlot.isEmpty())
        {
            GlStateManager.pushMatrix();
            GlStateManager.translate(-0.3125F, -0.0625F, 0.0F);
            GlStateManager.scale(0.5, 0.5, 0.5);
            Minecraft.getMinecraft().getRenderItem().renderItem(leftSlot, ItemCameraTransforms.TransformType.GROUND);
            GlStateManager.popMatrix();
        }
        if (!rightSlot.isEmpty())
        {
            GlStateManager.pushMatrix();
            GlStateManager.translate(0.3125F, -0.0625F, 0.0F);
            GlStateManager.scale(0.5, 0.5, 0.5);
            Minecraft.getMinecraft().getRenderItem().renderItem(rightSlot, ItemCameraTransforms.TransformType.GROUND);
            GlStateManager.popMatrix();
        }

        GlStateManager.popMatrix();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();
    }
}
