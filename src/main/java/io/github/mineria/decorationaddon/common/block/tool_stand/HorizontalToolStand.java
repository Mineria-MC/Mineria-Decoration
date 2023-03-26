package io.github.mineria.decorationaddon.common.block.tool_stand;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;

public class HorizontalToolStand extends Block {

    public HorizontalToolStand() {
        super(Properties.of(Material.WOOD).strength(0.5f, 0.3f).sound(SoundType.WOOD));
    }
}
