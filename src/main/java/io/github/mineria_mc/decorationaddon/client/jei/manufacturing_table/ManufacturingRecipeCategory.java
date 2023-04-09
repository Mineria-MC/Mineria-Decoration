package io.github.mineria_mc.decorationaddon.client.jei.manufacturing_table;

import io.github.mineria_mc.decorationaddon.DecorationAddon;
import io.github.mineria_mc.decorationaddon.common.init.MDABlocks;
import io.github.mineria_mc.decorationaddon.common.recipe.ManufacturingTableRecipe;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class ManufacturingRecipeCategory implements IRecipeCategory<ManufacturingTableRecipe> {
    public static final RecipeType<ManufacturingTableRecipe> TYPE = new RecipeType<>(new ResourceLocation(DecorationAddon.MODID, "manufacturing_table"), ManufacturingTableRecipe.class);
    private static final ResourceLocation TEXTURE = new ResourceLocation(DecorationAddon.MODID, "textures/container/manufacturing_table.png");
    private final IDrawable background;
    private final IDrawable icon;
    public ManufacturingRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 8, 4, 159, 62);
        this.icon = helper.createDrawableItemStack(new ItemStack(MDABlocks.MANUFACTURING_TABLE.get()));
    }

    @Override
    public RecipeType<ManufacturingTableRecipe> getRecipeType() {
        return TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("recipe_category.mda.manufacturing_table");
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, ManufacturingTableRecipe recipe, IFocusGroup focuses) {

    }
}
