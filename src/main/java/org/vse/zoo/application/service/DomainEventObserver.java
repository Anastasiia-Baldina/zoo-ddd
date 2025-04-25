package org.vse.zoo.application.service;

public interface DomainEventObserver {
    void register(DomainEventHandler<?> domainEventHandler);
}
