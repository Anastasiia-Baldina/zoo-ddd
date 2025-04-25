package org.vse.zoo.domain.model.animal.valobj;

import org.jetbrains.annotations.NotNull;
import org.vse.zoo.application.utils.Asserts;
import org.vse.zoo.domain.shared.ValueObject;

import java.time.Instant;
import java.time.LocalTime;

public class Feeding implements ValueObject<Feeding> {
    private final Instant updateTime;

    private Feeding(Builder b) {
        this.updateTime = b.updateTime;
    }

    @NotNull
    @Override
    public Feeding valid() {
        Asserts.notNull(updateTime, "updateTime");
        return this;
    }

    @Override
    public Instant getUpdateTime() {
        return updateTime;
    }

    public static class Builder {
        private Instant updateTime;

        public Feeding build() {
            return new Feeding(this);
        }

        public Builder setUpdateTime(Instant updateTime) {
            this.updateTime = updateTime;
            return this;
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return builder().setUpdateTime(updateTime);
    }
}
