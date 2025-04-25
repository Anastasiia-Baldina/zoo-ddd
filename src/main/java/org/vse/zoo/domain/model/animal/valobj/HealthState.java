package org.vse.zoo.domain.model.animal.valobj;

import org.jetbrains.annotations.NotNull;
import org.vse.zoo.application.utils.Asserts;
import org.vse.zoo.domain.shared.ValueObject;

import java.time.Instant;

public class HealthState implements ValueObject<HealthState> {
    private final boolean healthy;
    private final Instant updateTime;

    private HealthState(Builder b) {
        healthy = b.healthy;
        updateTime = b.updateTime;
    }

    @NotNull
    @Override
    public HealthState valid() {
        Asserts.notNull(updateTime, "HealthState.updateTime");
        return this;
    }

    public boolean isHealthy() {
        return healthy;
    }

    public Instant getUpdateTime() {
        return updateTime;
    }

    public static class Builder {
        private boolean healthy;
        private Instant updateTime;

        public HealthState build() {
            return new HealthState(this);
        }

        public Builder setHealthy(boolean healthy) {
            this.healthy = healthy;
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
                .setHealthy(healthy)
                .setUpdateTime(updateTime);
    }
}
