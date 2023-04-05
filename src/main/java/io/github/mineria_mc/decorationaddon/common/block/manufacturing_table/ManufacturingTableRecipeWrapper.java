package io.github.mineria_mc.decorationaddon.common.block.manufacturing_table;

import io.github.mineria_mc.mineria.util.MineriaItemStackHandler;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.wrapper.RecipeWrapper;

public class ManufacturingTableRecipeWrapper extends RecipeWrapper {
    private final MineriaItemStackHandler inventory;

    public ManufacturingTableRecipeWrapper(MineriaItemStackHandler inv) {
        super(new MineriaItemStackHandler(9, inv.toNonNullList().subList(1, 10).toArray(ItemStack[]::new)));
        this.inventory = inv;
    }

    public ItemStack getSaw() {
        return inventory.getStackInSlot(0);
    }
}
