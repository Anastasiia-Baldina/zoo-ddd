package org.vse.zoo.presentation.animal.facade;

import org.vse.zoo.presentation.animal.dto.AnimalDto;
import org.vse.zoo.presentation.animal.dto.ResultDto;

import java.util.List;
import java.util.UUID;

public interface AnimalFacadeService {

    ResultDto<List<AnimalDto>> listAnimals();

    ResultDto<AnimalDto> addAnimal(AnimalDto animalDto);

    ResultDto<Void> removeAnimal(UUID animalUid);

    ResultDto<AnimalDto> treatAnimal(UUID animalUid);

    ResultDto<AnimalDto> declareAnimalSick(UUID animalUid);
}
