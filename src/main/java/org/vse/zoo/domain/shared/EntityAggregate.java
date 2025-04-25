package org.vse.zoo.domain.shared;

import org.jetbrains.annotations.NotNull;

public interface EntityAggregate<T extends DomainEntity> {
    @NotNull
    T buildRootEntity();
}
