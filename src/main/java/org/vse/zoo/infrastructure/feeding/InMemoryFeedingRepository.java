package org.vse.zoo.infrastructure.feeding;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.vse.zoo.domain.model.feeding.entity.FeedingSchedule;

import java.util.*;

public class InMemoryFeedingRepository implements FeedingRepository {
    private final Map<UUID, FeedingSchedule> entries = new LinkedHashMap<>();

    @Override
    public void save(@NotNull FeedingSchedule entity) {
        entries.put(entity.getUid(), entity);
    }

    @Override
    public void delete(@NotNull UUID feedingScheduleEntityUid) {
        entries.remove(feedingScheduleEntityUid);
    }

    @Nullable
    @Override
    public FeedingSchedule findByUid(@NotNull UUID feedingScheduleEntityUid) {
        return entries.get(feedingScheduleEntityUid);
    }

    @NotNull
    @Override
    public Collection<FeedingSchedule> fetchAll() {
        return List.copyOf(entries.values());
    }
}
