package org.vse.zoo.infrastructure.animal;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.vse.zoo.domain.model.animal.entity.Animal;

import java.util.List;
import java.util.UUID;

public interface AnimalRepository {
    void save(@NotNull Animal animal);

    void delete(@NotNull UUID animalUid);

    @Nullable
    Animal findByUid(@NotNull UUID animalUid);

    List<Animal> list();
}
