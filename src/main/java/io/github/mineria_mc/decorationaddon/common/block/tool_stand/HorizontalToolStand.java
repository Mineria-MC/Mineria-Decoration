package io.github.mineria_mc.decorationaddon.common.block.tool_stand;

import io.github.mineria_mc.decorationaddon.common.init.MDATileEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class HorizontalToolStand extends Block implements EntityBlock {

    private static final VoxelShape SHAPE = makeShape();
    public HorizontalToolStand() {
        super(Properties.of(Material.WOOD).strength(0.5f, 0.3f).sound(SoundType.WOOD));
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    private static VoxelShape makeShape() {
        return Block.box(0, 0, 0, 16, 16, 16);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        BlockEntity be = level.getBlockEntity(pos);
        if(be instanceof ToolStandBlockEntity toolStand) {
            ItemStack stack = player.getItemInHand(hand);
            if(stack.isEmpty()) {
                ItemStack obtained = toolStand.pickTool();
                if(!obtained.isEmpty()) {
                    player.setItemInHand(hand, obtained);
                    return InteractionResult.sidedSuccess(level.isClientSide);
                }
            } else {
                if(toolStand.storeTool(stack)) {
                    player.setItemInHand(hand, ItemStack.EMPTY);
                    return InteractionResult.sidedSuccess(level.isClientSide);
                }
            }
        }
        return super.use(state, level, pos, player, hand, hit);
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
        if(blockEntity instanceof ToolStandBlockEntity toolStand && !toolStand.getStoredTool().isEmpty()) {
            Containers.dropItemStack(pLevel, pPos.getX() + 0.5, pPos.getY() + 0.5, pPos.getZ() + 0.5, toolStand.getStoredTool());
        }

        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return MDATileEntities.TOOL_STAND.get().create(pPos, pState);
    }
}
