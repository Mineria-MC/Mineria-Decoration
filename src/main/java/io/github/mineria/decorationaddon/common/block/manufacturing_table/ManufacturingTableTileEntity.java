package io.github.mineria.decorationaddon.common.block.manufacturing_table;

import com.mineria.mod.util.MineriaItemStackHandler;
import com.mineria.mod.util.MineriaLockableTileEntity;
import io.github.mineria.decorationaddon.common.init.MDATileEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

public class ManufacturingTableTileEntity extends MineriaLockableTileEntity {

    public ManufacturingTableTileEntity(BlockPos pos, BlockState state) {
        super(MDATileEntities.MANUFACTURING_TABLE.get(), pos, state, new MineriaItemStackHandler(11));
    }

    protected Component getDefaultName() {
        return Component.translatable("tile_entity.mineriadecoration.manufacturing_table");
    }

    protected AbstractContainerMenu createMenu(int id, Inventory pInventory) {
        return new ManufacturingTableMenu(id, pInventory, this);
    }
}
