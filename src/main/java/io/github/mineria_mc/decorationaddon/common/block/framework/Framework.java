package io.github.mineria_mc.decorationaddon.common.block.framework;

import io.github.mineria_mc.decorationaddon.common.block.MDACustomBlock;
import io.github.mineria_mc.decorationaddon.common.init.MDATileEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class Framework extends MDACustomBlock implements EntityBlock {
    public Framework() {
        super(Properties.of(Material.HEAVY_METAL).sound(SoundType.METAL));
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        BlockEntity entity = pLevel.getBlockEntity(pPos);
        if(entity instanceof FrameworkBlockEntity frameworkEntity) {
            ItemStack stack = pPlayer.getItemInHand(pHand);
            if(stack.isEmpty()) {
                ItemStack obtained = frameworkEntity.pickPainting();
                if(!obtained.isEmpty()) {
                    pPlayer.setItemInHand(pHand, obtained);
                    return InteractionResult.sidedSuccess(pLevel.isClientSide);
                }
            }else {
                if(frameworkEntity.storePainting(stack)) {
                    pPlayer.setItemInHand(pHand, ItemStack.EMPTY);
                    return InteractionResult.sidedSuccess(pLevel.isClientSide);
                }
            }
        }
        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        BlockEntity entity = pLevel.getBlockEntity(pPos);
        if(entity instanceof FrameworkBlockEntity frameworkEntity && !frameworkEntity.getStoredPainting().isEmpty()) {
            Containers.dropItemStack(pLevel, pPos.getX() + 0.5, pPos.getY() + 0.5, pPos.getZ() + 0.5, frameworkEntity.getStoredPainting());
        }

        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return MDATileEntities.FRAMEWORK.get().create(pPos, pState);
    }
}
