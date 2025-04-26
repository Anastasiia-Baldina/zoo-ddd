package org.vse.zoo.infrastructure.enclosure;

import org.jetbrains.annotations.Nullable;
import org.vse.zoo.domain.model.enclosure.entity.Enclosure;

import java.util.Collection;
import java.util.UUID;

public interface EnclosureRepository {
    void save(Enclosure enclosure);

    void delete(UUID enclosureUid);

    @Nullable
    Enclosure findByUid(UUID enclosureId);

    Collection<Enclosure> fetchAll();
}
