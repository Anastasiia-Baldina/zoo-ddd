package org.vse.zoo.domain.model.enclosure.valobj;

import org.jetbrains.annotations.NotNull;
import org.vse.zoo.application.utils.Asserts;
import org.vse.zoo.domain.shared.ValueObject;

import java.time.Instant;

public class Compatibility implements ValueObject<Compatibility> {
    private final String animalType;
    private final Instant updateTime;

    private Compatibility(Builder b) {
        this.animalType = b.animalType;
        this.updateTime = b.updateTime;
    }

    @NotNull
    @Override
    public Compatibility valid() {
        Asserts.notEmpty(animalType, "animalType");
        return this;
    }

    @Override
    public Instant getUpdateTime() {
        return updateTime;
    }

    public String getAnimalType() {
        return animalType;
    }

    public static class Builder {
        private String animalType;
        private Instant updateTime;

        public Compatibility build() {
            return new Compatibility(this);
        }

        public Builder setAnimalType(String animalType) {
            this.animalType = animalType;
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
