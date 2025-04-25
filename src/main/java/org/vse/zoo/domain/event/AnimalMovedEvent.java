package org.vse.zoo.domain.event;

import org.jetbrains.annotations.NotNull;
import org.vse.zoo.domain.model.animal.entity.AnimalEntity;
import org.vse.zoo.domain.model.enclosure.entity.EnclosureEntity;
import org.vse.zoo.domain.shared.DomainEvent;

public class AnimalMovedEvent implements DomainEvent {
    @NotNull
    private final AnimalEntity animal;
    @NotNull
    private final EnclosureEntity sourceEnclosure;
    @NotNull
    private final EnclosureEntity destinationEnclosure;

    public AnimalMovedEvent(@NotNull AnimalEntity animal,
                            @NotNull EnclosureEntity sourceEnclosure,
                            @NotNull EnclosureEntity destinationEnclosure) {
        this.animal = animal;
        this.sourceEnclosure = sourceEnclosure;
        this.destinationEnclosure = destinationEnclosure;
    }

    @NotNull
    public AnimalEntity getAnimal() {
        return animal;
    }

    @NotNull
    public EnclosureEntity getSourceEnclosure() {
        return sourceEnclosure;
    }

    @NotNull
    public EnclosureEntity getDestinationEnclosure() {
        return destinationEnclosure;
    }
}
