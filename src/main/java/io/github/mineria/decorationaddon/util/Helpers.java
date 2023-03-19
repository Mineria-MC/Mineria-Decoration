package io.github.mineria.decorationaddon.util;

import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class Helpers {

    public static Component translatable(String prefix, String suffix, String modid) {
        return Component.translatable(prefix + "." + modid + "." + suffix);
    }
}
