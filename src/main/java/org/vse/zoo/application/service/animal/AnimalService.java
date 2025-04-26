package org.vse.zoo.application.service.animal;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.vse.zoo.domain.model.animal.entity.Animal;

import java.util.List;
import java.util.UUID;

public interface AnimalService {

    void addAnimal(@NotNull Animal animal);

    void updateAnimal(@NotNull Animal animal);

    void removeAnimal(@NotNull UUID animalUid);

    @NotNull
    List<Animal> findAll();

    @Nullable
    Animal findByUid(@NotNull UUID animalUid);
}
