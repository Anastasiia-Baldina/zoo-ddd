package org.vse.zoo.application.event;

import org.vse.zoo.domain.shared.DomainEvent;

public interface DomainEventPublisher {
    void fairEvent(DomainEvent event);
}
