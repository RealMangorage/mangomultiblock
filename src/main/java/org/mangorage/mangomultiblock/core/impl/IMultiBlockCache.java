package org.mangorage.mangomultiblock.core.impl;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Rotation;

import java.util.function.Supplier;

public interface IMultiBlockCache<T extends IMultiBlockPattern> extends Supplier<T> {
    void updateStructure(T blockPattern);

    default boolean isValid(Level level, BlockPos pos, Rotation rotation) {
        return get() != null && get().matches(level, pos, rotation);
    };

    default boolean isCached() {
        return get() != null;
    }
}
