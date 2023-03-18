package io.github.mineria.decorationaddon.common.init;

import com.mineria.mod.common.items.MineriaItem;
import io.github.mineria.decorationaddon.DecorationAddon;
import io.github.mineria.decorationaddon.common.item.saw.*;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MDAItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, DecorationAddon.MODID);

    public static final RegistryObject<Item> IRON_SAW = ITEMS.register("iron_saw", () -> new Saw(Tiers.IRON));
    public static final RegistryObject<Item> GOLD_SAW = ITEMS.register("gold_saw", () -> new Saw(Tiers.GOLD));
    public static final RegistryObject<Item> DIAMOND_SAW = ITEMS.register("diamond_saw", () -> new Saw(Tiers.DIAMOND));
    public static final RegistryObject<Item> NETHERITE_SAW = ITEMS.register("netherite_saw", () -> new Saw(Tiers.NETHERITE));
    public static final RegistryObject<Item> TITANE_SAW = ITEMS.register("titane_saw", () -> new Saw(MineriaItem.ItemTier.TITANE));
    public static final RegistryObject<Item> LONSDALEITE_SAW = ITEMS.register("lonsdaleite_saw", () -> new Saw(MineriaItem.ItemTier.LONSDALEITE));

    public static final class Tags {
        public static final TagKey<Item> SAWS = MDAItems.ITEMS.createTagKey("saws");
    }
}
