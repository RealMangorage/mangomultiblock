package org.mangorage.mangomultiblock.core;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Rotation;

public class Util {
    public static Rotation DirectionToRotation(Direction direction) {
        return switch (direction) {
            case WEST -> Rotation.CLOCKWISE_90;
            case NORTH -> Rotation.CLOCKWISE_180;
            case EAST -> Rotation.COUNTERCLOCKWISE_90;
            default -> Rotation.NONE;
        };
    }
}
