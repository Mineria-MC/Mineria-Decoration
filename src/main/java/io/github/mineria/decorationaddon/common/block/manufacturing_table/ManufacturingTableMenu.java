package io.github.mineria.decorationaddon.common.block.manufacturing_table;

import com.mineria.mod.common.containers.MineriaMenu;
import io.github.mineria.decorationaddon.common.init.MDAMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.crafting.RecipeType;
import org.jetbrains.annotations.Nullable;

public class ManufacturingTableMenu extends MineriaMenu<ManufacturingTableTileEntity> {

    public ManufacturingTableMenu(int id, Inventory pInventory, ManufacturingTableTileEntity tileEntity) {
        super(MDAMenuTypes.MANUFACTURING_TABLE.get(), id, tileEntity);
        this.createPlayerInventorySlots(pInventory, 8, 84);
    }

    public static ManufacturingTableMenu create(int id, Inventory pInventory, FriendlyByteBuf buf) {
        return new ManufacturingTableMenu(id, pInventory, getTileEntity(ManufacturingTableTileEntity.class, pInventory, buf));
    }

    @Override
    protected void createInventorySlots(ManufacturingTableTileEntity manufacturingTableTileEntity) {

    }

    @Nullable
    @Override
    protected RecipeType<?> getRecipeType() {
        return null;
    }
}
