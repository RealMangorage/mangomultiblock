package org.mangorage.mangomultiblock.core;

import com.google.common.base.Joiner;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public final class SimpleMultiBlockPattern implements IIMultiBlockPattern {
    private final List<MultiBlockOffsetPos> multiBlockOffsetPosList;
    private final Map<Character, Predicate<BlockInWorld>> predicateHashMap;

    private SimpleMultiBlockPattern(List<MultiBlockOffsetPos> multiBlockOffsetPosList, Map<Character, Predicate<BlockInWorld>> predicateMap) {
        this.multiBlockOffsetPosList = multiBlockOffsetPosList;
        this.predicateHashMap = predicateMap;
    }

    private SimpleMultiBlockPattern() {
        this.multiBlockOffsetPosList = new ArrayList<>();
        this.predicateHashMap = new HashMap<>();
    }

    public boolean matches(Level level, BlockPos blockPos, Rotation rotation) {
        for (MultiBlockOffsetPos multiBlockOffsetPos : multiBlockOffsetPosList) {
            char character = multiBlockOffsetPos.character();
            Predicate<BlockInWorld> predicate = predicateHashMap.get(character);
            if (predicate == null || !predicate.test(new BlockInWorld(level, blockPos.offset(multiBlockOffsetPos.offsetPos().rotate(rotation)), false))) return false;
        }
        return true;
    }

    public MultiblockMatchResult matchesWithResult(Level level, BlockPos blockPos, Rotation rotation) {
        List<BlockInWorld> result = new ArrayList<>();
        for (MultiBlockOffsetPos multiBlockOffsetPos : multiBlockOffsetPosList) {
            char character = multiBlockOffsetPos.character();
            Predicate<BlockInWorld> predicate = predicateHashMap.get(character);
            BlockInWorld block = new BlockInWorld(level, blockPos.offset(multiBlockOffsetPos.offsetPos().rotate(rotation)), false);
            if (predicate == null || !predicate.test(block)) return null;
            result.add(block);
        }

        return new MultiblockMatchResult(List.copyOf(result));
    }

    public final static class Builder {
        private static final Joiner COMA_SEPERATOR = Joiner.on(',');
        public static Builder start() { return new Builder(); }
        private final List<MultiBlockOffsetPos> multiBlockOffsetPosList = new ArrayList<>();
        private final Map<Character, Predicate<BlockInWorld>> predicateHashMap = new HashMap<>();

        private Builder() {}

        private void ensureProperlyBuilt() {
            List<Character> characters = new ArrayList<>();
            multiBlockOffsetPosList.forEach(multiBlockOffsetPos -> {
                if (!predicateHashMap.containsKey(multiBlockOffsetPos.character())) characters.add(multiBlockOffsetPos.character());
            });
            if (!characters.isEmpty()) throw new IllegalStateException("Missing the following: \"%s\" for MultiBlock Pattern as they have not been defined".formatted(COMA_SEPERATOR.join(characters)));
        }

        public Builder add(char character, BlockPos relativePos) {
            multiBlockOffsetPosList.add(new MultiBlockOffsetPos(character, relativePos));
            return this;
        }
        public Builder where(char character, Predicate<BlockInWorld> worldPredicate) {
            if (!predicateHashMap.containsKey(character)) predicateHashMap.put(character, worldPredicate);
            return this;
        }
        public IIMultiBlockPattern build() {
            ensureProperlyBuilt();
            return new SimpleMultiBlockPattern(List.copyOf(multiBlockOffsetPosList), Map.copyOf(predicateHashMap));
        }
    }
}
