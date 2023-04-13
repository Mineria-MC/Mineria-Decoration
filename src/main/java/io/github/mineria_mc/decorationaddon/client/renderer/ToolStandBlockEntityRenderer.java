package io.github.mineria_mc.decorationaddon.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import io.github.mineria_mc.decorationaddon.common.block.tool_stand.HorizontalToolStand;
import io.github.mineria_mc.decorationaddon.common.block.tool_stand.ToolStandBlockEntity;
import io.github.mineria_mc.decorationaddon.common.block.tool_stand.VerticalToolStand;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.core.Direction;
import net.minecraftforge.common.Tags;
import org.joml.Vector3d;

public class ToolStandBlockEntityRenderer implements BlockEntityRenderer<ToolStandBlockEntity> {

    private final ItemRenderer itemRenderer;

    public ToolStandBlockEntityRenderer(BlockEntityRendererProvider.Context ctx) {
        this.itemRenderer = ctx.getItemRenderer();
    }

    @Override
    public void render(ToolStandBlockEntity blockEntity, float partialTick, PoseStack stack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        stack.pushPose();
        if(blockEntity.getBlockState().getBlock() instanceof HorizontalToolStand) {
            Direction direction = blockEntity.getBlockState().getValue(HorizontalToolStand.FACING);
            stack.translate(0.5 + (1. / 32) * direction.getStepX(), 0.5 - 1. / 32, 0.5 + (1. / 32) * direction.getStepZ());
            stack.mulPose(Axis.YN.rotationDegrees(direction.toYRot()));
            stack.mulPose(Axis.XP.rotationDegrees(40));
            stack.scale(0.8f, 0.8f, 0.8f);
            stack.mulPose(Axis.ZN.rotation(40));
        } else if(blockEntity.getBlockState().getBlock() instanceof VerticalToolStand) {
            Direction direction = blockEntity.getBlockState().getValue(VerticalToolStand.FACING);
            Vector3d pos = new Vector3d(0.5, 0.5, 0.5);
            pos.add(1. / 32 * direction.getStepZ() - 5. / 16 * direction.getStepX(), 0, -5. / 16 * direction.getStepZ() - 1. / 32 * direction.getStepX());
            stack.translate(pos.x(), pos.y(), pos.z());
            stack.mulPose(Axis.YN.rotationDegrees(direction.toYRot()));
            stack.mulPose(blockEntity.getStoredTool().is(Tags.Items.TOOLS_SWORDS) ? Axis.ZP.rotationDegrees(135) : Axis.ZN.rotationDegrees(45));
            stack.scale(0.8f, 0.8f, 0.8f);
        }
        itemRenderer.renderStatic(null, blockEntity.getStoredTool(), ItemTransforms.TransformType.FIXED, false, stack, bufferSource, null, LightTexture.FULL_BRIGHT, packedOverlay, 0);
        stack.popPose();
    }
}
