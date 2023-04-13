package io.github.mineria_mc.decorationaddon.common.init;

import io.github.mineria_mc.decorationaddon.DecorationAddon;
import io.github.mineria_mc.decorationaddon.common.block.framework.FrameworkBlockEntity;
import io.github.mineria_mc.decorationaddon.common.block.manufacturing_table.ManufacturingTableTileEntity;
import io.github.mineria_mc.decorationaddon.common.block.tool_stand.ToolStandBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BlockEntityType.Builder;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MDATileEntities {

    public static final DeferredRegister<BlockEntityType<?>> TILE_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, DecorationAddon.MODID);

    public static final RegistryObject<BlockEntityType<ManufacturingTableTileEntity>> MANUFACTURING_TABLE = TILE_ENTITY_TYPES.register("manufacturing_table", () -> Builder.of(ManufacturingTableTileEntity::new, MDABlocks.MANUFACTURING_TABLE.get()).build(null));
    public static final RegistryObject<BlockEntityType<ToolStandBlockEntity>> TOOL_STAND = TILE_ENTITY_TYPES.register("tool_stand", () -> Builder.of(ToolStandBlockEntity::new, MDABlocks.HORIZONTAL_TOOL_STAND.get(), MDABlocks.VERTICAL_TOOL_STAND.get()).build(null));
    public static final RegistryObject<BlockEntityType<FrameworkBlockEntity>> FRAMEWORK = TILE_ENTITY_TYPES.register("framework", () -> Builder.of(FrameworkBlockEntity::new, MDABlocks.FRAMEWORK.get()).build(null));
}
