package org.mangorage.mangomultiblock.core;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.level.block.state.pattern.BlockPattern;
import net.minecraft.world.level.block.state.pattern.BlockPatternBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Predicate;

public class Multiblock {

    private final List<RelativePos> relativePosList = new ArrayList<>();
    private final HashMap<Character, Predicate<BlockInWorld>> predicateHashMap = new HashMap<>();

    public void define(char character, Predicate<BlockInWorld> worldPredicate) {
        if (predicateHashMap.containsKey(character)) return;
        predicateHashMap.put(character, worldPredicate);
    }

    public void add(char character, BlockPos relativePos) {
        relativePosList.add(new RelativePos(character, relativePos));
    }

    public boolean matches(Level level, BlockPos blockPos) {
        for (RelativePos relativePos : relativePosList) {
            char character = relativePos.character();
            Predicate<BlockInWorld> predicate = predicateHashMap.get(character);
            if (predicate == null) return false;
            if (!predicate.test(new BlockInWorld(level, blockPos.offset(relativePos.relativePos()), false))) return false;
        }
        return true;
    }
}
