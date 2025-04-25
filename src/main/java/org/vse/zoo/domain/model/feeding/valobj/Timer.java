package org.vse.zoo.domain.model.feeding.valobj;

import org.jetbrains.annotations.NotNull;
import org.vse.zoo.application.utils.Asserts;
import org.vse.zoo.domain.shared.ValueObject;

import java.time.Instant;
import java.time.LocalDateTime;

public class Timer implements ValueObject<Timer> {
    private final boolean enabled;
    private final LocalDateTime nextTriggerTime;
    private final Instant updateTime;

    public Timer(Builder b) {
        enabled = b.enabled;
        nextTriggerTime = b.nextTriggerTime;
        updateTime = b.updateTime;
    }

    @NotNull
    @Override
    public Timer valid() {
        Asserts.notNull(updateTime, "updateTime");
        return this;
    }

    @Override
    public Instant getUpdateTime() {
        return updateTime;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public LocalDateTime getNextTriggerTime() {
        return nextTriggerTime;
    }

    public static class Builder {
        private boolean enabled;
        private LocalDateTime nextTriggerTime;
        private Instant updateTime;

        public Timer build() {
            return new Timer(this);
        }

        public Builder setEnabled(boolean enabled) {
            this.enabled = enabled;
            return this;
        }

        public Builder setNextTriggerTime(LocalDateTime nextTriggerTime) {
            this.nextTriggerTime = nextTriggerTime;
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
                .setEnabled(enabled)
                .setNextTriggerTime(nextTriggerTime)
                .setUpdateTime(updateTime);
    }
}
