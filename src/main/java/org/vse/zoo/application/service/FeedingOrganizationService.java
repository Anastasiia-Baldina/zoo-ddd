package org.vse.zoo.application.service;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.vse.zoo.domain.model.feeding.entity.FeedingScheduleEntity;

import java.util.List;
import java.util.UUID;

public interface FeedingOrganizationService {

    @NotNull
    List<FeedingScheduleEntity> findAll();

    @Nullable
    FeedingScheduleEntity findByUid(@NotNull UUID feedingScheduleUid);

    void addFeedingSchedule(@NotNull FeedingScheduleEntity entity);

    void updateFeedingSchedule(@NotNull FeedingScheduleEntity entity);

    void deleteFeedingSchedule(@NotNull UUID feedingScheduleUid);

    void disableFeedingSchedule(@NotNull UUID feedingScheduleUid);

    void enableFeedingSchedule(@NotNull UUID feedingScheduleUid);

    void removeAnimalFromAllowance(@NotNull UUID animalUid);
}
