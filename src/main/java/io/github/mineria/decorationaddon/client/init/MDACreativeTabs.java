package io.github.mineria.decorationaddon.client.init;

import io.github.mineria.decorationaddon.DecorationAddon;
import io.github.mineria.decorationaddon.common.init.MDABlocks;
import io.github.mineria.decorationaddon.common.init.MDAItems;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = DecorationAddon.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MDACreativeTabs {

    @Nullable
    private static CreativeModeTab TAB;

    @SubscribeEvent
    public static void register(final CreativeModeTabEvent.Register event) {
        final Supplier<ItemStack> manufactureTable = () -> new ItemStack(MDABlocks.MANUFACTURE_TABLE.get());

        TAB = event.registerCreativeModeTab(
                new ResourceLocation(DecorationAddon.MODID, "tab"),
                builder -> builder
                        .title(Component.translatable("itemGroup.mda"))
                        .icon(manufactureTable)
                        .displayItems((enabledFeatures, output, hasPermissions) -> {
                            add(output, MDABlocks.BLOCK_ITEMS.getEntries());
                            add(output, MDAItems.ITEMS.getEntries());
                        })
        );
    }

    private static void add(final CreativeModeTab.Output output, final Collection<RegistryObject<Item>> items) {
        items.stream().map(RegistryObject::get).forEach(output::accept);
    }
}
