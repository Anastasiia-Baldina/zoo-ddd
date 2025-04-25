package org.vse.zoo.presentation.feeding.assembler;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.vse.zoo.domain.model.feeding.entity.FeedingScheduleAggregate;
import org.vse.zoo.domain.model.feeding.entity.FeedingScheduleEntity;
import org.vse.zoo.domain.model.feeding.valobj.Recipient;
import org.vse.zoo.domain.model.feeding.valobj.Schedule;
import org.vse.zoo.domain.model.feeding.valobj.Timer;
import org.vse.zoo.presentation.feeding.dto.ResultDto;
import org.vse.zoo.presentation.feeding.dto.FeedingScheduleDto;

public class ScheduleDtoAssembler {

    @Nullable
    public FeedingScheduleDto toDto(@Nullable FeedingScheduleEntity entity) {
        if(entity == null) {
            return null;
        }
        var rcp = entity.getRecipient();
        var sch = entity.getSchedule();
        var dto = new FeedingScheduleDto();
        var timer = entity.getTimer();

        dto.setUid(entity.getUid());
        dto.setAnimalUid(rcp.getAnimalUid());
        dto.setIntervalDays(sch.getIntervalDays());
        dto.setMealtime(sch.getMealtime());
        dto.setNextTriggerTime(timer.getNextTriggerTime());
        dto.setEnabled(timer.isEnabled());

        return dto;
    }

    public FeedingScheduleEntity fromDto(FeedingScheduleDto dto) {
        return new FeedingScheduleAggregate(dto.getUid())
                .setTimer(Timer.builder()
                        .setEnabled(dto.isEnabled())
                        .setNextTriggerTime(dto.getNextTriggerTime())
                        .build())
                .setRecipient(Recipient.builder()
                        .setAnimalUid(dto.getAnimalUid())
                        .build())
                .setSchedule(Schedule.builder()
                        .setIntervalDays(dto.getIntervalDays())
                        .setMealtime(dto.getMealtime())
                        .build())
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
