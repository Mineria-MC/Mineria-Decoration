package io.github.mineria.decorationaddon.common.block.manufacturing_table;

import io.github.mineria.decorationaddon.common.init.MDATileEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

public class ManufacturingTable extends Block implements EntityBlock {

    public static final VoxelShape[] SHAPES = makeShape();
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    private static final Component CONTAINER_TITLE = Component.translatable("container.manufacturing_table");

    public ManufacturingTable() {
        super(Properties.of(Material.WOOD).strength(2.5f, 0f).sound(SoundType.WOOD));
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        BlockEntity entity = level.getBlockEntity(pos);
        if(!level.isClientSide && entity instanceof ManufacturingTableTileEntity tile) {
            NetworkHooks.openScreen((ServerPlayer) player, tile, pos);
            return InteractionResult.SUCCESS;
        }
        return super.use(state, level, pos, player, hand, hitResult);
    }

    @Override
    public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        if(p_60555_.getValue(FACING).getName().equals("north")) return SHAPES[0];
        if(p_60555_.getValue(FACING).getName().equals("east")) return SHAPES[3];
        if(p_60555_.getValue(FACING).getName().equals("south")) return SHAPES[2];
        if(p_60555_.getValue(FACING).getName().equals("west")) return SHAPES[1];


        return SHAPES[0];
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection());
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

    private static VoxelShape[] makeShape(){
        VoxelShape shapeNorth = Stream.of(
                Stream.of(
                        Block.box(4, 13, 13, 6, 14, 14),
                        Block.box(2, 13, 10, 3, 14, 12),
                        Block.box(6, 13, 10, 8, 14, 12),
                        Block.box(4, 13, 8, 8, 14, 10),
                        Block.box(4, 13, 12, 7, 14, 13),
                        Block.box(3, 13, 9, 4, 14, 13),
                        Block.box(11, 13, 1, 14, 14, 3),
                        Block.box(9, 13, 3, 13, 14, 5),
                        Block.box(8, 13, 7, 11, 14, 9),
                        Block.box(8, 13, 9, 10, 14, 10),
                        Block.box(10, 13, 2, 11, 14, 3),
                        Block.box(8, 13, 4, 9, 14, 5),
                        Block.box(6, 13, 6, 7, 14, 7),
                        Block.box(8, 13, 10, 9, 14, 11),
                        Block.box(7, 13, 5, 12, 14, 7),
                        Block.box(5, 13, 7, 8, 14, 8)
                ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
                        Block.box(1, 13, 1, 3, 15, 3),
                        Block.box(0, 12, 0, 16, 13, 16),
                        Block.box(3, 0, 12, 13, 12, 13),
                        Block.box(3, 0, 3, 13, 12, 4)
                ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

        VoxelShape shapeEast = Stream.of(
                Stream.of(
                        Block.box(13, 13, 10, 14, 14, 12),
                        Block.box(10, 13, 13, 12, 14, 14),
                        Block.box(10, 13, 8, 12, 14, 10),
                        Block.box(8, 13, 8, 10, 14, 12),
                        Block.box(12, 13, 9, 13, 14, 12),
                        Block.box(9, 13, 12, 13, 14, 13),
                        Block.box(1, 13, 2, 3, 14, 5),
                        Block.box(3, 13, 3, 5, 14, 7),
                        Block.box(7, 13, 5, 9, 14, 8),
                        Block.box(9, 13, 6, 10, 14, 8),
                        Block.box(2, 13, 5, 3, 14, 6),
                        Block.box(4, 13, 7, 5, 14, 8),
                        Block.box(6, 13, 9, 7, 14, 10),
                        Block.box(10, 13, 7, 11, 14, 8),
                        Block.box(5, 13, 4, 7, 14, 9),
                        Block.box(7, 13, 8, 8, 14, 11)
                ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
                        Block.box(1, 13, 13, 3, 15, 15),
                        Block.box(0, 12, 0, 16, 13, 16),
                        Block.box(12, 0, 3, 13, 12, 13),
                        Block.box(3, 0, 3, 4, 12, 13)
                ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

        VoxelShape shapeSouth = Stream.of(
                Stream.of(
                        Block.box(10, 13, 2, 12, 14, 3),
                        Block.box(13, 13, 4, 14, 14, 6),
                        Block.box(8, 13, 4, 10, 14, 6),
                        Block.box(8, 13, 6, 12, 14, 8),
                        Block.box(9, 13, 3, 12, 14, 4),
                        Block.box(12, 13, 3, 13, 14, 7),
                        Block.box(2, 13, 13, 5, 14, 15),
                        Block.box(3, 13, 11, 7, 14, 13),
                        Block.box(5, 13, 7, 8, 14, 9),
                        Block.box(6, 13, 6, 8, 14, 7),
                        Block.box(5, 13, 13, 6, 14, 14),
                        Block.box(7, 13, 11, 8, 14, 12),
                        Block.box(9, 13, 9, 10, 14, 10),
                        Block.box(7, 13, 5, 8, 14, 6),
                        Block.box(4, 13, 9, 9, 14, 11),
                        Block.box(8, 13, 8, 11, 14, 9)
                ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
                        Block.box(13, 13, 13, 15, 15, 15),
                        Block.box(0, 12, 0, 16, 13, 16),
                        Block.box(3, 0, 3, 13, 12, 4),
                        Block.box(3, 0, 12, 13, 12, 13)
                ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

        VoxelShape shapeWest = Stream.of(
                Stream.of(
                        Block.box(2, 13, 4, 3, 14, 6),
                        Block.box(4, 13, 2, 6, 14, 3),
                        Block.box(4, 13, 6, 6, 14, 8),
                        Block.box(6, 13, 4, 8, 14, 8),
                        Block.box(3, 13, 4, 4, 14, 7),
                        Block.box(3, 13, 3, 7, 14, 4),
                        Block.box(13, 13, 11, 15, 14, 14),
                        Block.box(11, 13, 9, 13, 14, 13),
                        Block.box(7, 13, 8, 9, 14, 11),
                        Block.box(6, 13, 8, 7, 14, 10),
                        Block.box(13, 13, 10, 14, 14, 11),
                        Block.box(11, 13, 8, 12, 14, 9),
                        Block.box(9, 13, 6, 10, 14, 7),
                        Block.box(5, 13, 8, 6, 14, 9),
                        Block.box(9, 13, 7, 11, 14, 12),
                        Block.box(8, 13, 5, 9, 14, 8)
                ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
                        Block.box(13, 13, 1, 15, 15, 3),
                        Block.box(0, 12, 0, 16, 13, 16),
                        Block.box(3, 0, 3, 4, 12, 13),
                        Block.box(12, 0, 3, 13, 12, 13)
                ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

        VoxelShape[] shapes = new VoxelShape[] { shapeNorth, shapeEast, shapeSouth, shapeWest };
        return shapes;
    }

    @Override
    public RenderShape getRenderShape(BlockState p_60550_) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos p_153215_, BlockState p_153216_) {
        return MDATileEntities.MANUFACTURING_TABLE.get().create(p_153215_, p_153216_);
    }
}
