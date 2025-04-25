package org.vse.zoo.domain.model.enclosure.service;

import org.jetbrains.annotations.Nullable;
import org.vse.zoo.domain.model.enclosure.entity.EnclosureEntity;

import java.util.Collection;
import java.util.UUID;

public interface EnclosureRepository {
    void save(EnclosureEntity enclosure);

    void delete(UUID enclosureUid);

    @Nullable
    EnclosureEntity findByUid(UUID enclosureId);

    Collection<EnclosureEntity> fetchAll();
}
