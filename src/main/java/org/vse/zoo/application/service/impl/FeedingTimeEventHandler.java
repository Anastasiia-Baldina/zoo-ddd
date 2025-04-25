package org.vse.zoo.application.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vse.zoo.application.service.DomainEventHandler;
import org.vse.zoo.domain.event.FeedingTimeEvent;
import org.vse.zoo.domain.model.animal.entity.AnimalAggregate;
import org.vse.zoo.domain.model.animal.service.AnimalRepository;

public class FeedingTimeEventHandler implements DomainEventHandler<FeedingTimeEvent> {
    private static final Logger log = LoggerFactory.getLogger(FeedingTimeEventHandler.class);
    private final AnimalRepository animalRepository;

    public FeedingTimeEventHandler(AnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
    }

    @Override
    public void apply(FeedingTimeEvent event) {
        var animal = new AnimalAggregate(event.getAnimal())
                .feed()
                .buildRootEntity();
        animalRepository.save(animal);
        log.info("The animal {} has been fed.", animal.getProfile().getNickname());
    }

    @Override
    public Class<FeedingTimeEvent> eventType() {
        return FeedingTimeEvent.class;
    }
}
