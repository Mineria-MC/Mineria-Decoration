package io.github.mineria_mc.decorationaddon.common.init;

import io.github.mineria_mc.decorationaddon.DecorationAddon;
import io.github.mineria_mc.decorationaddon.common.block.manufacturing_table.ManufacturingTableMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MDAMenuTypes {

    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(ForgeRegistries.MENU_TYPES, DecorationAddon.MODID);
    public static final RegistryObject<MenuType<ManufacturingTableMenu>> MANUFACTURING_TABLE = MENU_TYPES.register("manufacturing_table", () -> IForgeMenuType.create(ManufacturingTableMenu::create));
}
