package org.vse.zoo.presentation.statistics.web;

import org.springframework.web.bind.annotation.*;
import org.vse.zoo.presentation.statistics.dto.*;
import org.vse.zoo.presentation.statistics.facade.StatisticsFacadeService;

import java.util.List;

@RestController
@RequestMapping("/statistics")
public class StatisticsFacadeController {
    private final StatisticsFacadeService facadeService;

    public StatisticsFacadeController(StatisticsFacadeService facadeService) {
        this.facadeService = facadeService;
    }

    @GetMapping(value = "/sick-animal", produces = "application/json")
    @ResponseBody
    ResultDto<List<SickAnimalDto>> sickAnimals() {
        return facadeService.findSickAnimals();
    }

    @PostMapping(value = "/available-enclosure", produces = "application/json")
    @ResponseBody
    ResultDto<List<AvailableEnclosureDto>> availableEnclosures(@RequestBody AnimalTypeDto animalType) {
        return facadeService.findAvailableEnclosures(animalType.getValue());
    }

    @GetMapping(value = "/empty-enclosure", produces = "application/json")
    @ResponseBody
    ResultDto<List<EmptyEnclosureDto>> emptyEnclosures() {
        return facadeService.findEmptyEnclosures();
    }
}
