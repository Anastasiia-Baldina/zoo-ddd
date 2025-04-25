package org.vse.zoo.infrastructure;

import org.jetbrains.annotations.Nullable;
import org.vse.zoo.domain.model.enclosure.entity.EnclosureEntity;
import org.vse.zoo.domain.model.enclosure.service.EnclosureRepository;

import java.util.*;

public class InMemoryEnclosureRepository implements EnclosureRepository {
    private final Map<UUID, EnclosureEntity> entries = new LinkedHashMap<>();

    @Override
    public void save(EnclosureEntity enclosure) {
        entries.put(enclosure.getUid(), enclosure);
    }

    @Override
    public void delete(UUID enclosureUid) {
        entries.remove(enclosureUid);
    }

    @Nullable
    @Override
    public EnclosureEntity findByUid(UUID enclosureId) {
        return entries.get(enclosureId);
    }

    @Override
    public Collection<EnclosureEntity> fetchAll() {
        return List.copyOf(entries.values());
    }
}
