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

    private final List<MultiBlockOffsetPos> multiBlockOffsetPosList = Lists.newArrayList();
    private final Map<Character, Predicate<BlockInWorld>> predicateHashMap = Maps.newHashMap();
    private final Map<Character, Supplier<BlockState>> blockProvider = Maps.newHashMap();

    private SimpleMultiBlockPatternBuilder() {}

    private void ensureProperlyBuilt() {
        HashSet<Character> characters = new HashSet<>();
        multiBlockOffsetPosList.forEach(multiBlockOffsetPos -> {
            if (!predicateHashMap.containsKey(multiBlockOffsetPos.character()))
                characters.add(multiBlockOffsetPos.character());
        });
        if (!characters.isEmpty())
            throw new IllegalStateException("Missing the following: \"%s\" for MultiBlock Pattern as they have not been defined".formatted(COMA_SEPARATOR.join(characters)));
    }

    public SimpleMultiBlockPatternBuilder add(char character, BlockPos relativePos) {
        multiBlockOffsetPosList.add(new MultiBlockOffsetPos(character, relativePos));
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
        ensureProperlyBuilt();
        return builder.make(multiBlockOffsetPosList, predicateHashMap, blockProvider);
    }
}
