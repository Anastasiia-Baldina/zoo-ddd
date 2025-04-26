package org.vse.zoo.application.service.animal.impl;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.vse.zoo.application.exception.BusinessLogicException;
import org.vse.zoo.application.service.animal.AnimalService;
import org.vse.zoo.domain.model.animal.entity.Animal;
import org.vse.zoo.infrastructure.animal.AnimalRepository;

import java.util.List;
import java.util.UUID;

public class AnimalServiceImpl implements AnimalService {
    private final AnimalRepository animalRepository;

    public AnimalServiceImpl(AnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
    }

    @Override
    public void addAnimal(@NotNull Animal animal) {
        if(animalRepository.findByUid(animal.getUid()) != null) {
            throw new BusinessLogicException(
                    "Животное с идентификатором %s уже присутствует"
                            .formatted(animal.getUid()));
        }
        animalRepository.save(animal);
    }

    @Override
    public void updateAnimal(@NotNull Animal animal) {
        if(animalRepository.findByUid(animal.getUid()) == null) {
            throw new BusinessLogicException(
                    "Животное с идентификатором %s не найдено"
                            .formatted(animal.getUid()));
        }
        animalRepository.save(animal);
    }

    @Override
    public void removeAnimal(@NotNull UUID animalUid) {
        if(animalRepository.findByUid(animalUid) == null) {
            throw new BusinessLogicException(
                    "Животное с идентификатором %s не найдено"
                            .formatted(animalUid));
        }
        animalRepository.delete(animalUid);
    }

    @NotNull
    @Override
    public List<Animal> findAll() {
        return animalRepository.list();
    }

    @Nullable
    @Override
    public Animal findByUid(@NotNull UUID animalUid) {
        return animalRepository.findByUid(animalUid);
    }
}
