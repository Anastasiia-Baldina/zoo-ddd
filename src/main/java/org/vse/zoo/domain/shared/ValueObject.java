package org.vse.zoo.domain.shared;

import org.jetbrains.annotations.NotNull;

import java.time.Instant;

public interface ValueObject<T> {
    @NotNull
    T valid();

    Instant getUpdateTime();
}
