package org.mangorage.mangomultiblock;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Blocks;
import org.mangorage.mangomultiblock.core.IMultiBlockPattern;
import org.mangorage.mangomultiblock.core.SimpleMultiBlockPattern;

public class Multiblocks {
    public static final IMultiBlockPattern MULTIBLOCK_EXAMPLE =
            SimpleMultiBlockPattern.Builder.start()
                    .add('C', new BlockPos(0, 0, 0))
                    .add('#', new BlockPos(0, -1, 0))
                    .add('#', new BlockPos(1, -1, 0))
                    .add('#', new BlockPos(-1, -1, 0))
                    .add('#', new BlockPos(0, -1, 1))
                    .add('#', new BlockPos(0, -1, -1))
                    .add('#', new BlockPos(1, -1, 1))
                    .add('#', new BlockPos(-1, -1, -1))
                    .add('#', new BlockPos(-1, -1, 1))
                    .add('#', new BlockPos(1, -1, -1))
                    .add('#', new BlockPos(0, -2, 0))
                    .add('B', new BlockPos(0, -3, 0))
                    .add('B', new BlockPos(0, 5, 5))
                    .where('C', b -> b.getState().is(Blocks.BEACON))
                    .where('#', b -> b.getState().is(Blocks.IRON_BLOCK))
                    .where('B', b -> b.getState().is(Blocks.DIAMOND_BLOCK))
                    .build();
}
