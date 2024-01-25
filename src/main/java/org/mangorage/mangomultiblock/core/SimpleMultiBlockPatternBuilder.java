package org.mangorage.mangomultiblock.core;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import org.mangorage.mangomultiblock.core.impl.IMultiBlockPattern;
import org.mangorage.mangomultiblock.core.impl.IMultiBlockPatternBuilder;
import org.mangorage.mangomultiblock.core.impl.IPatternBuilder;
import org.mangorage.mangomultiblock.core.misc.MultiBlockOffsetPos;
import org.mangorage.mangomultiblock.core.misc.Util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.function.Supplier;

public final class SimpleMultiBlockPatternBuilder implements IMultiBlockPatternBuilder {
    private static final Joiner COMA_SEPARATOR = Joiner.on(',');
    public static SimpleMultiBlockPatternBuilder start() {
        return new SimpleMultiBlockPatternBuilder();
    }

    private final Map<Character, List<MultiBlockOffsetPos>> multiBlockOffsetPosList = Maps.newHashMap();
    private final Map<Character, Predicate<BlockInWorld>> predicateHashMap = Maps.newHashMap();
    private final Map<Character, Supplier<BlockState>> blockProvider = Maps.newHashMap();

    private SimpleMultiBlockPatternBuilder() {}

    private void ensureProperlyBuilt(List<MultiBlockOffsetPos> list) {
        HashSet<Character> characters = new HashSet<>();
        list.forEach(multiBlockOffsetPos -> {
            if (!predicateHashMap.containsKey(multiBlockOffsetPos.character()))
                characters.add(multiBlockOffsetPos.character());
        });
        if (!characters.isEmpty())
            throw new IllegalStateException("Missing the following: \"%s\" for MultiBlock Pattern as they have not been defined".formatted(COMA_SEPARATOR.join(characters)));
    }

    public SimpleMultiBlockPatternBuilder add(char character, BlockPos relativePos) {
        multiBlockOffsetPosList.computeIfAbsent(character, ArrayList::new).add(new MultiBlockOffsetPos(character, relativePos));
        return this;
    }

    public SimpleMultiBlockPatternBuilder where(char character, Predicate<BlockInWorld> worldPredicate) {
        if (!predicateHashMap.containsKey(character)) predicateHashMap.put(character, worldPredicate);
        return this;
    }

    public SimpleMultiBlockPatternBuilder block(char pSymbol, Supplier<BlockState> blockStateSupplier) {
        blockProvider.put(pSymbol, blockStateSupplier);
        return this;
    }


    public <T extends IMultiBlockPattern> T build(IPatternBuilder<T> builder) {
        var coreList = multiBlockOffsetPosList.get('*');
        if (coreList == null || coreList.size() != 1)
            throw new IllegalArgumentException("Failed to build pattern due to having more or less than one \"*\" defined! Have: %s Expected: 1".formatted(coreList != null ? coreList.size() : 0));
        List<MultiBlockOffsetPos> list = Lists.newArrayList();
        multiBlockOffsetPosList.forEach((k, v) -> list.addAll(v));
        return builder.make(list, predicateHashMap, blockProvider);
    }
}
