package com.github.siroshun09.sandbox4j.okopointshop.util;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EmptyStringChecker {

    public static @NotNull String isDefined(@Nullable String str, @NotNull String name) {
        if (str == null || str.isEmpty()) {
            throw new IllegalArgumentException(name + "is not defined (empty or not set?)");
        }

        return str;
    }
}
