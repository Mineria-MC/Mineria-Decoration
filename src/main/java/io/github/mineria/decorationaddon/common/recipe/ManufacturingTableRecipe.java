package io.github.mineria.decorationaddon.common.recipe;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.*;
import io.github.mineria.decorationaddon.DecorationAddon;
import io.github.mineria.decorationaddon.common.block.manufacturing_table.ManufacturingTableMenu;
import io.github.mineria.decorationaddon.common.init.MDARecipesSerializer;
import io.github.mineria.decorationaddon.common.init.MDARecipesTypes;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Set;

public class ManufacturingTableRecipe implements Recipe<RecipeWrapper> {

    private static int MAX_WIDTH = 3;
    private static int MAX_HEIGHT = 3;

    private final ResourceLocation id;
    private final int pWidth;
    private final int pHeight;
    private final NonNullList<Ingredient> recipeItems;
    private final float sawDurability;
    private final ItemStack result;

    public ManufacturingTableRecipe(ResourceLocation id, int pWidth, int pHeight, NonNullList<Ingredient> recipeItems, float sawDurability, ItemStack result) {
        this.id = id;
        this.pWidth = pWidth;
        this.pHeight = pHeight;
        this.recipeItems = recipeItems;
        this.sawDurability = sawDurability;
        this.result = result;
    }

