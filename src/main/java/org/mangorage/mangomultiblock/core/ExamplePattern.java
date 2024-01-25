package org.mangorage.mangomultiblock.core;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import org.mangorage.mangomultiblock.core.impl.IBetterPattern;
import org.mangorage.mangomultiblock.core.misc.MultiBlockOffsetPos;
import org.mangorage.mangomultiblock.core.misc.MultiblockMatchResult;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Example Class for Pattern's
 */
public class ExamplePattern implements IBetterPattern {
    public ExamplePattern(List<MultiBlockOffsetPos> multiBlockOffsetPosList, Map<Character, Predicate<BlockInWorld>> predicateMap, Map<Character, Supplier<BlockState>> blockSuppliers) {
    }

    @Override
    public boolean matches(Level level, BlockPos blockPos, Rotation rotation) {
        return false;
    }

    @Override
    public MultiblockMatchResult matchesWithResult(Level level, BlockPos blockPos, Rotation rotation) {
        return null;
    }

    @Override
    public void construct(Level level, BlockPos blockPos) {

    }
}
