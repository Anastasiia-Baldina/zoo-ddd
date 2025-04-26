package org.vse.zoo.infrastructure.animal;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.vse.zoo.domain.model.animal.entity.Animal;

import java.util.*;

public class InMemoryAnimalRepository implements AnimalRepository {
    private final Map<UUID, Animal> animals = new LinkedHashMap<>();

    @Override
    public void save(@NotNull Animal animal) {
        animals.put(animal.getUid(), animal);
    }

    @Override
    public void delete(@NotNull UUID animalUid) {
        animals.remove(animalUid);
    }

    @Nullable
    @Override
    public Animal findByUid(@NotNull UUID animalUid) {
        return animals.get(animalUid);
    }

    @Override
    public List<Animal> list() {
        return new ArrayList<>(animals.values());
    }
}
