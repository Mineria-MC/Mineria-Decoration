package io.github.mineria.decorationaddon.common.block.manufacturing_table;

import com.mineria.mod.util.MineriaItemStackHandler;
import com.mineria.mod.util.MineriaLockableTileEntity;
import com.mineria.mod.util.MineriaUtils;
import io.github.mineria.decorationaddon.common.init.MDARecipesTypes;
import io.github.mineria.decorationaddon.common.init.MDATileEntities;
import io.github.mineria.decorationaddon.common.recipe.ManufacturingTableRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.Set;

public class ManufacturingTableTileEntity extends MineriaLockableTileEntity {

    public ManufacturingTableTileEntity(BlockPos pos, BlockState state) {
        super(MDATileEntities.MANUFACTURING_TABLE.get(), pos, state, new CustomInventory(11));
        if(this.inventory instanceof CustomInventory customInventory) {
            customInventory.setTile(this);
        }
    }

    protected Component getDefaultName() {
        return Component.translatable("tile_entity.mineriadecoration.manufacturing_table");
    }

    protected AbstractContainerMenu createMenu(int id, Inventory pInventory) {
        return new ManufacturingTableMenu(id, pInventory, this);
    }

    public void tick(Level level) {

    }

    private void contentsChanged(int slot) {
        if(level.isClientSide || !(this.inventory instanceof CustomInventory customInventory)) {
            return;
        }

        if(slot == 0) {
            contentsChanged(1);
            return;
        }

        if(slot == 10) {
            for (int i = 1; i < 10; i++) {
                ItemStack stack = customInventory.getStackInSlot(i);
                if(stack.getCount() == 1) {
                    customInventory.setStackInSlot(i, stack.getCraftingRemainingItem());
                } else {
                    ItemStack copy = stack.copy();
                    copy.shrink(1);
                    customInventory.setStackInSlot(i, copy);
                }
            }
        } else {
            ItemStack result = ItemStack.EMPTY;
            if(isCraftingTableMode()) {
                ManufacturingTableMenu.CraftingContainerWrapper craftingContainer = new ManufacturingTableMenu.CraftingContainerWrapper(this.inventory);
                Optional<CraftingRecipe> recipeOpt = level.getServer().getRecipeManager().getRecipeFor(RecipeType.CRAFTING, craftingContainer, level);
                if (recipeOpt.isPresent()) {
                    ItemStack assembled = recipeOpt.get().assemble(craftingContainer);
                    if (assembled.isItemEnabled(level.enabledFeatures())) {
                        result = assembled;
                    }
                }
            } else {
                ManufacturingTableRecipe recipe = findManufacturingTableRecipe();
                if(recipe != null) {
                    ItemStack assembled = recipe.assemble(new RecipeWrapper(this.inventory));
                    if(assembled.isItemEnabled(level.enabledFeatures())) {
                        result = assembled;
                    }
                }
            }

            customInventory.setStackInSlotNoUpdate(10, result);
        }
        setChanged();
    }

    @Nullable
    private ManufacturingTableRecipe findManufacturingTableRecipe() {
        Set<Recipe<?>> recipes = MineriaUtils.findRecipesByType(MDARecipesTypes.MANUFACTURING_TABLE_RECIPE.get(), level);
        for (Recipe<?> recipe : recipes) {
            if(recipe instanceof ManufacturingTableRecipe manufacturingTableRecipe && manufacturingTableRecipe.matches(new RecipeWrapper(this.inventory), level)) {
                return manufacturingTableRecipe;
            }
        }
        return null;
    }

    public boolean isCraftingTableMode() {
        ItemStack saw = this.inventory.getStackInSlot(0);
        return saw.isEmpty();
    }

    public static class CustomInventory extends MineriaItemStackHandler {
        @Nullable
        private ManufacturingTableTileEntity tile;

        public CustomInventory(int size, ItemStack... stacks) {
            super(size, stacks);
        }

        public void setTile(ManufacturingTableTileEntity tile) {
            this.tile = tile;
        }

        @Override
        protected void onContentsChanged(int slot) {
            if(tile != null) {
                tile.contentsChanged(slot);
            }
        }

        public void setStackInSlotNoUpdate(int slot, @NotNull ItemStack stack) {
            validateSlotIndex(slot);
            this.stacks.set(slot, stack);
        }
    }
}
