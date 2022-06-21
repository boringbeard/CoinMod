package com.boringbread.client.renderer.entity;

import com.boringbread.entity.item.EntityItemCoin;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.RenderEntityItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;

public class RenderEntityItemCoin<E extends EntityItemCoin> extends RenderEntityItem
{
    private final RenderItem itemRenderer = Minecraft.getMinecraft().getRenderItem();

    public RenderEntityItemCoin(RenderManager renderManager)
    {
        super(renderManager, Minecraft.getMinecraft().getRenderItem());
    }

    @Override
    public void doRender(EntityItem entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
        ItemStack stack = entity.getItem();

        if (this.bindEntityTexture(entity))
        {
            this.renderManager.renderEngine.getTexture(getEntityTexture(entity)).setBlurMipmap(false, false);
        }

        GlStateManager.enableRescaleNormal();
        GlStateManager.alphaFunc(516, 0.1F);
        GlStateManager.enableBlend();
        RenderHelper.enableStandardItemLighting();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.pushMatrix();

        IBakedModel model = itemRenderer.getItemModelWithOverrides(stack, entity.world, null);

        GlStateManager.translate((float) x, (float) y, (float) z);

        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

        if (this.renderOutlines)
        {
            GlStateManager.enableColorMaterial();
            GlStateManager.enableOutlineMode(this.getTeamColor(entity));
        }

        GlStateManager.pushMatrix();
        GlStateManager.rotate(entityYaw, 0.0F, -1.0F, 0.0F);
        IBakedModel transformedModel = net.minecraftforge.client.ForgeHooksClient.handleCameraTransforms(model, ItemCameraTransforms.TransformType.GROUND, false);

        if(!entity.onGround)
        {
            float flip = ((float) entity.getAge() + partialTicks) * 90;
            GlStateManager.rotate(flip, 1.0F, 0.0F, 0.0F);
        }
        else
        {
            GlStateManager.rotate(-90.0F, 1.0F, 0.0F, 0.0F);
        }

        this.itemRenderer.renderItem(stack, transformedModel);
        GlStateManager.popMatrix();
        GlStateManager.translate(0.0F, 0.0F, 0.09375F);

        if (this.renderOutlines)
        {
            GlStateManager.disableOutlineMode();
            GlStateManager.disableColorMaterial();
        }

        GlStateManager.popMatrix();
        GlStateManager.disableRescaleNormal();
        GlStateManager.disableBlend();
        this.bindEntityTexture(entity);

        if (!this.renderOutlines)
        {
            this.renderName(entity, x, y, z);
        }
    }
}
