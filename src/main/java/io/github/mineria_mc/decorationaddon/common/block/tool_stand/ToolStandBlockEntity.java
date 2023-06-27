package io.github.mineria_mc.decorationaddon.common.block.tool_stand;

import io.github.mineria_mc.decorationaddon.common.init.MDAItems;
import io.github.mineria_mc.decorationaddon.common.init.MDATileEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ToolStandBlockEntity extends BlockEntity {
    private ItemStack storedTool = ItemStack.EMPTY;

    public ToolStandBlockEntity(BlockPos pos, BlockState state) {
        super(MDATileEntities.TOOL_STAND.get(), pos, state);
    }

    public boolean storeTool(ItemStack stack) {
        if(!storedTool.isEmpty()) {
            return false;
        }
        if(!isValidTool(stack)) {
            return false;
        }
        storedTool = stack;
        setChanged();
        return true;
    }

    public ItemStack pickTool() {
        if(storedTool.isEmpty()) {
            return ItemStack.EMPTY;
        }
        ItemStack tool = storedTool;
        storedTool = ItemStack.EMPTY;
        setChanged();
        return tool;
    }

    public ItemStack getStoredTool() {
        return storedTool;
    }

    private boolean isValidTool(ItemStack stack) {
        return stack.is(MDAItems.Tags.TOOL_STAND_VALID);
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag nbt) {
        super.saveAdditional(nbt);
        nbt.put("StoredTool", storedTool.save(new CompoundTag()));
    }

    @Override
    public void load(@NotNull CompoundTag nbt) {
        super.load(nbt);
        this.storedTool = ItemStack.of(nbt.getCompound("StoredTool"));
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
