package org.mangorage.mangomultiblock.core.impl;

import org.mangorage.mangomultiblock.core.SimpleMultiBlockPattern;

public interface IMultiBlockPatternBuilder {
    <T extends IMultiBlockPattern> T build(IPatternBuilder<T> builder);


    @SuppressWarnings("unchecked")
    default <T extends IMultiBlockPattern> T build() {
        return (T) build(SimpleMultiBlockPattern::new);
    };
}
