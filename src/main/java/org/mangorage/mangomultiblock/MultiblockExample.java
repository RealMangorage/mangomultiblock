package org.mangorage.mangomultiblock;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Blocks;
import org.mangorage.mangomultiblock.core.ExamplePattern;
import org.mangorage.mangomultiblock.core.SimpleMultiBlockAislePatternBuilder;
import org.mangorage.mangomultiblock.core.SimpleMultiBlockPatternBuilder;
import org.mangorage.mangomultiblock.core.impl.IBetterPattern;
import org.mangorage.mangomultiblock.core.impl.IMultiBlockPattern;

public class MultiblockExample {
    public static final IMultiBlockPattern MULTIBLOCK_EXAMPLE =
            SimpleMultiBlockPatternBuilder.start()
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
            SimpleMultiBlockAislePatternBuilder.start()
                    .aisle("0   0", "     ", "     ", "     ", "0    ")
                    .aisle("0   0", "     ", "  *  ", "     ", "0   0")
                    .aisle("0   0", "     ", "     ", "     ", "0   0")
                    .where('*', a -> a.getState().is(Blocks.DIAMOND_BLOCK))
                    .where('0', a -> a.getState().is(Blocks.BEACON))
                    .block('*', Blocks.DIAMOND_BLOCK::defaultBlockState)
                    .block('0', Blocks.BEACON::defaultBlockState)
                    .build();

    /**
     * Showcasing how you can expand the API a lot.
     * If need be.
     */
    public static final IBetterPattern MULTIBLOCK_EXAMPLE_BETTER =
            SimpleMultiBlockPatternBuilder.start()
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
                    .build(ExamplePattern::new);
}
