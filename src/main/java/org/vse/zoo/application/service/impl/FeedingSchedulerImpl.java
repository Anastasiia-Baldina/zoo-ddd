package org.vse.zoo.application.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vse.zoo.application.service.AnimalService;
import org.vse.zoo.application.service.DomainEventPublisher;
import org.vse.zoo.application.service.FeedingOrganizationService;
import org.vse.zoo.application.service.FeedingScheduler;
import org.vse.zoo.application.transaction.TransactionManager;
import org.vse.zoo.domain.event.FeedingTimeEvent;
import org.vse.zoo.domain.model.animal.entity.AnimalAggregate;
import org.vse.zoo.domain.model.feeding.entity.FeedingScheduleAggregate;

import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class FeedingSchedulerImpl implements FeedingScheduler {
    private static final Logger log = LoggerFactory.getLogger(FeedingSchedulerImpl.class);
    private final TransactionManager transactionManager;
    private final AnimalService animalService;
    private final FeedingOrganizationService feedingService;
    private final DomainEventPublisher eventPublisher;
    private final ScheduledExecutorService scheduler;

    public FeedingSchedulerImpl(TransactionManager transactionManager,
                                AnimalService animalService,
                                FeedingOrganizationService feedingService,
                                DomainEventPublisher eventPublisher) {
        this.transactionManager = transactionManager;
        this.animalService = animalService;
        this.feedingService = feedingService;
        this.eventPublisher = eventPublisher;
        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleWithFixedDelay(this::checkTimers, 5, 5, TimeUnit.SECONDS);
    }

    private void checkTimers() {
        try {
            var now = LocalDateTime.now();
            var schedules = feedingService.findAll();
            for (var schedule : schedules) {
                var scheduleAggregate = new FeedingScheduleAggregate(schedule);
                if (scheduleAggregate.checkFeeding(now)) {
                    var tx = transactionManager.createWriteTx();
                    var animalUid = schedule.getRecipient().getAnimalUid();
                    var animal = animalService.findByUid(animalUid);
                    if (animal == null) {
                        log.error("Couldn't find animal {}", animalUid);
                        continue;
                    }
                    tx.begin();
                    try {
                        feedingService.updateFeedingSchedule(scheduleAggregate.buildRootEntity());
                        eventPublisher.fairEvent(new FeedingTimeEvent(animal));
                    } finally {
                        tx.end();
                    }
                }
            }
        } catch (Exception e) {
            log.error("Check feeding timers failed.", e);
        }
    }

    @Override
    public void close() {
        scheduler.shutdown();
    }
}
