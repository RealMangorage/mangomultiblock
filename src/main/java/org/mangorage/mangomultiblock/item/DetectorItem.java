package org.mangorage.mangomultiblock.item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.mangorage.mangomultiblock.MultiblockExample;

public class DetectorItem extends Item {
    public DetectorItem() {
        super(new Properties());
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        var lvl = pContext.getLevel();

        if (!lvl.isClientSide) {
            var player = pContext.getPlayer();
            if (player != null && player.isHolding(Items.STICK)) {
                var matches = MultiblockExample.PATTERN.matchesWithResult(lvl, pContext.getClickedPos());
                pContext.getPlayer().sendSystemMessage(Component.literal("FOUND STRUCTURE! Blocks: " + matches.blocks().size()));
                matches.blocks().forEach(a -> {
                    if (a.getLevel() instanceof Level level) level.setBlock(a.getPos(), Blocks.BEDROCK.defaultBlockState(), Block.UPDATE_CLIENTS);
                });
            } else if (player != null && player.isHolding(Items.IRON_AXE)) {
                MultiblockExample.PATTERN.construct(lvl, pContext.getClickedPos().above(5));
            } else {
                var matches = MultiblockExample.PATTERN.matches(lvl, pContext.getClickedPos());
                if (matches) {
                    pContext.getPlayer().sendSystemMessage(Component.literal("FOUND STRUCTURE!"));
                }
            }
        }
        return super.useOn(pContext);
    }
}
