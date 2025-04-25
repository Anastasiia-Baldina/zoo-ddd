package org.vse.zoo.presentation.statistics.dto;

import java.util.UUID;

public class EmptyEnclosureDto {
    private UUID enclosureUid;
    private String enclosureType;

    public UUID getEnclosureUid() {
        return enclosureUid;
    }

    public void setEnclosureUid(UUID enclosureUid) {
        this.enclosureUid = enclosureUid;
    }

    public String getEnclosureType() {
        return enclosureType;
    }

    public void setEnclosureType(String enclosureType) {
        this.enclosureType = enclosureType;
    }
}
