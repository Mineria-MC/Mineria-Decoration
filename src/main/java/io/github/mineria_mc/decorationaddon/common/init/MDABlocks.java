package io.github.mineria_mc.decorationaddon.common.init;

import io.github.mineria_mc.decorationaddon.DecorationAddon;
import io.github.mineria_mc.decorationaddon.common.block.tool_stand.HorizontalToolStand;
import io.github.mineria_mc.decorationaddon.common.block.manufacturing_table.ManufacturingTable;
import io.github.mineria_mc.decorationaddon.common.block.tool_stand.VerticalToolStand;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nullable;
import java.util.function.Function;
import java.util.function.Supplier;

public class MDABlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, DecorationAddon.MODID);
    public static final DeferredRegister<Item> BLOCK_ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, DecorationAddon.MODID);

    public static final RegistryObject<Block> MANUFACTURING_TABLE = register("manufacturing_table", ManufacturingTable::new);

    public static final RegistryObject<Block> HORIZONTAL_TOOL_STAND = register("horizontal_tool_stand", HorizontalToolStand::new);
    public static final RegistryObject<Block> VERTICAL_TOOL_STAND = register("vertical_tool_stand", VerticalToolStand::new);

    private static RegistryObject<Block> register(String name, Supplier<Block> instance) {
        return registerBlock(name, instance, block -> new BlockItem(block, new Item.Properties()));
    }

    private static <T extends Block> RegistryObject<Block> registerBlock(String name, Supplier<T> blockSup, @Nullable Function<T, ? extends Item> blockItem) {
        RegistryObject<Block> obj = BLOCKS.register(name, blockSup);
        if(blockItem != null) BLOCK_ITEMS.register(name, () -> blockItem.apply((T)obj.get()));
        return obj;
    }

    private static BlockBehaviour.Properties properties(Material material, float hardness, float resistance, SoundType sound) {
        return BlockBehaviour.Properties.of(material).strength(hardness, resistance).sound(sound);
    }
}
