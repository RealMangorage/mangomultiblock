package org.mangorage.mangomultiblock.item;

import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Rotation;
import org.mangorage.mangomultiblock.Multiblocks;

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
                var matches = Multiblocks.MULTIBLOCK_EXAMPLE.matchesWithResult(lvl, pContext.getClickedPos(), Rotation.NONE);
                matches.blocks().forEach(a -> {
                    if (a.getLevel() instanceof Level level) level.setBlock(a.getPos(), Blocks.BEDROCK.defaultBlockState(), Block.UPDATE_CLIENTS);
                });
            } else {
                var matches = Multiblocks.MULTIBLOCK_EXAMPLE.matches(lvl, pContext.getClickedPos(), pContext.getClickedFace().getOpposite());
                if (matches)
                    pContext.getPlayer().sendSystemMessage(Component.literal("FOUND STRUCTURE!"));
            }
        }
        return super.useOn(pContext);
    }
}
