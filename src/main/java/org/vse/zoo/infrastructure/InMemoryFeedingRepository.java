package org.vse.zoo.infrastructure;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.vse.zoo.domain.model.feeding.entity.FeedingScheduleEntity;
import org.vse.zoo.domain.model.feeding.service.FeedingRepository;

import java.util.*;

public class InMemoryFeedingRepository implements FeedingRepository {
    private final Map<UUID, FeedingScheduleEntity> entries = new LinkedHashMap<>();

    @Override
    public void save(@NotNull FeedingScheduleEntity entity) {
        entries.put(entity.getUid(), entity);
    }

    @Override
    public void delete(@NotNull UUID feedingScheduleEntityUid) {
        entries.remove(feedingScheduleEntityUid);
    }

    @Nullable
    @Override
    public FeedingScheduleEntity findByUid(@NotNull UUID feedingScheduleEntityUid) {
        return entries.remove(feedingScheduleEntityUid);
    }

    @NotNull
    @Override
    public Collection<FeedingScheduleEntity> fetchAll() {
        return List.copyOf(entries.values());
    }
}
