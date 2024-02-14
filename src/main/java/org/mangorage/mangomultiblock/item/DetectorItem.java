package org.mangorage.mangomultiblock.item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Rotation;
import org.mangorage.mangomultiblock.MultiBlockExample;
import org.mangorage.mangomultiblock.core.manager.MultiBlockManager;

public class DetectorItem extends Item {
    public DetectorItem() {
        super(new Properties());
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        var lvl = pContext.getLevel();

        if (!lvl.isClientSide) {
            var player = pContext.getPlayer();
            if (player == null) return super.useOn(pContext);

            var server = lvl.getServer();
            var isOP = false;
            if (server != null)
                isOP = lvl.getServer().getPlayerList().isOp(player.getGameProfile());


           if (player.isHolding(Items.IRON_AXE) && isOP) {
                MultiBlockExample.PATTERN.construct(lvl, pContext.getClickedPos().above(5));
            } else {
                var matches = MultiBlockManager.findAnyStructure(lvl, pContext.getClickedPos(), Rotation.NONE);
                if (matches != null) {
                    var result = matches.pattern().matchesWithResult(lvl, pContext.getClickedPos());
                    if (result != null) {
                        player.sendSystemMessage(Component.literal("Found Structure %s with manager %s".formatted(matches.ID(), matches.manager().getID())));
                    } else {
                        player.sendSystemMessage(Component.literal("No Structure Found!"));
                    }
                } else {
                    player.sendSystemMessage(Component.literal("No Structure Found!"));
                }
            }
        }
        return super.useOn(pContext);
    }
}
