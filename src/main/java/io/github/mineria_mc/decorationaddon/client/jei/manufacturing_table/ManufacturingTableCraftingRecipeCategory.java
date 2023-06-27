package io.github.mineria_mc.decorationaddon.client.jei.manufacturing_table;

import io.github.mineria_mc.decorationaddon.DecorationAddon;
import io.github.mineria_mc.decorationaddon.common.init.MDABlocks;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.IRecipeSlotBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.ICraftingGridHelper;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraftforge.common.crafting.IShapedRecipe;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ManufacturingTableCraftingRecipeCategory implements IRecipeCategory<CraftingRecipe> {
    public static final RecipeType<CraftingRecipe> TYPE = new RecipeType<>(new ResourceLocation(DecorationAddon.MODID, "manufacturing_table_crafting"), CraftingRecipe.class);
    private static final ResourceLocation TEXTURE = new ResourceLocation(DecorationAddon.MODID, "textures/gui/container/manufacturing_table.png");

    private final IDrawable background;
    private final IDrawable icon;
    private final ICraftingGridHelper craftingGridHelper;

    public ManufacturingTableCraftingRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 8, 12, 160, 64);
        this.icon = helper.createDrawableItemStack(new ItemStack(MDABlocks.MANUFACTURING_TABLE.get()));
        this.craftingGridHelper = helper.createCraftingGridHelper();
    }

    @Override
    public @NotNull RecipeType<CraftingRecipe> getRecipeType() {
        return TYPE;
    }

    @Override
    public @NotNull Component getTitle() {
        return Component.translatable("recipe_category.mda.manufacturing_table.crafting");
    }

    @Override
    public @NotNull IDrawable getBackground() {
        return background;
    }

    @Override
    public @NotNull IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(@NotNull IRecipeLayoutBuilder builder, @NotNull CraftingRecipe recipe, @NotNull IFocusGroup focuses) {
        List<List<ItemStack>> inputs = recipe.getIngredients().stream().map(ingredient -> List.of(ingredient.getItems())).toList();

        int width = 0;
        int height = 0;
        if(recipe instanceof IShapedRecipe<?> shaped) {
            width = shaped.getRecipeWidth();
            height = shaped.getRecipeHeight();
        }

        if (width <= 0 || height <= 0) {
            builder.setShapeless();
        }

        List<IRecipeSlotBuilder> inputSlots = new ArrayList<>();
        for (int y = 0; y < 3; ++y) {
            for (int x = 0; x < 3; ++x) {
                IRecipeSlotBuilder slot = builder.addSlot(RecipeIngredientRole.INPUT, x * 18 + 42, y * 18 + 6);
                inputSlots.add(slot);
            }
        }

        craftingGridHelper.setInputs(inputSlots, VanillaTypes.ITEM_STACK, inputs, width, height);
        builder.addSlot(RecipeIngredientRole.OUTPUT, 135, 24).addItemStack(recipe.getResultItem());
    }
}
