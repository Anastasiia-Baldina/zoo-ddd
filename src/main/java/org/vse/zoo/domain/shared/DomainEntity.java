package org.vse.zoo.domain.shared;

import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public interface DomainEntity {
    @NotNull
    UUID getUid();
}
