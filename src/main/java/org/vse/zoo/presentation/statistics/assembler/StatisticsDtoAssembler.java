package org.vse.zoo.presentation.statistics.assembler;

import org.jetbrains.annotations.NotNull;
import org.vse.zoo.domain.model.animal.entity.AnimalEntity;
import org.vse.zoo.domain.model.enclosure.entity.EnclosureEntity;
import org.vse.zoo.presentation.statistics.dto.AvailableEnclosureDto;
import org.vse.zoo.presentation.statistics.dto.EmptyEnclosureDto;
import org.vse.zoo.presentation.statistics.dto.ResultDto;
import org.vse.zoo.presentation.statistics.dto.SickAnimalDto;

public class StatisticsDtoAssembler {

    public SickAnimalDto toSickAnimalDto(AnimalEntity entity) {
        var dto = new SickAnimalDto();
        dto.setAnimalUid(entity.getUid());
        dto.setUpdateTime(entity.getHealthState().getUpdateTime());
        return dto;
    }

    public AvailableEnclosureDto toAvailableEnclosureDto(EnclosureEntity entity) {
        int vacancyCount = entity.getCapacity().getMaxCount() - entity.getCapacity().getAnimals().size();
        var dto = new AvailableEnclosureDto();
        dto.setEnclosureUid(entity.getUid());
        dto.setVacancyCount(vacancyCount);
        return dto;
    }

    public EmptyEnclosureDto toEmptyEnclosureDto(EnclosureEntity entity) {
        var dto = new EmptyEnclosureDto();
        dto.setEnclosureUid(entity.getUid());
        dto.setEnclosureType(entity.getType().getValue());
        return dto;
    }

    @NotNull
    public <T> ResultDto<T> toResultDto(T resultValue) {
        ResultDto<T> res = new ResultDto<>();
        res.setSuccess(true);
        res.setValue(resultValue);

        return res;
    }

    @NotNull
    public <T> ResultDto<T> toErrorResultDto(Exception e) {
        ResultDto<T> res = new ResultDto<>();
        res.setSuccess(false);
        res.setErrorMessage(e.getClass().getSimpleName() + ':' + e.getMessage());

        return res;
    }
}
