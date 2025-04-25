package org.vse.zoo.presentation.statistics.facade;

import org.vse.zoo.presentation.statistics.dto.AvailableEnclosureDto;
import org.vse.zoo.presentation.statistics.dto.EmptyEnclosureDto;
import org.vse.zoo.presentation.statistics.dto.ResultDto;
import org.vse.zoo.presentation.statistics.dto.SickAnimalDto;

import java.util.List;

public interface StatisticsFacadeService {

    ResultDto<List<SickAnimalDto>> findSickAnimals();

    ResultDto<List<AvailableEnclosureDto>> findAvailableEnclosures(String animalType);

    ResultDto<List<EmptyEnclosureDto>> findEmptyEnclosures();
}
