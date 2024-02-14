package org.mangorage.mangomultiblock.core.manager;

import org.mangorage.mangomultiblock.core.impl.IMultiBlockCache;
import org.mangorage.mangomultiblock.core.impl.IMultiBlockPattern;

public class MultiBlockCache<T extends IMultiBlockPattern> implements IMultiBlockCache<T> {
    private T cachedResult;
    public MultiBlockCache() {}

    @Override
    public T get() {
        return cachedResult;
    }

    @Override
    public void updateStructure(T blockPattern) {
        this.cachedResult = blockPattern;
    }
}
