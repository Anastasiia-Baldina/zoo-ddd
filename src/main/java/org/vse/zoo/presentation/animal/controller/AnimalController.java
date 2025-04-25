package org.vse.zoo.presentation.animal.controller;

import org.springframework.web.bind.annotation.*;
import org.vse.zoo.presentation.animal.dto.AnimalDto;
import org.vse.zoo.presentation.animal.dto.AnimalUidDto;
import org.vse.zoo.presentation.animal.dto.ResultDto;
import org.vse.zoo.presentation.animal.facade.AnimalFacadeService;

import java.util.List;

@RestController
@RequestMapping("/animal")
public class AnimalController {
    private final AnimalFacadeService animalFacadeService;

    public AnimalController(AnimalFacadeService animalFacadeService) {
        this.animalFacadeService = animalFacadeService;
    }

    @PostMapping(value = "/add", produces = "application/json")
    @ResponseBody
    public ResultDto<AnimalDto> add(@RequestBody AnimalDto animalDto) {
        return animalFacadeService.addAnimal(animalDto);
    }

    @PostMapping(value = "/delete", produces = "application/json")
    @ResponseBody
    public ResultDto<Void> delete(@RequestBody AnimalUidDto animalUidDto) {
        return animalFacadeService.removeAnimal(animalUidDto.getUid());
    }

    @GetMapping(value = "/list", produces = "application/json")
    @ResponseBody
    public ResultDto<List<AnimalDto>> list() {
        return animalFacadeService.listAnimals();
    }

    @PostMapping(value = "/treat", produces = "application/json")
    @ResponseBody
    public ResultDto<AnimalDto> treat(@RequestBody AnimalUidDto animalUidDto) {
        return animalFacadeService.treatAnimal(animalUidDto.getUid());
    }

    @PostMapping(value = "/sick", produces = "application/json")
    @ResponseBody
    public ResultDto<AnimalDto> sick(@RequestBody AnimalUidDto animalUidDto) {
        return animalFacadeService.declareAnimalSick(animalUidDto.getUid());
    }
}
