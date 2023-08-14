package com.hippout.lwjgltest.util;

@FunctionalInterface
public interface MapFunction<InType, OutType> {
    OutType get(InType input);
}
