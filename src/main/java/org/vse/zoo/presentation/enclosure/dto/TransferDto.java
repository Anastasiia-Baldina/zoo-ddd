package org.vse.zoo.presentation.enclosure.dto;


import java.util.UUID;

public class TransferDto {
    private UUID animalUid;
    private UUID destinationEnclosureUid;

    public UUID getAnimalUid() {
        return animalUid;
    }

    public void setAnimalUid(UUID animalUid) {
        this.animalUid = animalUid;
    }

    public UUID getDestinationEnclosureUid() {
        return destinationEnclosureUid;
    }

    public void setDestinationEnclosureUid(UUID destinationEnclosureUid) {
        this.destinationEnclosureUid = destinationEnclosureUid;
    }
}
