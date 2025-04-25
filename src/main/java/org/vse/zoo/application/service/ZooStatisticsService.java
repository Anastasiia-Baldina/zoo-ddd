package org.vse.zoo.application.service;

import org.vse.zoo.domain.model.animal.entity.AnimalEntity;
import org.vse.zoo.domain.model.enclosure.entity.EnclosureEntity;

import java.util.List;

public interface ZooStatisticsService {

    List<AnimalEntity> findSickAnimals();

    List<EnclosureEntity> findAvailableEnclosures(String animalType);

    List<EnclosureEntity> findEmptyEnclosures();
}
