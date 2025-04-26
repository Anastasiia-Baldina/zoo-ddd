package org.vse.zoo.domain.event;

import org.jetbrains.annotations.NotNull;
import org.vse.zoo.domain.model.animal.entity.Animal;
import org.vse.zoo.domain.model.enclosure.entity.Enclosure;
import org.vse.zoo.domain.shared.DomainEvent;

public class AnimalMovedEvent implements DomainEvent {
    @NotNull
    private final Animal animal;
    @NotNull
    private final Enclosure sourceEnclosure;
    @NotNull
    private final Enclosure destinationEnclosure;

    public AnimalMovedEvent(@NotNull Animal animal,
                            @NotNull Enclosure sourceEnclosure,
                            @NotNull Enclosure destinationEnclosure) {
        this.animal = animal;
        this.sourceEnclosure = sourceEnclosure;
        this.destinationEnclosure = destinationEnclosure;
    }

    @NotNull
    public Animal getAnimal() {
        return animal;
    }

    @NotNull
    public Enclosure getSourceEnclosure() {
        return sourceEnclosure;
    }

    @NotNull
    public Enclosure getDestinationEnclosure() {
        return destinationEnclosure;
    }
}
