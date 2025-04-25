package org.vse.zoo.application.service;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.vse.zoo.domain.model.animal.entity.AnimalEntity;

import java.util.List;
import java.util.UUID;

public interface AnimalService {

    void addAnimal(@NotNull AnimalEntity animal);

    void updateAnimal(@NotNull AnimalEntity animal);

    void removeAnimal(@NotNull UUID animalUid);

    @NotNull
    List<AnimalEntity> findAll();

    @Nullable
    AnimalEntity findByUid(@NotNull UUID animalUid);
}
