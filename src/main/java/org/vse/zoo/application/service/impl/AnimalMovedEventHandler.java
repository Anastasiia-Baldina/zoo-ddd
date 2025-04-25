package org.vse.zoo.application.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vse.zoo.application.service.DomainEventHandler;
import org.vse.zoo.domain.event.AnimalMovedEvent;

public class AnimalMovedEventHandler implements DomainEventHandler<AnimalMovedEvent> {
    private static final Logger log = LoggerFactory.getLogger(AnimalMovedEventHandler.class);

    @Override
    public void apply(AnimalMovedEvent event) {
        log.info("Event handled: AnimalMovedEvent, animal={}, source={}, destination={}",
                event.getAnimal().getUid(),
                event.getSourceEnclosure().getUid(),
                event.getDestinationEnclosure().getUid());
    }

    @Override
    public Class<AnimalMovedEvent> eventType() {
        return AnimalMovedEvent.class;
    }
}
