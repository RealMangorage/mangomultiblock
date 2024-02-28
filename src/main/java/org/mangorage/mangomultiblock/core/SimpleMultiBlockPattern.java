package org.mangorage.mangomultiblock.core;

import net.minecraft.core.BlockPos;
import net.minecraft.server.TickTask;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import org.mangorage.mangomultiblock.core.impl.IMultiBlockPattern;
import org.mangorage.mangomultiblock.core.misc.MultiBlockOffsetPos;
import org.mangorage.mangomultiblock.core.misc.MultiblockMatchResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.function.Supplier;

public final class SimpleMultiBlockPattern implements IMultiBlockPattern {
    private final List<MultiBlockOffsetPos> multiBlockOffsetPosList;
    private final Map<Character, Predicate<BlockInWorld>> predicateHashMap;
    private final Map<Character, Supplier<BlockState>> blockProvider;

    /*
        Should make a Builder that provides the List and 2 maps.
        Any other method is invalid and dangerous...

        This is to ensure stuff is Immutable as it shouldnt be Mutable...
     */
    public SimpleMultiBlockPattern(List<MultiBlockOffsetPos> multiBlockOffsetPosList, Map<Character, Predicate<BlockInWorld>> predicateMap, Map<Character, Supplier<BlockState>> blockSuppliers) {
        this.multiBlockOffsetPosList = List.copyOf(multiBlockOffsetPosList);
        this.predicateHashMap = Map.copyOf(predicateMap);
        this.blockProvider = Map.copyOf(blockSuppliers);
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

    @Override
    public void construct(Level level, BlockPos blockPos, BiPredicate<Character, BlockState> stateBiPredicate, Rotation rotation) {
        if (level.isClientSide) return;
        if (level.getServer() == null) return;
        for (MultiBlockOffsetPos multiBlockOffsetPos : multiBlockOffsetPosList) {
            char character = multiBlockOffsetPos.character();
            Supplier<BlockState> stateSupplier = blockProvider.get(character);
            if (stateSupplier != null) {
                var pos = blockPos.offset(multiBlockOffsetPos.offsetPos().rotate(rotation));
                var state = stateSupplier.get();
                if (stateBiPredicate.test(character, state)) level.getServer().tell(new TickTask(3, () -> level.setBlock(pos, state, Block.UPDATE_ALL)));
            }
        }
    }

}
