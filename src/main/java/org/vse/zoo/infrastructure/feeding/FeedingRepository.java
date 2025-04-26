package org.vse.zoo.infrastructure.feeding;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.vse.zoo.domain.model.feeding.entity.FeedingSchedule;

import java.util.Collection;
import java.util.UUID;

public interface FeedingRepository {

    void save(@NotNull FeedingSchedule entity);

    void delete(@NotNull UUID feedingScheduleEntityUid);

    @Nullable
    FeedingSchedule findByUid(@NotNull UUID feedingScheduleEntityUid);

    @NotNull
    Collection<FeedingSchedule> fetchAll();
}
