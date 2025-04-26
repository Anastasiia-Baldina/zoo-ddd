package org.vse.zoo.application.event;

public interface DomainEventObserver {
    void register(DomainEventHandler<?> domainEventHandler);
}
