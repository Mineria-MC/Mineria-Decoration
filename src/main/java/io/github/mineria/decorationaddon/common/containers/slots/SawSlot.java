package io.github.mineria.decorationaddon.common.containers.slots;

import io.github.mineria.decorationaddon.common.init.MDAItems;
import io.github.mineria.decorationaddon.common.item.saw.Saw;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

public class SawSlot extends SlotItemHandler {

    public SawSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
        super(itemHandler, index, xPosition, yPosition);
    }

    @Override
    public boolean mayPlace(@NotNull ItemStack stack) {
        return stack.getItem() instanceof Saw;
    }
}
