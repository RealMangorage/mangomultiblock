package org.mangorage.mangomultiblock.core.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

import org.mangorage.mangomultiblock.core.Constants;
import org.mangorage.mangomultiblock.item.DetectorItem;

public class ItemRegistry {
    
    private static final DeferredRegister<Item> ITEMS = 
        DeferredRegister.create(Registries.ITEM, Constants.MODID);
    
  
    public static final DeferredHolder<Item, DetectorItem> EXAMPLE_ITEM = 
        ITEMS.register("example_item", DetectorItem::new);


    public static void init(IEventBus modEventBus) { 
        ITEMS.register(modEventBus);
    }
}