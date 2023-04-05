package io.github.mineria_mc.decorationaddon.client.init;

import io.github.mineria_mc.decorationaddon.common.block.manufacturing_table.ManufacturingTableScreen;
import io.github.mineria_mc.decorationaddon.common.init.MDAMenuTypes;
import net.minecraft.client.gui.screens.MenuScreens;

public class MDAScreens {
    public static void register() {
        MenuScreens.register(MDAMenuTypes.MANUFACTURING_TABLE.get(), ManufacturingTableScreen::new);
    }
}
