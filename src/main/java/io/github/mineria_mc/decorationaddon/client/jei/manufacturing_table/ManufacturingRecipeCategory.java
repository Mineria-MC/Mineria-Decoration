package io.github.mineria_mc.decorationaddon.client.jei.manufacturing_table;

import io.github.mineria_mc.decorationaddon.DecorationAddon;
import io.github.mineria_mc.decorationaddon.common.init.MDABlocks;
import io.github.mineria_mc.decorationaddon.common.init.MDAItems;
import io.github.mineria_mc.decorationaddon.common.recipe.ManufacturingTableRecipe;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

public class ManufacturingRecipeCategory implements IRecipeCategory<ManufacturingTableRecipe> {
    public static final RecipeType<ManufacturingTableRecipe> TYPE = new RecipeType<>(new ResourceLocation(DecorationAddon.MODID, "manufacturing_table"), ManufacturingTableRecipe.class);
    private static final ResourceLocation TEXTURE = new ResourceLocation(DecorationAddon.MODID, "textures/gui/container/manufacturing_table.png");

    private final IDrawable background;
    private final IDrawable icon;

    public ManufacturingRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 8, 12, 160, 64);
        this.icon = helper.createDrawableItemStack(new ItemStack(MDABlocks.MANUFACTURING_TABLE.get()));
    }

    @Override
    public @NotNull RecipeType<ManufacturingTableRecipe> getRecipeType() {
        return TYPE;
    }

    @Override
    public @NotNull Component getTitle() {
        return Component.translatable("recipe_category.mda.manufacturing_table");
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
    public void setRecipe(@NotNull IRecipeLayoutBuilder builder, @NotNull ManufacturingTableRecipe recipe, @NotNull IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.CATALYST, 12, 21).addIngredients(Ingredient.of(MDAItems.Tags.SAWS)).addTooltipCallback((recipeSlotView, tooltip) -> {
            tooltip.add(Component.translatable("tooltip.mda.required_saw_durability", recipe.getSawDamage()));
        });
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {
                builder.addSlot(RecipeIngredientRole.INPUT, 42 + 18 * x, 6 + 18 * y).addIngredients(recipe.getIngredients().get(y * 3 + x));
            }
        }
        builder.addSlot(RecipeIngredientRole.OUTPUT, 135, 24).addItemStack(recipe.getResultItem());
    }
}
