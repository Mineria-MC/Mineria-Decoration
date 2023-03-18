package io.github.mineria.decorationaddon.common.item.saw;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;

public class Saw extends Item {
    public Saw(Tier tier) {
        super(new Properties().durability(tier.getUses()));
    }

    public Saw() {
        super(new Properties().stacksTo(1));
    }
}
