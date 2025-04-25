package org.vse.zoo.application.service.impl;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.vse.zoo.application.exception.BusinessLogicException;
import org.vse.zoo.application.service.FeedingOrganizationService;
import org.vse.zoo.application.transaction.TransactionManager;
import org.vse.zoo.domain.model.feeding.entity.FeedingScheduleAggregate;
import org.vse.zoo.domain.model.feeding.entity.FeedingScheduleEntity;
import org.vse.zoo.domain.model.feeding.service.FeedingRepository;

import java.util.List;
import java.util.UUID;

public class FeedingOrganizationServiceImpl implements FeedingOrganizationService {
    private final TransactionManager transactionManager;
    private final FeedingRepository feedingRepository;

    public FeedingOrganizationServiceImpl(TransactionManager transactionManager,
                                          FeedingRepository feedingRepository) {
        this.transactionManager = transactionManager;
        this.feedingRepository = feedingRepository;
    }

    @Override
    @NotNull
    public List<FeedingScheduleEntity> findAll() {
        var tx = transactionManager.createReadTx();
        tx.begin();
        try {
            return List.copyOf(feedingRepository.fetchAll());
        } finally {
            tx.end();
        }
    }

    @Nullable
    @Override
    public FeedingScheduleEntity findByUid(@NotNull UUID feedingScheduleUid) {
        return feedingRepository.findByUid(feedingScheduleUid);
    }

    @Override
    public void addFeedingSchedule(@NotNull FeedingScheduleEntity entity) {
        if (feedingRepository.findByUid(entity.getUid()) != null) {
            throw new BusinessLogicException(
                    "Расписание кормления с идентификатором %s уже присутствует"
                            .formatted(entity.getUid()));
        }
        feedingRepository.save(entity);
    }

    @Override
    public void updateFeedingSchedule(@NotNull FeedingScheduleEntity entity) {
        if (feedingRepository.findByUid(entity.getUid()) == null) {
            throw new BusinessLogicException(
                    "Расписание кормления с идентификатором %s не найдено"
                            .formatted(entity.getUid()));
        }
        feedingRepository.save(entity);
    }

    @Override
    public void deleteFeedingSchedule(@NotNull UUID feedingScheduleUid) {
        if (feedingRepository.findByUid(feedingScheduleUid) == null) {
            throw new BusinessLogicException(
                    "Расписание кормления с идентификатором %s не найдено"
                            .formatted(feedingScheduleUid));
        }
        feedingRepository.delete(feedingScheduleUid);
    }

    @Override
    public void disableFeedingSchedule(@NotNull UUID feedingScheduleUid) {
        var entity = feedingRepository.findByUid(feedingScheduleUid);
        if (entity == null) {
            throw new BusinessLogicException(
                    "Расписание кормления с идентификатором %s не найдено"
                            .formatted(feedingScheduleUid));
        }
        entity = new FeedingScheduleAggregate(entity)
                .cancelFeeding()
                .buildRootEntity();
        feedingRepository.save(entity);
    }

    @Override
    public void enableFeedingSchedule(@NotNull UUID feedingScheduleUid) {
        var entity = feedingRepository.findByUid(feedingScheduleUid);
        if (entity == null) {
            throw new BusinessLogicException(
                    "Расписание кормления с идентификатором %s не найдено"
                            .formatted(feedingScheduleUid));
        }
        entity = new FeedingScheduleAggregate(entity)
                .enableFeeding()
                .buildRootEntity();
        feedingRepository.save(entity);
    }

    @Override
    public void removeAnimalFromAllowance(@NotNull UUID animalUid) {
        for (var entity : feedingRepository.fetchAll()) {
            if (animalUid.equals(entity.getRecipient().getAnimalUid())) {
                feedingRepository.delete(entity.getUid());
            }
        }
    }
}
