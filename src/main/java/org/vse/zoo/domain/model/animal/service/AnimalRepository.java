package org.vse.zoo.domain.model.animal.service;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.vse.zoo.domain.model.animal.entity.AnimalEntity;

import java.util.List;
import java.util.UUID;

public interface AnimalRepository {
    void save(@NotNull AnimalEntity animal);

    void delete(@NotNull UUID animalUid);

    @Nullable
    AnimalEntity findByUid(@NotNull UUID animalUid);

    List<AnimalEntity> list();
}
