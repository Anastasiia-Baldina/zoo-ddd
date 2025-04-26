package org.vse.zoo.application.service.feeding;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.vse.zoo.domain.model.feeding.entity.FeedingSchedule;

import java.util.List;
import java.util.UUID;

public interface FeedingOrganizationService {

    @NotNull
    List<FeedingSchedule> findAll();

    @Nullable
    FeedingSchedule findByUid(@NotNull UUID feedingScheduleUid);

    void addFeedingSchedule(@NotNull FeedingSchedule entity);

    void updateFeedingSchedule(@NotNull FeedingSchedule entity);

    void deleteFeedingSchedule(@NotNull UUID feedingScheduleUid);

    void disableFeedingSchedule(@NotNull UUID feedingScheduleUid);

    void enableFeedingSchedule(@NotNull UUID feedingScheduleUid);

    void removeAnimalFromAllowance(@NotNull UUID animalUid);
}
