package io.github.mineria.decorationaddon.common.init;

import com.mojang.datafixers.types.Type;
import io.github.mineria.decorationaddon.DecorationAddon;
import io.github.mineria.decorationaddon.common.block.manufacturing_table.ManufacturingTableTileEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BlockEntityType.Builder;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MDATileEntities {

    public static final DeferredRegister<BlockEntityType<?>> TILE_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, DecorationAddon.MODID);

    public static final RegistryObject<BlockEntityType<ManufacturingTableTileEntity>> MANUFACTURING_TABLE = TILE_ENTITY_TYPES.register("manufacturing_table", () -> Builder.of(ManufacturingTableTileEntity::new, MDABlocks.MANUFACTURING_TABLE.get()).build(null));

}
