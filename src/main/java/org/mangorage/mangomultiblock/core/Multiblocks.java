package org.mangorage.mangomultiblock.core;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Blocks;

public class Multiblocks {
    public static final Multiblock multiblock = new Multiblock();

    static {
        multiblock.add('C', new BlockPos(0, 0, 0)); // Controller Example
        multiblock.add('#', new BlockPos(0, -1, 0));
        multiblock.add('#', new BlockPos(0, -2, 0));
        multiblock.add('B', new BlockPos(0, -3, 0));

        multiblock.define('C', b -> b.getState().is(Blocks.BEACON));
        multiblock.define('#', b -> b.getState().is(Blocks.IRON_BLOCK));
        multiblock.define('B', b -> b.getState().is(Blocks.DIAMOND_BLOCK));
    }
}
