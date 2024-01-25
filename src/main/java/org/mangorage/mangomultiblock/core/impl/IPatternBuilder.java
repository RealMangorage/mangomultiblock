package org.mangorage.mangomultiblock.core.impl;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import org.mangorage.mangomultiblock.core.impl.IMultiBlockPattern;
import org.mangorage.mangomultiblock.core.misc.MultiBlockOffsetPos;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.function.Supplier;

@FunctionalInterface
public interface IPatternBuilder<T extends IMultiBlockPattern> {
    T make(List<MultiBlockOffsetPos> multiBlockOffsetPosList, Map<Character, Predicate<BlockInWorld>> predicateMap, Map<Character, Supplier<BlockState>> blockSuppliers);
}
