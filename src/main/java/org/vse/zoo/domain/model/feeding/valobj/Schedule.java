package org.vse.zoo.domain.model.feeding.valobj;

import org.jetbrains.annotations.NotNull;
import org.vse.zoo.application.utils.Asserts;
import org.vse.zoo.domain.shared.ValueObject;

import java.time.Instant;
import java.time.LocalTime;

public class Schedule implements ValueObject<Schedule> {
    private final Instant updateTime;
    private final int intervalDays;
    private final LocalTime mealtime;

    private Schedule(Builder b) {
        updateTime = b.updateTime;
        mealtime = b.mealtime;
        intervalDays = b.intervalDays;
    }

    @NotNull
    @Override
    public Schedule valid() {
        Asserts.notNull(updateTime, "updateTime");
        Asserts.notNull(mealtime, "mealtime");
        Asserts.ensure(intervalDays, x -> x > 0, "intervalDays > 0");
        return this;
    }

    @Override
    public Instant getUpdateTime() {
        return updateTime;
    }

    public LocalTime getMealtime() {
        return mealtime;
    }

    public int getIntervalDays() {
        return intervalDays;
    }

    public static class Builder {
        private Instant updateTime;
        private int intervalDays;
        private LocalTime mealtime;

        public Schedule build() {
            return new Schedule(this);
        }

        public Builder setUpdateTime(Instant updateTime) {
            this.updateTime = updateTime;
            return this;
        }

        public Builder setMealtime(LocalTime mealtime) {
            this.mealtime = mealtime;
            return this;
        }

        public Builder setIntervalDays(int intervalDays) {
            this.intervalDays = intervalDays;
            return this;
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return builder()
                .setIntervalDays(intervalDays)
                .setMealtime(mealtime)
                .setUpdateTime(Instant.now());
    }
}
