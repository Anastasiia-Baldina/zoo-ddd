package org.vse.zoo.application.utils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Map;
import java.util.function.Predicate;

public final class Asserts {
    private Asserts() {
    }

    @NotNull
    public static <T> T notNull(@Nullable T param, String paramName) {
        if (param == null) {
            throw new IllegalArgumentException("Parameter [" + paramName + "] mustn't be null");
        }
        return param;
    }

    @NotNull
    public static <K, T> Map<K, T> notEmpty(Map<K, T> param, String paramName) {
        if (notNull(param, paramName).isEmpty()) {
            throw new IllegalArgumentException("Parameter [" + paramName + "] mustn't be empty");
        }
        return param;
    }

    @NotNull
    public static <T, C extends Collection<T>> C notEmpty(C param, String paramName) {
        if (notNull(param, paramName).isEmpty()) {
            throw new IllegalArgumentException("Parameter [" + paramName + "] mustn't be empty");
        }
        return param;
    }

    public static String notEmpty(String param, String paramName) {
        if (notNull(param, paramName).isEmpty()) {
            throw new IllegalArgumentException("Parameter [" + paramName + "] mustn't be empty");
        }
        return param;
    }

    public static <T> T ensure(T param, Predicate<T> checker, String info) {
        if (!checker.test(param)) {
            throw new IllegalArgumentException("Expected [" + info + "], bit isn't");
        }
        return param;
    }
}
