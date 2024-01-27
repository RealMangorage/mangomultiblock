package org.mangorage.mangomultiblock.core.manager;

import org.mangorage.mangomultiblock.core.impl.IMultiBlockPattern;

public record RegisteredMultiBlockPattern<T>(String ID, T data, IMultiBlockPattern pattern) { }
