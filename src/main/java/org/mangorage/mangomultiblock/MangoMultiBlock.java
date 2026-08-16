package org.mangorage.mangomultiblock;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

import org.mangorage.mangomultiblock.core.Constants;
import org.mangorage.mangomultiblock.core.registry.ItemRegistry;

@Mod(Constants.MODID)
public class MangoMultiBlock {
    
    public MangoMultiBlock(IEventBus modEventBus) {
        // Registrar items
        ItemRegistry.init(modEventBus);
        
        // Setup común
        modEventBus.addListener(this::commonSetup);
    }
    
    private void commonSetup(FMLCommonSetupEvent event) {
        // Lógica de setup común aquí
    }
}