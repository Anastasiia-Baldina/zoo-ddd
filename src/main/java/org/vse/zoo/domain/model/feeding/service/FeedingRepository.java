package org.vse.zoo.domain.model.feeding.service;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.vse.zoo.domain.model.feeding.entity.FeedingScheduleEntity;

import java.util.Collection;
import java.util.UUID;

public interface FeedingRepository {

    void save(@NotNull FeedingScheduleEntity entity);

    void delete(@NotNull UUID feedingScheduleEntityUid);

    @Nullable
    FeedingScheduleEntity findByUid(@NotNull UUID feedingScheduleEntityUid);

    @NotNull
    Collection<FeedingScheduleEntity> fetchAll();
}
