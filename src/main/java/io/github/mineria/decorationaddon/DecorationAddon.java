package io.github.mineria.decorationaddon;

import com.mojang.logging.LogUtils;
import io.github.mineria.decorationaddon.client.init.MDAScreens;
import io.github.mineria.decorationaddon.common.init.MDABlocks;
import io.github.mineria.decorationaddon.common.init.MDAItems;
import io.github.mineria.decorationaddon.common.init.MDAMenuTypes;
import io.github.mineria.decorationaddon.common.init.MDATileEntities;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(DecorationAddon.MODID)
@Mod.EventBusSubscriber(modid = DecorationAddon.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DecorationAddon {

    public static final String MODID = "mineriadecoration";
    private static final Logger LOGGER = LogUtils.getLogger();

    public DecorationAddon() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::clientSetup);

        MDABlocks.BLOCKS.register(modEventBus);
        MDABlocks.BLOCK_ITEMS.register(modEventBus);
        MDAItems.ITEMS.register(modEventBus);
        MDATileEntities.TILE_ENTITY_TYPES.register(modEventBus);
        MDAMenuTypes.MENU_TYPES.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {

    }

    private void clientSetup(final FMLClientSetupEvent event) {
        MDAScreens.register();
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }
}
