package org.vse.zoo.domain.event;

import org.jetbrains.annotations.NotNull;
import org.vse.zoo.domain.model.animal.entity.Animal;
import org.vse.zoo.domain.shared.DomainEvent;

public class FeedingTimeEvent implements DomainEvent {
    @NotNull
    private final Animal animal;

    public FeedingTimeEvent(@NotNull Animal animal) {
        this.animal = animal;
    }

    @NotNull
    public Animal getAnimal() {
        return animal;
    }
}
