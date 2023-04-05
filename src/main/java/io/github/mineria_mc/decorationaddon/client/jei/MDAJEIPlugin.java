package io.github.mineria_mc.decorationaddon.client.jei;

import io.github.mineria_mc.decorationaddon.DecorationAddon;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import net.minecraft.resources.ResourceLocation;

@JeiPlugin
public class MDAJEIPlugin implements IModPlugin {

    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(DecorationAddon.MODID, "mineriadecoration_jei");
    }
}
