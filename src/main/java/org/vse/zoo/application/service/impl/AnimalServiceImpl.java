package org.vse.zoo.application.service.impl;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.vse.zoo.application.exception.BusinessLogicException;
import org.vse.zoo.application.service.AnimalService;
import org.vse.zoo.domain.model.animal.entity.AnimalEntity;
import org.vse.zoo.domain.model.animal.service.AnimalRepository;

import java.util.List;
import java.util.UUID;

public class AnimalServiceImpl implements AnimalService {
    private final AnimalRepository animalRepository;

    public AnimalServiceImpl(AnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
    }

    @Override
    public void addAnimal(@NotNull AnimalEntity animal) {
        if(animalRepository.findByUid(animal.getUid()) != null) {
            throw new BusinessLogicException(
                    "Животное с идентификатором %s уже присутствует"
                            .formatted(animal.getUid()));
        }
        animalRepository.save(animal);
    }

    @Override
    public void updateAnimal(@NotNull AnimalEntity animal) {
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
    public List<AnimalEntity> findAll() {
        return animalRepository.list();
    }

    @Nullable
    @Override
    public AnimalEntity findByUid(@NotNull UUID animalUid) {
        return animalRepository.findByUid(animalUid);
    }
}
