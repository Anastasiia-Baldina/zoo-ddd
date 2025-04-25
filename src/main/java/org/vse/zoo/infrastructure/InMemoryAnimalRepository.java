package org.vse.zoo.infrastructure;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.vse.zoo.domain.model.animal.entity.AnimalEntity;
import org.vse.zoo.domain.model.animal.service.AnimalRepository;

import java.util.*;

public class InMemoryAnimalRepository implements AnimalRepository {
    private final Map<UUID, AnimalEntity> animals = new LinkedHashMap<>();

    @Override
    public void save(@NotNull AnimalEntity animal) {
        animals.put(animal.getUid(), animal);
    }

    @Override
    public void delete(@NotNull UUID animalUid) {
        animals.remove(animalUid);
    }

    @Nullable
    @Override
    public AnimalEntity findByUid(@NotNull UUID animalUid) {
        return animals.get(animalUid);
    }

    @Override
    public List<AnimalEntity> list() {
        return new ArrayList<>(animals.values());
    }
}
