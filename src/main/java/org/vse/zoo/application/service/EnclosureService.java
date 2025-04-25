package org.vse.zoo.application.service;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.vse.zoo.domain.model.enclosure.entity.EnclosureEntity;

import java.util.List;
import java.util.UUID;

public interface EnclosureService {

    void addEnclosure(@NotNull EnclosureEntity enclosure);

    void updateEnclosure(@NotNull EnclosureEntity enclosure);

    @Nullable
    EnclosureEntity findByUid(@NotNull UUID enclosureUid);

    void putAnimalIn(@NotNull UUID enclosureUid,
                     @NotNull UUID animalUid,
                     @NotNull String animalType);

    void removeAnimalFrom(@NotNull UUID enclosureUid, @NotNull UUID animalUid);

    @NotNull
    List<EnclosureEntity> findAll();

    void delete(@NotNull UUID enclosureUid);

    void cleanEnclosure(@NotNull UUID enclosureUid);
}
