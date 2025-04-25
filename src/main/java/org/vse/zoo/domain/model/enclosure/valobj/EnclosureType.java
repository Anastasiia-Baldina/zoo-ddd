package org.vse.zoo.domain.model.enclosure.valobj;

import org.jetbrains.annotations.NotNull;
import org.vse.zoo.application.utils.Asserts;
import org.vse.zoo.domain.shared.ValueObject;

import java.time.Instant;

public class EnclosureType implements ValueObject<EnclosureType> {
    private final String value;
    private final Instant updateTime;

    private EnclosureType(Builder b) {
        this.value = b.value;
        this.updateTime = b.updateTime;
    }

    @NotNull
    @Override
    public EnclosureType valid() {
        Asserts.notEmpty(value, "EnclosureType.value");
        return this;
    }

    @Override
    public Instant getUpdateTime() {
        return updateTime;
    }

    public String getValue() {
        return value;
    }

    public static class Builder {
        private String value;
        private Instant updateTime;

        public EnclosureType build() {
            return new EnclosureType(this);
        }

        public Builder setValue(String value) {
            this.value = value;
            return this;
        }

        public Builder setUpdateTime(Instant updateTime) {
            this.updateTime = updateTime;
            return this;
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
