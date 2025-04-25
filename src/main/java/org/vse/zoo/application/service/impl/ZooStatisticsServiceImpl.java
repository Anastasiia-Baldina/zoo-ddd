package org.vse.zoo.application.service.impl;

import org.vse.zoo.application.service.ZooStatisticsService;
import org.vse.zoo.application.transaction.TransactionManager;
import org.vse.zoo.domain.model.animal.entity.AnimalEntity;
import org.vse.zoo.domain.model.animal.service.AnimalRepository;
import org.vse.zoo.domain.model.enclosure.entity.EnclosureEntity;
import org.vse.zoo.domain.model.enclosure.service.EnclosureRepository;

import java.util.List;
import java.util.Objects;

public class ZooStatisticsServiceImpl implements ZooStatisticsService {
    private final TransactionManager transactionManager;
    private final AnimalRepository animalRepository;
    private final EnclosureRepository enclosureRepository;

    public ZooStatisticsServiceImpl(TransactionManager transactionManager,
                                    AnimalRepository animalRepository,
                                    EnclosureRepository enclosureRepository) {
        this.transactionManager = transactionManager;
        this.animalRepository = animalRepository;
        this.enclosureRepository = enclosureRepository;
    }

    @Override
    public List<AnimalEntity> findSickAnimals() {
        var tx = transactionManager.createReadTx();
        tx.begin();
        try {
            return animalRepository.list().stream()
                    .filter(x -> !x.getHealthState().isHealthy())
                    .toList();
        } finally {
            tx.end();
        }
    }

    @Override
    public List<EnclosureEntity> findAvailableEnclosures(String animalType) {
        var tx = transactionManager.createReadTx();
        tx.begin();
        try {
            return enclosureRepository.fetchAll().stream()
                    .filter(x -> x.getCompatibilities().stream()
                            .anyMatch(c -> Objects.equals(animalType, c.getAnimalType())))
                    .filter(x -> x.getCapacity().getMaxCount() > x.getCapacity().getAnimals().size())
                    .toList();
        } finally {
            tx.end();
        }
    }

    @Override
    public List<EnclosureEntity> findEmptyEnclosures() {
        var tx = transactionManager.createReadTx();
        tx.begin();
        try {
            return enclosureRepository.fetchAll().stream()
                    .filter(x -> x.getCapacity().getAnimals().isEmpty())
                    .toList();
        } finally {
            tx.end();
        }
    }
}
