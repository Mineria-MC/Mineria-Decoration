package io.github.mineria.decorationaddon.common.init;

import io.github.mineria.decorationaddon.DecorationAddon;
import io.github.mineria.decorationaddon.common.recipe.ManufacturingTableRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MDARecipesSerializer {

    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZER = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, DecorationAddon.MODID);

    public static final RegistryObject<RecipeSerializer<ManufacturingTableRecipe>> MANUFACTURING_TABLE = RECIPE_SERIALIZER.register("manufacturing_table", ManufacturingTableRecipe.Serializer::new);
}
