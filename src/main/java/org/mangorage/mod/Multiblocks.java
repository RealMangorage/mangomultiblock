package org.mangorage.mod;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Blocks;
import org.mangorage.mangomultiblock.core.IMultiBlockPattern;
import org.mangorage.mangomultiblock.core.IMultiBlockPatternBuilder;
import org.mangorage.mangomultiblock.core.SimpleMultiBlockPattern;

public class Multiblocks {
    public static final IMultiBlockPattern MULTIBLOCK_EXAMPLE =
            SimpleMultiBlockPattern.Builder.start()
                    .add('B', new BlockPos(0, 1, 1))
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
                    .where('C', b -> b.getState().is(Blocks.BEACON))
                    .where('#', b -> b.getState().is(Blocks.IRON_BLOCK))
                    .where('B', b -> b.getState().is(Blocks.DIAMOND_BLOCK))
                    .build();

    public static final IMultiBlockPattern PATTERN =
            SimpleMultiBlockPattern.AisleBuilder.start()
                    .aisle("0   0", "     ", "     ", "     ", "0    ")
                    .aisle("0   0", "     ", "  *  ", "     ", "0   0")
                    .aisle("0   0", "     ", "     ", "     ", "0   0")
                    .where('*', a -> a.getState().is(Blocks.DIAMOND_BLOCK))
                    .where('0', a -> a.getState().is(Blocks.BEACON))
                    .build();
}
