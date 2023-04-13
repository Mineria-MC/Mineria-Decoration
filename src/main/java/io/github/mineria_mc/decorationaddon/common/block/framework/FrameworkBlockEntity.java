package io.github.mineria_mc.decorationaddon.common.block.framework;

import io.github.mineria_mc.decorationaddon.common.init.MDATileEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class FrameworkBlockEntity extends BlockEntity {

    private ItemStack storedPainting = ItemStack.EMPTY;

    public FrameworkBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(MDATileEntities.FRAMEWORK.get(), pPos, pBlockState);
    }

    public boolean storePainting(ItemStack stack) {
        if(!storedPainting.isEmpty()) return false;
        if(stack.getItem() != Items.PAINTING) return false;

        storedPainting = stack;
        setChanged();
        return true;
    }

    public ItemStack pickPainting() {
        if(storedPainting.isEmpty()) return ItemStack.EMPTY;
        ItemStack painting = storedPainting;
        storedPainting = ItemStack.EMPTY;
        setChanged();

        return painting;
    }

    public ItemStack getStoredPainting() {
        return storedPainting;
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.put("StoredPainting", storedPainting.save(new CompoundTag()));
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        this.storedPainting = ItemStack.of(pTag.getCompound("StoredPainting"));
    }

    @Override
    public CompoundTag getUpdateTag() {
        return saveWithoutMetadata();
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
}
