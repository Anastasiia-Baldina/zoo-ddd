package org.vse.zoo.domain.model.feeding.valobj;

import org.jetbrains.annotations.NotNull;
import org.vse.zoo.application.utils.Asserts;
import org.vse.zoo.domain.shared.ValueObject;

import java.time.Instant;
import java.util.UUID;

public class Recipient implements ValueObject<Recipient> {
    private final UUID animalUid;
    private final Instant updateTime;

    private Recipient(Builder b) {
        animalUid = b.animalUid;

        updateTime = b.updateTime;
    }

    @NotNull
    @Override
    public Recipient valid() {
        Asserts.notNull(animalUid, "animalUid");
        Asserts.notNull(updateTime, "updateTime");
        return this;
    }

    @Override
    public Instant getUpdateTime() {
        return updateTime;
    }

    public UUID getAnimalUid() {
        return animalUid;
    }

    public static class Builder {
        private UUID animalUid;
        private Instant updateTime;

        public Recipient build() {
            return new Recipient(this);
        }

        public Builder setAnimalUid(UUID animalUid) {
            this.animalUid = animalUid;
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
                .setAnimalUid(animalUid)
                .setUpdateTime(updateTime);
    }
}


