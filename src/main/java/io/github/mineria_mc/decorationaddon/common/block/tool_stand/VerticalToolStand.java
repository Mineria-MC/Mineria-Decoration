package io.github.mineria_mc.decorationaddon.common.block.tool_stand;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class VerticalToolStand extends ToolStandBlock {

    private static final VoxelShape SHAPE_SOUTH = Block.box(5, 1, 0, 12, 15, 8);
    private static final VoxelShape SHAPE_NORTH = Block.box(4, 1, 8, 11, 15, 16);
    private static final VoxelShape SHAPE_EAST = Block.box(0, 1, 4, 8, 15, 11);
    private static final VoxelShape SHAPE_WEST = Block.box(8, 1, 5, 16, 15, 12);
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    public VerticalToolStand() {
        super(Properties.of(Material.WOOD).strength(0.5f, 0.3f).sound(SoundType.WOOD).noOcclusion());
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return switch (pState.getValue(FACING)) {
            case NORTH -> SHAPE_NORTH;
            case EAST -> SHAPE_EAST;
            case WEST -> SHAPE_WEST;
            default -> SHAPE_SOUTH;
        };
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    public BlockState rotate(BlockState state, Rotation direction) {
        return state.setValue(FACING, direction.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }
}
