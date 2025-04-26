package org.vse.zoo.infrastructure.enclosure;

import org.jetbrains.annotations.Nullable;
import org.vse.zoo.domain.model.enclosure.entity.Enclosure;

import java.util.*;

public class InMemoryEnclosureRepository implements EnclosureRepository {
    private final Map<UUID, Enclosure> entries = new LinkedHashMap<>();

    @Override
    public void save(Enclosure enclosure) {
        entries.put(enclosure.getUid(), enclosure);
    }

    @Override
    public void delete(UUID enclosureUid) {
        entries.remove(enclosureUid);
    }

    @Nullable
    @Override
    public Enclosure findByUid(UUID enclosureId) {
        return entries.get(enclosureId);
    }

    @Override
    public Collection<Enclosure> fetchAll() {
        return List.copyOf(entries.values());
    }
}
