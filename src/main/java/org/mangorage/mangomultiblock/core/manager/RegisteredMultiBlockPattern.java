package org.mangorage.mangomultiblock.core.manager;

import net.minecraft.resources.ResourceLocation;
import org.mangorage.mangomultiblock.core.impl.IMultiBlockPattern;

public record RegisteredMultiBlockPattern(MultiBlockManager manager, ResourceLocation ID, IMultiBlockPattern pattern) { }
