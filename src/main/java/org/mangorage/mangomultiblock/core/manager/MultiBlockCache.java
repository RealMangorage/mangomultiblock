package org.mangorage.mangomultiblock.core.manager;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Rotation;
import org.mangorage.mangomultiblock.core.impl.IMultiBlockPattern;

public class MultiBlockCache<T extends IMultiBlockPattern> {
    private T cachedResult;
    public MultiBlockCache() {}


    public void updateStructure(T blockPattern) {
        this.cachedResult = blockPattern;
    }

    public boolean isValid(Level level, BlockPos pos, Rotation rotation) {
        return cachedResult != null && cachedResult.matches(level, pos, rotation);
    }

    public boolean isCached() {
        return cachedResult == null;
    }
}
