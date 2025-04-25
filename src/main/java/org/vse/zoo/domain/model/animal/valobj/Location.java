package org.vse.zoo.domain.model.animal.valobj;

import org.jetbrains.annotations.NotNull;
import org.vse.zoo.application.utils.Asserts;
import org.vse.zoo.domain.shared.ValueObject;

import java.time.Instant;
import java.util.UUID;

public class Location implements ValueObject<Location> {
    private final UUID enclosureUid;
    private final Instant updateTime;

    private Location(Builder b) {
        this.enclosureUid = b.enclosureUid;
        this.updateTime = b.updateTime;
    }

    @NotNull
    @Override
    public Location valid() {
        Asserts.notNull(enclosureUid, "Location.enclosureUid");
        Asserts.notNull(updateTime, "updateTime");
        return this;
    }

    @Override
    public Instant getUpdateTime() {
        return updateTime;
    }

    public UUID getEnclosureUid() {
        return enclosureUid;
    }

    public static class Builder {
        private UUID enclosureUid;
        private Instant updateTime;

        public Location build() {
            return new Location(this);
        }

        public Builder setEnclosureUid(UUID enclosureUid) {
            this.enclosureUid = enclosureUid;
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
                .setEnclosureUid(enclosureUid)
                .setUpdateTime(updateTime);
    }
}
