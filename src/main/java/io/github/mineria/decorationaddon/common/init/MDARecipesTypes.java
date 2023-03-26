package io.github.mineria.decorationaddon.common.init;

import io.github.mineria.decorationaddon.DecorationAddon;
import io.github.mineria.decorationaddon.common.recipe.ManufacturingTableRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class MDARecipesTypes {

    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPE = DeferredRegister.create(Registries.RECIPE_TYPE, DecorationAddon.MODID);

    public static final RegistryObject<RecipeType<ManufacturingTableRecipe>> MANUFACTURING_TABLE_RECIPE = registerRecipeType("manufacturing_table");

    public static <V extends Recipe<?>> RegistryObject<RecipeType<V>> registerRecipeType(String name) {
        return RECIPE_TYPE.register(name, () -> {
            return new RecipeType<V>() {
                public String toString() {
                    return (new ResourceLocation(DecorationAddon.MODID, name)).toString();
                }
            };
        });
    }
}
