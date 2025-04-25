package org.vse.zoo.domain.model.animal.valobj;

import org.jetbrains.annotations.NotNull;
import org.vse.zoo.application.utils.Asserts;
import org.vse.zoo.domain.shared.ValueObject;

import java.time.Instant;

public class Requirement implements ValueObject<Requirement> {
    private final String foodType;
    private final int foodCount;
    private final Instant updateTime;

    private Requirement(Builder b) {
        foodCount = b.foodCount;
        foodType = b.foodType;
        updateTime = b.updateTime;
    }

    @NotNull
    @Override
    public Requirement valid() {
        Asserts.notEmpty(foodType, "Requirement.foodType");
        Asserts.ensure(foodCount, x -> x > 0, "Requirement.foodCount > 0");
        Asserts.notNull(updateTime, "Requirement.updateTime");
        return this;
    }

    @Override
    public Instant getUpdateTime() {
        return updateTime;
    }

    public String getFoodType() {
        return foodType;
    }

    public int getFoodCount() {
        return foodCount;
    }

    public static class Builder {
        private String foodType;
        private int foodCount;
        private Instant updateTime;

        public Requirement build() {
            return new Requirement(this);
        }

        public Builder setFoodType(String foodType) {
            this.foodType = foodType;
            return this;
        }

        public Builder setFoodCount(int foodCount) {
            this.foodCount = foodCount;
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
        return new Builder()
                .setFoodType(foodType)
                .setFoodCount(foodCount)
                .setUpdateTime(updateTime);
    }
}
