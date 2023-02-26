package io.github.mineria.decorationaddon.common.block.manufacturing_table;

import com.mineria.mod.common.containers.MineriaMenu;
import com.mineria.mod.common.containers.slots.OutputSlot;
import com.mineria.mod.util.MineriaItemStackHandler;
import com.mineria.mod.util.MineriaUtils;
import io.github.mineria.decorationaddon.common.containers.slots.SawSlot;
import io.github.mineria.decorationaddon.common.init.MDAMenuTypes;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.ResultContainer;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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

    /*protected void slotChangedCraftingGrid(Level level, Player player, MineriaItemStackHandler handler) {
        if (!level.isClientSide) {
            ServerPlayer serverPlayer = (ServerPlayer) player;
            ItemStack result = ItemStack.EMPTY;
            CraftingContainerWrapper craftingContainer = new CraftingContainerWrapper(this, handler);
            Optional<CraftingRecipe> recipeOpt = level.getServer().getRecipeManager().getRecipeFor(RecipeType.CRAFTING, craftingContainer, level);
            if (recipeOpt.isPresent()) {
                ItemStack assembled = recipeOpt.get().assemble(craftingContainer);
                if (assembled.isItemEnabled(level.enabledFeatures())) {
                    result = assembled;
                }
            }

            handler.setStackInSlot(10, result);
            this.setRemoteSlot(0, result);
            serverPlayer.connection.send(new ClientboundContainerSetSlotPacket(this.containerId, this.incrementStateId(), 0, result));
        }
    }*/

    @NotNull
    @Override
    public ItemStack quickMoveStack(@NotNull Player player, int index) {
        ItemStack stack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);

        if(slot != null && slot.hasItem()) {
            ItemStack itemStack = slot.getItem();
            stack = itemStack.copy();

            if (index < 10) {
                if (!this.moveItemStackTo(itemStack, 10, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
                slot.onQuickCraft(itemStack, stack);
            } else if (!this.moveItemStackTo(itemStack, 0, 10, false)) {
                if (index < 10 + 27) {
                    if (!this.moveItemStackTo(itemStack, 10 + 27, this.slots.size(), true)) {
                        return ItemStack.EMPTY;
                    }
                } else {
                    if (!this.moveItemStackTo(itemStack, 10, 10 + 27, false)) {
                        return ItemStack.EMPTY;
                    }
                }
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
