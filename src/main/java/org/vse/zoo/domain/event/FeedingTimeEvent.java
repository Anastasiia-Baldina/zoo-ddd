package org.vse.zoo.domain.event;

import org.jetbrains.annotations.NotNull;
import org.vse.zoo.domain.model.animal.entity.AnimalEntity;
import org.vse.zoo.domain.shared.DomainEvent;

public class FeedingTimeEvent implements DomainEvent {
    @NotNull
    private final AnimalEntity animal;

    public FeedingTimeEvent(@NotNull AnimalEntity animal) {
        this.animal = animal;
    }

    @NotNull
    public AnimalEntity getAnimal() {
        return animal;
    }
}
