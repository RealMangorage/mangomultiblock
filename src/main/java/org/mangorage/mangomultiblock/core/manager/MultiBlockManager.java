package org.mangorage.mangomultiblock.core.manager;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Rotation;
import org.mangorage.mangomultiblock.core.impl.IMultiBlockPattern;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class MultiBlockManager {
    private static final HashMap<ResourceLocation, MultiBlockManager> MANAGERS = new HashMap<>();

    public static MultiBlockManager getOrCreate(String modID, String managerID) {
        return MANAGERS.computeIfAbsent(new ResourceLocation(modID, managerID), MultiBlockManager::new);
    }

    private final String modID;
    private final ResourceLocation managerID;
    private final Map<ResourceLocation, RegisteredMultiBlockPattern> MULTIBLOCKS = new HashMap<>();

    private MultiBlockManager(ResourceLocation ID) {
        this.modID = ID.getNamespace();
        this.managerID = ID;
    }

    public ResourceLocation getID() {
        return managerID;
    }

    public <E extends IMultiBlockPattern> E register(ResourceLocation ID, E blockPattern) {
        if (MULTIBLOCKS.containsKey(ID)) throw new IllegalStateException("Already registered a Multiblock with ID: %s to Manager %s".formatted(ID, managerID));
        var rmbp = new RegisteredMultiBlockPattern(this, ID, blockPattern);
        MULTIBLOCKS.put(ID, rmbp);
        return blockPattern;
    }

    public <E extends IMultiBlockPattern> E register(String ID, E blockPattern) {
        return register(new ResourceLocation(modID, ID), blockPattern);
    }

    public @Nullable RegisteredMultiBlockPattern findStructure(Level level, BlockPos blockPos, Rotation rotation) {
        for (RegisteredMultiBlockPattern registeredMultiBlockPattern : MULTIBLOCKS.values()) {
            var result = registeredMultiBlockPattern.pattern().matches(level, blockPos, rotation);
            if (result) return registeredMultiBlockPattern;
        }
        return null;
    }

    public @Nullable RegisteredMultiBlockPattern getStructure(ResourceLocation ID) {
        return MULTIBLOCKS.get(ID);
    }

    public @Nullable RegisteredMultiBlockPattern getStructure(String ID) {
        return getStructure(new ResourceLocation(modID, ID));
    }
}
