package io.github.mineria.decorationaddon.common.block.manufacturing_table;

import com.mineria.mod.common.containers.MineriaMenu;
import com.mineria.mod.common.containers.slots.OutputSlot;
import com.mineria.mod.util.MineriaItemStackHandler;
import io.github.mineria.decorationaddon.common.containers.slots.SawSlot;
import io.github.mineria.decorationaddon.common.init.MDAMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ManufacturingTableMenu extends MineriaMenu<ManufacturingTableTileEntity> {

    private final ManufacturingTableTileEntity tileEntity;

    public ManufacturingTableMenu(int id, Inventory pInventory, ManufacturingTableTileEntity tileEntity) {
        super(MDAMenuTypes.MANUFACTURING_TABLE.get(), id, tileEntity);
        this.createPlayerInventorySlots(pInventory, 8, 84);
        this.tileEntity = tileEntity;
    }

    public static ManufacturingTableMenu create(int id, Inventory pInventory, FriendlyByteBuf buf) {
        return new ManufacturingTableMenu(id, pInventory, getTileEntity(ManufacturingTableTileEntity.class, pInventory, buf));
    }

    @Override
    protected void createInventorySlots(ManufacturingTableTileEntity tileEntity) {
        IItemHandler handler = tileEntity.getInventory();

        this.addSlot(new SawSlot(handler, 0, 20, 33));

        for (int y  = 0; y < 3; y++) {
            for(int x = 0; x < 3; x++) {
                this.addSlot(new SlotItemHandler(handler, (x + y * 3) + 1, 50 + x * 18, 18 + y * 18));
            }
        }

        this.addSlot(new OutputSlot(handler, 10, 143, 36));
    }

    @NotNull
    @Override
    public ItemStack quickMoveStack(@NotNull Player player, int index) {
        ItemStack stack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);

        if(slot != null && slot.hasItem()) {
            ItemStack itemStack = slot.getItem();
            stack = itemStack.copy();

            if (index == 10) {
                // Output
                if (!this.moveItemStackTo(itemStack, 11, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
                slot.onQuickCraft(itemStack, stack);
            } else if (index > 10) {
                if(!this.moveItemStackTo(itemStack, 0, 10, false)) {
                    if (index < 11 + 27) {
                        if (!this.moveItemStackTo(itemStack, 11 + 27, this.slots.size(), true)) {
                            return ItemStack.EMPTY;
                        }
                    } else {
                        if (!this.moveItemStackTo(itemStack, 11, 11 + 27, false)) {
                            return ItemStack.EMPTY;
                        }
                    }
                }
            } else if (!this.moveItemStackTo(itemStack, 11, 47, false)) {
                return ItemStack.EMPTY;
            }

            if (itemStack.getCount() == 0) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
            if (itemStack.getCount() == stack.getCount()) {
                return ItemStack.EMPTY;
            }
            slot.onTake(player, itemStack);
        }
        return stack;
    }

    @Nullable
    @Override
    protected RecipeType<?> getRecipeType() {
        return tileEntity.isCraftingTableMode() ? RecipeType.CRAFTING : null;
    }

    public static class CraftingContainerWrapper extends CraftingContainer {
        private final MineriaItemStackHandler items;

        public CraftingContainerWrapper(MineriaItemStackHandler items) {
            super(null, 3, 3);
            this.items = new MineriaItemStackHandler(9, items.toNonNullList().subList(1, 10).toArray(ItemStack[]::new));
        }

        @Override
        public int getContainerSize() {
            return items.getSlots();
        }

        @Override
        public boolean isEmpty() {
            return items.isEmpty();
        }

        @Override
        public ItemStack getItem(int index) {
            return items.getStackInSlot(index);
        }

        @Override
        public ItemStack removeItem(int index, int amount) {
            return items.extractItem(index, amount, false);
        }

        @Override
        public ItemStack removeItemNoUpdate(int index) {
            return items.extractItem(index, items.getSlotLimit(index), false);
        }

        @Override
        public void setItem(int index, ItemStack stack) {
            items.setStackInSlot(index, stack);
        }

        @Override
        public void clearContent() {
            items.clear();
        }

        @Override
        public void fillStackedContents(StackedContents contents) {
            for (ItemStack stack : items.toNonNullList()) {
                contents.accountSimpleStack(stack);
            }
        }
    }
}