    @Override
    public boolean matches(RecipeWrapper inv, Level level) {
        for (int i = 0; i < (inv.getContainerSize() / (18 * 3)) - this.pWidth; i++) {
            for (int j = 0; j < (inv.getContainerSize() / (18 * 9)) - this.pHeight; j++) {
                if(this.matches(inv, i, j, true)) {
                    return true;
                }

                if(this.matches(inv, i, j, false)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean matches(RecipeWrapper inv, int width, int height, boolean mirrored) {
        for(int i = 0; i < inv.getContainerSize() / (18 * 3); i++) {
            for(int j = 0; j < inv.getContainerSize() / (18 * 9); j++) {
                int k = i - width;
                int l = j - height;
                Ingredient ingredient = Ingredient.EMPTY;

                if(k >= 0 && l >= 0 && k < this.pWidth && l < this.pHeight) {
                    if(mirrored) {
                        ingredient = this.recipeItems.get(this.pWidth - k - 1 + l * this.pWidth);
                    }else {
                        ingredient = this.recipeItems.get(k + l * this.pWidth);
                    }
                }

                if(!ingredient.test(inv.getItem(i + j * (inv.getContainerSize() / (18 * 3))))) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public ItemStack assemble(RecipeWrapper inv) {
        return this.getResultItem().copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width >= this.pWidth && height >= this.pHeight;
    }

    @Override
    public ItemStack getResultItem() {
        return this.result;
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return (RecipeSerializer)MDARecipesSerializer.MANUFACTURING_TABLE.get();
    }

    @Override
    public RecipeType<?> getType() {
        return (RecipeType)MDARecipesTypes.MANUFACTURING_TABLE_RECIPE.get();
    }

    public static Map<String, Ingredient> keyFromJson(JsonObject keyEntry) {
        Map<String, Ingredient> map = Maps.newHashMap();

        for(Map.Entry<String, JsonElement> entry : keyEntry.entrySet()) {
            if (entry.getKey().length() != 1) {
                throw new JsonSyntaxException("Invalid key entry: '" + (String)entry.getKey() + "' is an invalid symbol (must be 1 character only).");
            }

            if (" ".equals(entry.getKey())) {
                throw new JsonSyntaxException("Invalid key entry: ' ' is a reserved symbol.");
            }

            map.put(entry.getKey(), Ingredient.fromJson(entry.getValue()));
        }

        map.put(" ", Ingredient.EMPTY);
        return map;
    }

    public static String[] patternFromJson(JsonArray array) {
        String[] astring = new String[array.size()];
        if (astring.length > MAX_HEIGHT) {
            throw new JsonSyntaxException("Invalid pattern: too many rows, " + MAX_HEIGHT + " is maximum");
        } else if (astring.length == 0) {
            throw new JsonSyntaxException("Invalid pattern: empty pattern not allowed");
        } else {
            for(int i = 0; i < astring.length; ++i) {
                String s = GsonHelper.convertToString(array.get(i), "pattern[" + i + "]");
                if (s.length() > MAX_WIDTH) {
                    throw new JsonSyntaxException("Invalid pattern: too many columns, " + MAX_WIDTH + " is maximum");
                }

                if (i > 0 && astring[0].length() != s.length()) {
                    throw new JsonSyntaxException("Invalid pattern: each row must be the same width");
                }

                astring[i] = s;
            }

            return astring;
        }
    }

    public static ItemStack itemStackFromJson(JsonObject itemStack) {
        return CraftingHelper.getItemStack(itemStack, true, true);
    }

    public static String[] shrink(String... toShrink) {
        int i = Integer.MAX_VALUE;
        int j = 0;
        int k = 0;
        int l = 0;

        for (int m = 0; m < toShrink.length; m++) {
            String s = toShrink[m];
            i = Math.min(i, firstNonSpace(s));
            int n = lastNonSpace(s);
            j = Math.max(j, n);

            if(n < 0) {
                if(k == m) {
                    k++;
                }

                l++;
            }else {
                l = 0;
            }
        }

        if(toShrink.length == l) {
            return new String[0];
        }else {
            String[] astring = new String[toShrink.length - l - k];

            for (int m = 0; m < astring.length; m++) {
                astring[m] = toShrink[m + k].substring(i, j + 1);
            }

            return astring;
        }
    }

    private static int firstNonSpace(String pEntry) {
        int i;
        for(i = 0; i < pEntry.length() && pEntry.charAt(i) == ' '; ++i) {
        }

        return i;
    }

    private static int lastNonSpace(String pEntry) {
        int i;
        for(i = pEntry.length() - 1; i >= 0 && pEntry.charAt(i) == ' '; --i) {
        }

        return i;
    }

    public static NonNullList<Ingredient> dissolvePattern(String[] pattern, Map<String, Ingredient> pKeys, int pPatternWidth, int pPatternHeight) {
        NonNullList<Ingredient> list = NonNullList.withSize(pPatternWidth * pPatternHeight, Ingredient.EMPTY);
        Set<String> set = Sets.newHashSet(pKeys.keySet());
        set.remove(" ");

        for (int i = 0; i < pattern.length; i++) {
            for (int j = 0; j < pattern[0].length(); j++) {
                String s = pattern[i].substring(j, j + 1);
                Ingredient ingredient = pKeys.get(s);

                if(ingredient == null) {
                    throw new JsonSyntaxException("Pattern references symbol '" + s + "' but it's not defined in the key");
                }

                set.remove(s);
                list.set(j + pPatternWidth * i, ingredient);
            }
        }

        if(!set.isEmpty()) throw new JsonSyntaxException("Key defines symbols that aren't used in the pattern: " + set);
        return list;
    }

    public static class Serializer implements RecipeSerializer<ManufacturingTableRecipe> {
        private static final ResourceLocation RECIPE_ID = new ResourceLocation(DecorationAddon.MODID, "manufacturing_table");

        public ManufacturingTableRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            Map<String, Ingredient> map = ManufacturingTableRecipe.keyFromJson(GsonHelper.getAsJsonObject(json, "key"));
            String[] pattern = ManufacturingTableRecipe.shrink(ManufacturingTableRecipe.patternFromJson(GsonHelper.getAsJsonArray(json, "pattern")));
            int i = pattern[0].length();
            int j = pattern.length;
            NonNullList<Ingredient> list = ManufacturingTableRecipe.dissolvePattern(pattern, map, i, j);
            float durability = Float.parseFloat(GsonHelper.getAsString(json, "saw_durability"));
            ItemStack result = ManufacturingTableRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "result"));
            return new ManufacturingTableRecipe(recipeId, i, j, list, durability, result);
        }

        @Override
        public @Nullable ManufacturingTableRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            int i = pBuffer.readVarInt();
            int j = pBuffer.readVarInt();
            float saw = pBuffer.readVarInt();
            String s = pBuffer.readUtf();
            CraftingBookCategory craftingbookcategory = pBuffer.readEnum(CraftingBookCategory.class);
            NonNullList<Ingredient> nonnulllist = NonNullList.withSize(i * j, Ingredient.EMPTY);

            for(int k = 0; k < nonnulllist.size(); ++k) {
                nonnulllist.set(k, Ingredient.fromNetwork(pBuffer));
            }

            ItemStack itemstack = pBuffer.readItem();
            return new ManufacturingTableRecipe(pRecipeId, i, j, nonnulllist, saw, itemstack);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, ManufacturingTableRecipe pRecipe) {
            pBuffer.writeVarInt(pRecipe.pWidth);
            pBuffer.writeVarInt(pRecipe.pHeight);

            for(Ingredient ingredient : pRecipe.recipeItems) {
                ingredient.toNetwork(pBuffer);
            }

            pBuffer.writeItem(pRecipe.result);
        }
    }
}
