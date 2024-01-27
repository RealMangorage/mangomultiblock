package org.mangorage.mangomultiblock.core.manager;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Rotation;
import org.mangorage.mangomultiblock.core.impl.IMultiBlockPattern;

import java.util.ArrayList;

public class MultiBlockManager<T> {
    private final ArrayList<RegisteredMultiBlockPattern<T>> registeredMultiBlockPatterns = new ArrayList<>();

    public MultiBlockManager() {}

    public <E extends IMultiBlockPattern> E register(String ID, T data, E blockPattern) {
        registeredMultiBlockPatterns.add(new RegisteredMultiBlockPattern<>(ID, data, blockPattern));
        return blockPattern;
    }

    public RegisteredMultiBlockPattern<T> findStructure(Level level, BlockPos blockPos, Rotation rotation) {
        for (RegisteredMultiBlockPattern<T> registeredMultiBlockPattern : registeredMultiBlockPatterns) {
            var result = registeredMultiBlockPattern.pattern().matches(level, blockPos, rotation);
            if (result) return registeredMultiBlockPattern;
        }
        return null;
    }
}
