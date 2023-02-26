package io.github.mineria.decorationaddon.client.init;

import io.github.mineria.decorationaddon.common.block.manufacturing_table.ManufacturingTableScreen;
import io.github.mineria.decorationaddon.common.init.MDAMenuTypes;
import net.minecraft.client.gui.screens.MenuScreens;

public class MDAScreens {
    public static void register() {
        MenuScreens.register(MDAMenuTypes.MANUFACTURING_TABLE.get(), ManufacturingTableScreen::new);
    }
}
