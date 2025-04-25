package org.vse.zoo.presentation.statistics.dto;

import java.time.Instant;
import java.util.UUID;

public class SickAnimalDto {
    private UUID animalUid;
    private Instant updateTime;

    public UUID getAnimalUid() {
        return animalUid;
    }

    public void setAnimalUid(UUID animalUid) {
        this.animalUid = animalUid;
    }

    public Instant getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Instant updateTime) {
        this.updateTime = updateTime;
    }
}
