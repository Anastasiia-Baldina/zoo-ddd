package org.vse.zoo.presentation.feeding.facade;

import org.vse.zoo.presentation.feeding.dto.FeedingScheduleDto;
import org.vse.zoo.presentation.feeding.dto.ResultDto;

import java.util.UUID;

public interface FeedingFacadeService {
    ResultDto<FeedingScheduleDto> addSchedule(FeedingScheduleDto scheduleDto);

    ResultDto<Void> deleteSchedule(UUID feedingScheduleUid);
}
