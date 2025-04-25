package org.vse.zoo.presentation.enclosure.assembler;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.vse.zoo.domain.model.enclosure.entity.EnclosureAggregate;
import org.vse.zoo.domain.model.enclosure.entity.EnclosureEntity;
import org.vse.zoo.domain.model.enclosure.valobj.Compatibility;
import org.vse.zoo.domain.model.enclosure.valobj.EnclosureSize;
import org.vse.zoo.presentation.enclosure.dto.EnclosureDto;
import org.vse.zoo.presentation.enclosure.dto.ResultDto;

public class EnclosureDtoAssembler {

    @Nullable
    public EnclosureDto toDto(@Nullable EnclosureEntity entity) {
        if(entity == null) {
            return null;
        }
        var size = entity.getSize();
        var capacity = entity.getCapacity();

        var dto = new EnclosureDto();
        dto.setUid(entity.getUid());
        dto.setWidth(size.getWidth());
        dto.setHeight(size.getHeight());
        dto.setLength(size.getLength());
        dto.setMaxCount(capacity.getMaxCount());
        dto.setAnimals(capacity.getAnimals());
        dto.setType(entity.getType().getValue());
        dto.setAcceptableAnimalTypes(
                entity.getCompatibilities().stream()
                        .map(Compatibility::getAnimalType)
                        .toList());
        dto.setCleaningTime(
                entity.getCleaning() == null ? null : entity.getCleaning().getUpdateTime());

        return dto;
    }

    public EnclosureEntity fromDto(EnclosureDto dto) {
        var size = EnclosureSize.builder()
                .setHeight(dto.getHeight())
                .setWidth(dto.getWidth())
                .setLength(dto.getLength())
                .build();

        return new EnclosureAggregate(dto.getUid())
                .setSize(size)
                .setType(dto.getType())
                .setMaxAnimalCount(dto.getMaxCount())
                .setCompatibilities(dto.getAcceptableAnimalTypes())
                .buildRootEntity();

    }

    @NotNull
    public <T> ResultDto<T> toResultDto(T resultValue) {
        ResultDto<T> res = new ResultDto<>();
        res.setSuccess(true);
        res.setValue(resultValue);

        return res;
    }

    @NotNull
    public ResultDto<Void> toVoidResultDto() {
        ResultDto<Void> res = new ResultDto<>();
        res.setSuccess(true);
        res.setValue(null);

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
