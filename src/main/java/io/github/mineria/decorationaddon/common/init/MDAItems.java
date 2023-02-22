package io.github.mineria.decorationaddon.common.init;

import io.github.mineria.decorationaddon.DecorationAddon;
import io.github.mineria.decorationaddon.common.item.saw.*;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MDAItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, DecorationAddon.MODID);

    public static final RegistryObject<Item> IRON_SAW = ITEMS.register("iron_saw", IronSaw::new);
    public static final RegistryObject<Item> GOLD_SAW = ITEMS.register("gold_saw", GoldSaw::new);
    public static final RegistryObject<Item> DIAMOND_SAW = ITEMS.register("diamond_saw", DiamondSaw::new);
    public static final RegistryObject<Item> NETHERITE_SAW = ITEMS.register("netherite_saw", NetheriteSaw::new);
    public static final RegistryObject<Item> TITANE_SAW = ITEMS.register("titane_saw", TitaneSaw::new);
    public static final RegistryObject<Item> LONSDALEITE_SAW = ITEMS.register("lonsdaleite_saw", LonsdaleiteSaw::new);
}
