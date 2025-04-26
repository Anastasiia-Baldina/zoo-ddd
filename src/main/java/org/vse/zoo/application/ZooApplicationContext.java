package org.vse.zoo.application;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.vse.zoo.application.service.animal.AnimalService;
import org.vse.zoo.application.service.animal.impl.AnimalMovedEventHandler;
import org.vse.zoo.application.service.animal.impl.AnimalServiceImpl;
import org.vse.zoo.application.service.enclosure.AnimalTransferService;
import org.vse.zoo.application.service.enclosure.EnclosureService;
import org.vse.zoo.application.service.enclosure.impl.AnimalTransferServiceImpl;
import org.vse.zoo.application.service.feeding.FeedingOrganizationService;
import org.vse.zoo.application.service.enclosure.impl.EnclosureServiceImpl;
import org.vse.zoo.application.service.feeding.impl.FeedingOrganizationServiceImpl;
import org.vse.zoo.application.service.feeding.impl.FeedingSchedulerImpl;
import org.vse.zoo.application.event.DomainEventHandler;
import org.vse.zoo.application.event.impl.DomainEventPublisherImpl;
import org.vse.zoo.application.event.impl.FeedingTimeEventHandler;
import org.vse.zoo.application.service.statistics.ZooStatisticsService;
import org.vse.zoo.application.service.statistics.impl.ZooStatisticsServiceImpl;
import org.vse.zoo.application.transaction.TransactionManager;
import org.vse.zoo.application.transaction.impl.TransactionManagerImpl;
import org.vse.zoo.domain.event.AnimalMovedEvent;
import org.vse.zoo.domain.event.FeedingTimeEvent;
import org.vse.zoo.infrastructure.animal.AnimalRepository;
import org.vse.zoo.infrastructure.enclosure.EnclosureRepository;
import org.vse.zoo.infrastructure.feeding.FeedingRepository;
import org.vse.zoo.infrastructure.animal.InMemoryAnimalRepository;
import org.vse.zoo.infrastructure.enclosure.InMemoryEnclosureRepository;
import org.vse.zoo.infrastructure.feeding.InMemoryFeedingRepository;

@Configuration
public class ZooApplicationContext {
    @Bean
    public TransactionManager transactionManager() {
        return new TransactionManagerImpl();
    }

    @Bean
    public AnimalRepository animalRepository() {
        return new InMemoryAnimalRepository();
    }

    @Bean
    public EnclosureRepository enclosureRepository() {
        return new InMemoryEnclosureRepository();
    }

    @Bean
    public FeedingRepository feedingRepository() {
        return new InMemoryFeedingRepository();
    }

    @Bean
    public AnimalService animalService() {
        return new AnimalServiceImpl(animalRepository());
    }

    @Bean
    public EnclosureService enclosureService() {
        return new EnclosureServiceImpl(enclosureRepository());
    }

    @Bean
    public FeedingOrganizationService feedingOrganizationService() {
        return new FeedingOrganizationServiceImpl(transactionManager(), feedingRepository());
    }

    @Bean
    DomainEventPublisherImpl domainEventPublisher() {
        return new DomainEventPublisherImpl();
    }

    @Bean
    public AnimalTransferService animalTransferService() {
        return new AnimalTransferServiceImpl(
                animalRepository(),
                enclosureRepository(),
                domainEventPublisher(),
                transactionManager());
    }

    @Bean
    DomainEventHandler<AnimalMovedEvent> animalMovedEventDomainEventHandler() {
        var handler = new AnimalMovedEventHandler();
        domainEventPublisher().register(handler);

        return handler;
    }

    @Bean
    DomainEventHandler<FeedingTimeEvent> feedingTimeEventDomainEventHandler() {
        return new FeedingTimeEventHandler(animalRepository());
    }

    @Bean
    FeedingSchedulerImpl feedingScheduler() {
        return new FeedingSchedulerImpl(
                transactionManager(),
                animalService(),
                feedingOrganizationService(),
                domainEventPublisher());
    }

    @Bean
    public ZooStatisticsService zooStatisticsService() {
        return new ZooStatisticsServiceImpl(
                transactionManager(), animalRepository(), enclosureRepository());
    }
}
