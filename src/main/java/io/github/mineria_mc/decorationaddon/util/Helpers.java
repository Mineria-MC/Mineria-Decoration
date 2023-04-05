package io.github.mineria_mc.decorationaddon.util;

import net.minecraft.network.chat.Component;

public class Helpers {

    public static Component translatable(String prefix, String suffix, String modid) {
        return Component.translatable(prefix + "." + modid + "." + suffix);
    }
}
