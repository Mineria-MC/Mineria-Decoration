package io.github.mineria_mc.decorationaddon.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import io.github.mineria_mc.decorationaddon.common.block.tool_stand.ToolStandBlockEntity;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;

public class ToolStandBlockEntityRenderer implements BlockEntityRenderer<ToolStandBlockEntity> {

    private final ItemRenderer itemRenderer;

    public ToolStandBlockEntityRenderer(BlockEntityRendererProvider.Context ctx) {
        this.itemRenderer = ctx.getItemRenderer();
    }

    @Override
    public void render(ToolStandBlockEntity blockEntity, float partialTick, PoseStack stack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        stack.pushPose();
        stack.translate(0.5, 0.5, 0.5);
        stack.scale(0.8f, 0.8f, 0.8f);
        itemRenderer.renderStatic(null, blockEntity.getStoredTool(), ItemTransforms.TransformType.FIXED, false, stack, bufferSource, null, LightTexture.FULL_BRIGHT, packedOverlay, 0);
        stack.popPose();
    }
}
