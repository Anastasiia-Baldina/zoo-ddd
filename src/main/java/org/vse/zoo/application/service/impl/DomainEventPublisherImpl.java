package org.vse.zoo.application.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vse.zoo.application.service.DomainEventHandler;
import org.vse.zoo.application.service.DomainEventObserver;
import org.vse.zoo.application.service.DomainEventPublisher;
import org.vse.zoo.domain.shared.DomainEvent;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class DomainEventPublisherImpl implements DomainEventPublisher, DomainEventObserver {
    private static final Logger log = LoggerFactory.getLogger(DomainEventPublisherImpl.class);
    private final List<DomainEventHandler<DomainEvent>> handlers = new CopyOnWriteArrayList<>();

    @Override
    @SuppressWarnings("unchecked")
    public void register(DomainEventHandler<? extends DomainEvent> domainEventHandler) {
        handlers.add((DomainEventHandler<DomainEvent>) domainEventHandler);
    }

    @Override
    public void fairEvent(DomainEvent event) {
        Class<? extends DomainEvent> eventType = event.getClass();
        for (var handler : handlers) {
            if (eventType == handler.eventType()) {
                try {
                    handler.apply(event);
                } catch (Exception e) {
                    log.error("Handle event {} error", eventType.getSimpleName(), e);
                }
            }
        }
    }
}
