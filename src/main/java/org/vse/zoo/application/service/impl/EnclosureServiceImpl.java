package org.vse.zoo.application.service.impl;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.vse.zoo.application.exception.BusinessLogicException;
import org.vse.zoo.application.service.EnclosureService;
import org.vse.zoo.domain.model.enclosure.entity.EnclosureAggregate;
import org.vse.zoo.domain.model.enclosure.entity.EnclosureEntity;
import org.vse.zoo.domain.model.enclosure.service.EnclosureRepository;

import java.util.List;
import java.util.UUID;

public class EnclosureServiceImpl implements EnclosureService {
    private final EnclosureRepository enclosureRepository;

    public EnclosureServiceImpl(EnclosureRepository enclosureRepository) {
        this.enclosureRepository = enclosureRepository;
    }

    @Override
    public void addEnclosure(@NotNull EnclosureEntity enclosure) {
        if (enclosureRepository.findByUid(enclosure.getUid()) != null) {
            throw new BusinessLogicException(
                    "Вольер с идентификатором %s уже присутствует"
                            .formatted(enclosure.getUid()));
        }
        enclosureRepository.save(enclosure);
    }

    @Override
    public void updateEnclosure(@NotNull EnclosureEntity enclosure) {
        if (enclosureRepository.findByUid(enclosure.getUid()) == null) {
            throw new BusinessLogicException(
                    "Вольер с идентификатором %s не найден"
                            .formatted(enclosure.getUid()));
        }
        enclosureRepository.save(enclosure);
    }

    @Override
    @Nullable
    public EnclosureEntity findByUid(@NotNull UUID enclosureUid) {
        return enclosureRepository.findByUid(enclosureUid);
    }

    @Override
    public void putAnimalIn(@NotNull UUID enclosureUid,
                            @NotNull UUID animalUid,
                            @NotNull String animalType) {
        var enclosure = enclosureRepository.findByUid(enclosureUid);
        if (enclosure == null) {
            throw new BusinessLogicException(
                    "Вольер с идентификатором %s не найден".formatted(enclosureUid));
        }
        enclosure = new EnclosureAggregate(enclosure)
                .putAnimalIn(animalUid, animalType)
                .buildRootEntity();
        enclosureRepository.save(enclosure);
    }

    @Override
    public void removeAnimalFrom(@NotNull UUID enclosureUid, @NotNull UUID animalUid) {
        var enclosure = enclosureRepository.findByUid(enclosureUid);
        if (enclosure == null) {
            throw new BusinessLogicException(
                    "Вольер с идентификатором %s не найден".formatted(enclosureUid));
        }
        enclosure = new EnclosureAggregate(enclosure)
                .removeAnimal(animalUid)
                .buildRootEntity();
        enclosureRepository.save(enclosure);
    }

    @NotNull
    @Override
    public List<EnclosureEntity> findAll() {
        return List.copyOf(enclosureRepository.fetchAll());
    }

    @Override
    public void delete(@NotNull UUID enclosureUid) {
        var entity = enclosureRepository.findByUid(enclosureUid);
        if (entity == null) {
            throw new BusinessLogicException(
                    "Вольер с идентификатором %s не найден"
                            .formatted(enclosureUid));
        }
        new EnclosureAggregate(entity).makeDeleteChecks();
        enclosureRepository.delete(enclosureUid);
    }

    @Override
    public void cleanEnclosure(@NotNull UUID enclosureUid) {
        var entity = enclosureRepository.findByUid(enclosureUid);
        if (entity == null) {
            throw new BusinessLogicException(
                    "Вольер с идентификатором %s не найден"
                            .formatted(enclosureUid));
        }
        entity = new EnclosureAggregate(entity)
                .cleanEnclosure()
                .buildRootEntity();
        enclosureRepository.save(entity);
    }
}
