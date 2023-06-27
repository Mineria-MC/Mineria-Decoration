package io.github.mineria_mc.decorationaddon.client.jei;

import io.github.mineria_mc.decorationaddon.DecorationAddon;
import io.github.mineria_mc.decorationaddon.client.jei.manufacturing_table.ManufacturingRecipeCategory;
import io.github.mineria_mc.decorationaddon.client.jei.manufacturing_table.ManufacturingTableCraftingRecipeCategory;
import io.github.mineria_mc.decorationaddon.common.block.manufacturing_table.ManufacturingTableMenu;
import io.github.mineria_mc.decorationaddon.common.block.manufacturing_table.ManufacturingTableScreen;
import io.github.mineria_mc.decorationaddon.common.init.MDAMenuTypes;
import io.github.mineria_mc.decorationaddon.common.init.MDARecipesTypes;
import io.github.mineria_mc.mineria.util.MineriaUtils;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.gui.handlers.IGuiClickableArea;
import mezz.jei.api.gui.handlers.IGuiContainerHandler;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusFactory;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.IRecipeTransferRegistration;
import mezz.jei.api.runtime.IRecipesGui;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeType;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;

@JeiPlugin
public class MDAJEIPlugin implements IModPlugin {
    @Override
    public @NotNull ResourceLocation getPluginUid() {
        return new ResourceLocation(DecorationAddon.MODID, "mineriadecoration_jei");
    }

    @Override
    public void registerCategories(@NotNull IRecipeCategoryRegistration registration) {
        IGuiHelper guiHelper = registration.getJeiHelpers().getGuiHelper();
        registration.addRecipeCategories(new ManufacturingRecipeCategory(guiHelper), new ManufacturingTableCraftingRecipeCategory(guiHelper));
    }

    @Override
    public void registerRecipes(@NotNull IRecipeRegistration registration) {
        registration.addRecipes(ManufacturingRecipeCategory.TYPE, MineriaUtils.findRecipesByType(MDARecipesTypes.MANUFACTURING_TABLE_RECIPE.get()));
        registration.addRecipes(ManufacturingTableCraftingRecipeCategory.TYPE, MineriaUtils.findRecipesByType(RecipeType.CRAFTING));
    }

    @Override
    public void registerRecipeTransferHandlers(@NotNull IRecipeTransferRegistration registration) {
        registration.addRecipeTransferHandler(ManufacturingTableMenu.class, MDAMenuTypes.MANUFACTURING_TABLE.get(), ManufacturingRecipeCategory.TYPE, 1, 9, 11, 36);
        registration.addRecipeTransferHandler(ManufacturingTableMenu.class, MDAMenuTypes.MANUFACTURING_TABLE.get(), ManufacturingTableCraftingRecipeCategory.TYPE, 1, 9, 11, 36);
    }

    @Override
    public void registerGuiHandlers(@NotNull IGuiHandlerRegistration registration) {
        registration.addGuiContainerHandler(ManufacturingTableScreen.class, new IGuiContainerHandler<>() {
            @Override
            public @NotNull Collection<IGuiClickableArea> getGuiClickableAreas(@NotNull ManufacturingTableScreen screen, double mouseX, double mouseY) {
                Rect2i area = new Rect2i(110, 36, 22, 15);
                IGuiClickableArea clickableArea = new IGuiClickableArea() {
                    @Override
                    public @NotNull Rect2i getArea() {
                        return area;
                    }

                    @Override
                    public void onClick(@NotNull IFocusFactory focusFactory, @NotNull IRecipesGui recipesGui) {
                        recipesGui.showTypes(List.of(screen.getMenu().getSlot(0).hasItem() ? ManufacturingRecipeCategory.TYPE : ManufacturingTableCraftingRecipeCategory.TYPE));
                    }
                };
                return List.of(clickableArea);
            }
        });
    }
}
