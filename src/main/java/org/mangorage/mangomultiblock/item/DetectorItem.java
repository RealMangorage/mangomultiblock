package org.mangorage.mangomultiblock.item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import org.mangorage.mangomultiblock.core.Multiblocks;

public class DetectorItem extends Item {
    public DetectorItem() {
        super(new Properties());
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        var lvl = pContext.getLevel();
        if (!lvl.isClientSide) {
            var matches = Multiblocks.multiblock.matches(lvl, pContext.getClickedPos());
            if (matches)
                pContext.getPlayer().sendSystemMessage(Component.literal("FOUND STRUCTURE!"));
        }
        return super.useOn(pContext);
    }
}
