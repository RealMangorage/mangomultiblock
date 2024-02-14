package org.mangorage.mangomultiblock;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.StairBlock;
import org.mangorage.mangomultiblock.core.Constants;
import org.mangorage.mangomultiblock.core.SimpleMultiBlockAislePatternBuilder;
import org.mangorage.mangomultiblock.core.SimpleMultiBlockPatternBuilder;
import org.mangorage.mangomultiblock.core.impl.IMultiBlockPattern;
import org.mangorage.mangomultiblock.core.manager.MultiBlockManager;

public class MultiBlockExample {
    public static final MultiBlockManager MANAGER = MultiBlockManager.getOrCreate(Constants.MODID, "miners");

    public static final IMultiBlockPattern MULTIBLOCK_RAW_POSITION_EXAMPLE =
            SimpleMultiBlockPatternBuilder.start()
                    .add('*', new BlockPos(0, 1, 1))
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
                    .where('*', b -> b.getState().is(Blocks.DIAMOND_BLOCK))
                    .build();

    public static final IMultiBlockPattern PATTERN =
            SimpleMultiBlockAislePatternBuilder.start()
                    .aisle("2   0", "     ", "     ", "     ", "0   1")
                    .aisle("0   0", "     ", "  *  ", "     ", "0   0")
                    .aisle("0   0", "     ", "     ", "     ", "0   0")
                    .where('*', a -> a.getState().is(Blocks.DIAMOND_BLOCK))
                    .where('0', a -> a.getState().is(Blocks.BEACON))
                    .where('1', a -> a.getState().is(Blocks.STONE_STAIRS) && a.getState().getValue(StairBlock.FACING) == Direction.WEST)
                    .where('2', a -> a.getState().is(Blocks.IRON_BLOCK))
                    .block('*', Blocks.DIAMOND_BLOCK::defaultBlockState)
                    .block('0', Blocks.BEACON::defaultBlockState)
                    .block('1', () -> Blocks.STONE_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.WEST))
                    .block('2', Blocks.IRON_BLOCK::defaultBlockState)
                    .build();

    public static void init() {
        MANAGER.register("test", PATTERN);
    }
}
