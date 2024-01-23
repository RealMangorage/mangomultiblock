package org.mangorage.mangomultiblock.core;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Rotation;

public interface IIMultiBlockPattern {
    boolean matches(Level level, BlockPos blockPos, Rotation rotation);
    MultiblockMatchResult matchesWithResult(Level level, BlockPos blockPos, Rotation rotation);

    default boolean matches(Level level, BlockPos blockPos, Direction direction) {
        return matches(level, blockPos, Util.DirectionToRotation(direction));
    }

    default MultiblockMatchResult matchesWithResult(Level level, BlockPos blockPos, Direction direction) {
        return matchesWithResult(level, blockPos, Util.DirectionToRotation(direction));
    }

    default boolean matches(Level level, BlockPos blockPos) {
        return matches(level, blockPos, Rotation.NONE);
    }

    default MultiblockMatchResult matchesWithResult(Level level, BlockPos blockPos) {
        return matchesWithResult(level, blockPos, Rotation.NONE);
    }
}
