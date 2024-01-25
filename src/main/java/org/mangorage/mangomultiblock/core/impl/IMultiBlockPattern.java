package org.mangorage.mangomultiblock.core.impl;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import org.mangorage.mangomultiblock.core.misc.Util;
import org.mangorage.mangomultiblock.core.misc.MultiblockMatchResult;

import java.util.function.BiPredicate;

public interface IMultiBlockPattern {
    boolean matches(Level level, BlockPos blockPos, Rotation rotation);
    MultiblockMatchResult matchesWithResult(Level level, BlockPos blockPos, Rotation rotation);
    void construct(Level level, BlockPos blockPos, BiPredicate<Character, BlockState> blockStateBiPredicate);


    default boolean matches(Level level, BlockPos blockPos, Direction direction) {
        return matches(level, blockPos, Util.DirectionToRotation(direction));
    }

    default boolean matches(Level level, BlockPos blockPos) {
        return matches(level, blockPos, Rotation.NONE);
    }

    default MultiblockMatchResult matchesWithResult(Level level, BlockPos blockPos, Direction direction) {
        return matchesWithResult(level, blockPos, Util.DirectionToRotation(direction));
    }

    default MultiblockMatchResult matchesWithResult(Level level, BlockPos blockPos) {
        return matchesWithResult(level, blockPos, Rotation.NONE);
    }

    default void construct(Level level, BlockPos blockPos) {
        construct(level, blockPos, (a, b) -> true);
    }
}
