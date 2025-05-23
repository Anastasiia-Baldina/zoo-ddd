package org.vse.zoo.application.event;

import org.vse.zoo.domain.shared.DomainEvent;

public interface DomainEventHandler<T extends DomainEvent> {
    void apply(T event);

    Class<T> eventType();
}
