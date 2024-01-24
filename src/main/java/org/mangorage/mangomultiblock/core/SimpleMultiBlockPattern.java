package org.mangorage.mangomultiblock.core;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public final class SimpleMultiBlockPattern implements IMultiBlockPattern {
    private final List<MultiBlockOffsetPos> multiBlockOffsetPosList;
    private final Map<Character, Predicate<BlockInWorld>> predicateHashMap;

    private SimpleMultiBlockPattern(List<MultiBlockOffsetPos> multiBlockOffsetPosList, Map<Character, Predicate<BlockInWorld>> predicateMap) {
        this.multiBlockOffsetPosList = multiBlockOffsetPosList;
        this.predicateHashMap = predicateMap;
    }

    public boolean matches(Level level, BlockPos blockPos, Rotation rotation) {
        for (MultiBlockOffsetPos multiBlockOffsetPos : multiBlockOffsetPosList) {
            char character = multiBlockOffsetPos.character();
            Predicate<BlockInWorld> predicate = predicateHashMap.get(character);

            BlockInWorld block = new BlockInWorld(level, blockPos.offset(multiBlockOffsetPos.offsetPos().rotate(rotation)), false);
            if (predicate == null || !predicate.test(block)) return false;
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

    public final static class Builder implements IMultiBlockPatternBuilder {
        private static final Joiner COMA_SEPARATOR = Joiner.on(',');
        public static Builder start() {
            return new Builder();
        }
        private final List<MultiBlockOffsetPos> multiBlockOffsetPosList = new ArrayList<>();
        private final Map<Character, Predicate<BlockInWorld>> predicateHashMap = new HashMap<>();

        private Builder() {}

        private void ensureProperlyBuilt() {
            HashSet<Character> characters = new HashSet<>();
            multiBlockOffsetPosList.forEach(multiBlockOffsetPos -> {
                if (!predicateHashMap.containsKey(multiBlockOffsetPos.character())) characters.add(multiBlockOffsetPos.character());
            });
            if (!characters.isEmpty()) throw new IllegalStateException("Missing the following: \"%s\" for MultiBlock Pattern as they have not been defined".formatted(COMA_SEPARATOR.join(characters)));
        }

        public Builder add(char character, BlockPos relativePos) {
            multiBlockOffsetPosList.add(new MultiBlockOffsetPos(character, relativePos));
            return this;
        }
        public Builder where(char character, Predicate<BlockInWorld> worldPredicate) {
            if (!predicateHashMap.containsKey(character)) predicateHashMap.put(character, worldPredicate);
            return this;
        }

        @Override
        public IMultiBlockPattern build() {
            ensureProperlyBuilt();
            return new SimpleMultiBlockPattern(List.copyOf(multiBlockOffsetPosList), Map.copyOf(predicateHashMap));
        }
    }

    public final static class AisleBuilder implements IMultiBlockPatternBuilder {
        private final List<String[]> pattern = Lists.newArrayList();
        private final Map<Character, Predicate<BlockInWorld>> lookup = Maps.newHashMap();
        private int height;
        private int width;

        private AisleBuilder() {
        }

        public AisleBuilder aisle(String... pAisle) {
            if (!ArrayUtils.isEmpty(pAisle) && !StringUtils.isEmpty(pAisle[0])) {
                if (this.pattern.isEmpty()) {
                    this.height = pAisle.length;
                    this.width = pAisle[0].length();
                }

                if (pAisle.length != this.height) {
                    throw new IllegalArgumentException("Expected aisle with height of " + this.height + ", but was given one with a height of " + pAisle.length + ")");
                } else {
                    for(String s : pAisle) {
                        if (s.length() != this.width) {
                            throw new IllegalArgumentException("Not all rows in the given aisle are the correct width (expected " + this.width + ", found one with " + s.length() + ")");
                        }
                    }

                    this.pattern.add(pAisle);
                    return this;
                }
            } else {
                throw new IllegalArgumentException("Empty pattern for aisle");
            }
        }

        public static AisleBuilder start() {
            return new AisleBuilder();
        }

        public AisleBuilder where(char pSymbol, Predicate<BlockInWorld> pBlockMatcher) {
            this.lookup.put(pSymbol, pBlockMatcher);
            return this;
        }

        @Override
        public IMultiBlockPattern build() {
            SimpleMultiBlockPattern.Builder simple = SimpleMultiBlockPattern.Builder.start();
            var data = Util.parseBlockPattern(pattern, lookup.keySet());

            data.forEach((c, blockOffsetPos) -> {
                blockOffsetPos.forEach(o -> simple.add(c, o.offsetPos()));
            });

            lookup.forEach(simple::where);

            return simple.build();
        }
    }
}
