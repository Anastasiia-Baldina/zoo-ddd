package org.vse.zoo.domain.model.enclosure.valobj;

import org.jetbrains.annotations.NotNull;
import org.vse.zoo.application.utils.Asserts;
import org.vse.zoo.domain.shared.ValueObject;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public class Capacity implements ValueObject<Capacity> {
    private final int maxCount;
    private final List<UUID> animals;
    private final Instant updateTime;

    private Capacity(Builder b) {
        maxCount = b.maxCount;
        animals = b.animals;
        updateTime = b.updateTime;
    }

    @NotNull
    @Override
    public Capacity valid() {
        Asserts.ensure(maxCount, x -> x > 0, "Capacity.maxCount > 0");
        Asserts.notNull(animals, "Capacity.animals");
        Asserts.notNull(updateTime, "Capacity.updateTime");
        return this;
    }

    @Override
    public Instant getUpdateTime() {
        return updateTime;
    }

    public int getMaxCount() {
        return maxCount;
    }

    public List<UUID> getAnimals() {
        return animals;
    }

    public static class Builder {
        private int maxCount;
        private List<UUID> animals = List.of();
        private Instant updateTime;

        public Capacity build() {
            return new Capacity(this);
        }

        public Builder setMaxCount(int maxCount) {
            this.maxCount = maxCount;
            return this;
        }

        public Builder setAnimals(List<UUID> animals) {
            this.animals = animals;
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

    public Builder toBuilder() {
        return builder()
                .setAnimals(animals)
                .setMaxCount(maxCount);
    }
}
