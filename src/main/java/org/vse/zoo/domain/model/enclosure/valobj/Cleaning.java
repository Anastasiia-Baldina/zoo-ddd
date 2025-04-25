package org.vse.zoo.domain.model.enclosure.valobj;

import org.jetbrains.annotations.NotNull;
import org.vse.zoo.application.utils.Asserts;
import org.vse.zoo.domain.shared.ValueObject;

import java.time.Instant;

public class Cleaning implements ValueObject<Cleaning> {
    private final Instant updateTime;

    public Cleaning(Instant time) {
        this.updateTime = time;
    }

    @NotNull
    @Override
    public Cleaning valid() {
        Asserts.notNull(updateTime, "Cleaning.time");
        return this;
    }

    public Instant getUpdateTime() {
        return updateTime;
    }
}
