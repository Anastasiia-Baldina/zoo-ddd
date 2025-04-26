package org.vse.zoo.presentation.statistics.facade.impl;

import org.vse.zoo.application.service.statistics.ZooStatisticsService;
import org.vse.zoo.presentation.statistics.assembler.StatisticsDtoAssembler;
import org.vse.zoo.presentation.statistics.dto.AvailableEnclosureDto;
import org.vse.zoo.presentation.statistics.dto.EmptyEnclosureDto;
import org.vse.zoo.presentation.statistics.dto.ResultDto;
import org.vse.zoo.presentation.statistics.dto.SickAnimalDto;
import org.vse.zoo.presentation.statistics.facade.StatisticsFacadeService;

import java.util.List;

public class StatisticsFacadeServiceImpl implements StatisticsFacadeService {
    private final ZooStatisticsService statisticsService;

    public StatisticsFacadeServiceImpl(ZooStatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @Override
    public ResultDto<List<SickAnimalDto>> findSickAnimals() {
        var dtoAssembler = new StatisticsDtoAssembler();
        try {
            var valRes = statisticsService.findSickAnimals().stream()
                    .map(dtoAssembler::toSickAnimalDto)
                    .toList();
            return dtoAssembler.toResultDto(valRes);
        } catch (Exception e) {
            return dtoAssembler.toErrorResultDto(e);
        }
    }

    @Override
    public ResultDto<List<AvailableEnclosureDto>> findAvailableEnclosures(String animalType) {
        var dtoAssembler = new StatisticsDtoAssembler();
        try {
            var valRes = statisticsService.findAvailableEnclosures(animalType).stream()
                    .map(dtoAssembler::toAvailableEnclosureDto)
                    .toList();
            return dtoAssembler.toResultDto(valRes);
        } catch (Exception e) {
            return dtoAssembler.toErrorResultDto(e);
        }
    }

    @Override
    public ResultDto<List<EmptyEnclosureDto>> findEmptyEnclosures() {
        var dtoAssembler = new StatisticsDtoAssembler();
        try {
            var valRes = statisticsService.findEmptyEnclosures().stream()
                    .map(dtoAssembler::toEmptyEnclosureDto)
                    .toList();
            return dtoAssembler.toResultDto(valRes);
        } catch (Exception e) {
            return dtoAssembler.toErrorResultDto(e);
        }
    }
}
