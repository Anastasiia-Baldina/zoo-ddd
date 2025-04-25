package org.vse.zoo.presentation.statistics.dto;

import java.util.UUID;

public class AvailableEnclosureDto {
    private UUID enclosureUid;
    private int vacancyCount;

    public UUID getEnclosureUid() {
        return enclosureUid;
    }

    public void setEnclosureUid(UUID enclosureUid) {
        this.enclosureUid = enclosureUid;
    }

    public int getVacancyCount() {
        return vacancyCount;
    }

    public void setVacancyCount(int vacancyCount) {
        this.vacancyCount = vacancyCount;
    }
}
