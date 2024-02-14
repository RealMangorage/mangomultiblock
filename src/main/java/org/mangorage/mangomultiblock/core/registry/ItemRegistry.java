package org.mangorage.mangomultiblock.core.registry;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.mangorage.mangomultiblock.core.Constants;
import org.mangorage.mangomultiblock.item.DetectorItem;

public class ItemRegistry {
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Constants.MODID);
    public static final RegistryObject<Item> EXAMPLE_ITEM = ITEMS.register("example_item", DetectorItem::new);

    public static void init(IEventBus modEventBus) {
        ITEMS.register(modEventBus);
    }
}
