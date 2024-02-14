package org.mangorage.mangomultiblock;

import com.mojang.logging.LogUtils;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.mangorage.mangomultiblock.core.Constants;
import org.mangorage.mangomultiblock.core.registry.ItemRegistry;
import org.slf4j.Logger;

@Mod(Constants.MODID)
public class MangoMultiBlock {

    private static final Logger LOGGER = LogUtils.getLogger();

    public MangoMultiBlock() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ItemRegistry.init(modEventBus);
        MultiBlockExample.init();
    }
}
