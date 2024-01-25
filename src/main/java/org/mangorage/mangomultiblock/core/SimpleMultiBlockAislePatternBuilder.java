package org.mangorage.mangomultiblock.core;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.mangorage.mangomultiblock.core.impl.IMultiBlockPattern;
import org.mangorage.mangomultiblock.core.impl.IMultiBlockPatternBuilder;
import org.mangorage.mangomultiblock.core.impl.IPatternBuilder;
import org.mangorage.mangomultiblock.core.misc.MultiBlockOffsetPos;
import org.mangorage.mangomultiblock.core.misc.Util;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.function.Supplier;

public final class SimpleMultiBlockAislePatternBuilder implements IMultiBlockPatternBuilder {
    public static SimpleMultiBlockAislePatternBuilder start() {
        return new SimpleMultiBlockAislePatternBuilder();
    }

    private final List<String[]> pattern = Lists.newArrayList();
    private final Map<Character, Predicate<BlockInWorld>> lookup = Maps.newHashMap();
    private final Map<Character, Supplier<BlockState>> blockProvider = Maps.newHashMap();
    private int height;
    private int width;

    private SimpleMultiBlockAislePatternBuilder() {
    }

    public SimpleMultiBlockAislePatternBuilder aisle(String... pAisle) {
        if (!ArrayUtils.isEmpty(pAisle) && !StringUtils.isEmpty(pAisle[0])) {
            if (this.pattern.isEmpty()) {
                this.height = pAisle.length;
                this.width = pAisle[0].length();
            }

            if (pAisle.length != this.height) {
                throw new IllegalArgumentException("Expected aisle with height of " + this.height + ", but was given one with a height of " + pAisle.length + ")");
            } else {
                for (String s : pAisle) {
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

    public SimpleMultiBlockAislePatternBuilder where(char pSymbol, Predicate<BlockInWorld> pBlockMatcher) {
        this.lookup.put(pSymbol, pBlockMatcher);
        return this;
    }

    public SimpleMultiBlockAislePatternBuilder block(char pSymbol, Supplier<BlockState> blockStateSupplier) {
        blockProvider.put(pSymbol, blockStateSupplier);
        return this;
    }

    @Override
    public <T extends IMultiBlockPattern> T build(IPatternBuilder<T> builder) {
        var data = Util.parseBlockPattern(pattern, lookup.keySet());
        List<MultiBlockOffsetPos> list = Lists.newArrayList();
        data.forEach((k, v) -> list.addAll(v));
        return builder.make(list, lookup, blockProvider);
    }
}
