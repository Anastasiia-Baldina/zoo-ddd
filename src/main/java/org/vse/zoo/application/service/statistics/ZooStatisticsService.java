package org.vse.zoo.application.service.statistics;

import org.vse.zoo.domain.model.animal.entity.Animal;
import org.vse.zoo.domain.model.enclosure.entity.Enclosure;

import java.util.List;

public interface ZooStatisticsService {

    List<Animal> findSickAnimals();

    List<Enclosure> findAvailableEnclosures(String animalType);

    List<Enclosure> findEmptyEnclosures();
}
