package io.github.mineria.decorationaddon.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.stream.Stream;

public class Jar extends Block {

    public static final VoxelShape SHAPE = makeShape();
    public static final IntegerProperty CONTAINS = IntegerProperty.create("contains", 0, 5);

    public Jar() {
        super(Properties.of(Material.GLASS).strength(0.5f, 0.1f).sound(SoundType.GLASS));
    }

    @Override
    public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        return SHAPE;
    }

    private static VoxelShape makeShape() {
        VoxelShape shape = Stream.of(
                Block.box(6, 10, 6, 10, 14, 10),
                Block.box(3, 0, 3, 13, 10, 13),
                Block.box(3, 0, 3, 13, 10, 13),
                Block.box(5, 15, 5, 11, 16, 11),
                Block.box(6, 14, 6, 10, 15, 10)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

        return shape;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(CONTAINS);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if(!level.isClientSide() && hand == InteractionHand.MAIN_HAND) {
            ItemStack stack = player.getItemInHand(hand);
            if(stack.is(Items.COOKIE)) {
                if(state.getValue(CONTAINS) < 5) {
                    stack.shrink(1);
                    level.setBlock(pos, state.cycle(CONTAINS), 3);
                    return InteractionResult.CONSUME;
                } else if(stack.getCount() < 64) {
                    stack.grow(1);
                    level.setBlock(pos, state.setValue(CONTAINS, state.getValue(CONTAINS) - 1), 3);
                    return InteractionResult.SUCCESS;
                }
            } else if(state.getValue(CONTAINS) > 0) {
                ItemEntity entity = new ItemEntity(level, pos.getX() + 0.5, pos.getY() + 1.5, pos.getZ() + 0.5, new ItemStack(Items.COOKIE));
                level.addFreshEntity(entity);
                level.setBlock(pos, state.setValue(CONTAINS, state.getValue(CONTAINS) - 1), 3);
                return InteractionResult.SUCCESS;
            }
        }
        return super.use(state, level, pos, player, hand, hit);
    }

    @Override
    public boolean onDestroyedByPlayer(BlockState state, Level level, BlockPos pos, Player player, boolean willHarvest, FluidState fluid) {
        if(!level.isClientSide() && state.getValue(CONTAINS) > 0) {
            ItemEntity items = new ItemEntity(level, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, new ItemStack(Items.COOKIE, state.getValue(CONTAINS)));
            level.addFreshEntity(items);
        }
        return super.onDestroyedByPlayer(state, level, pos, player, willHarvest, fluid);
    }
}
