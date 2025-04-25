package org.vse.zoo.presentation.feeding.facade.impl;

import org.vse.zoo.application.service.FeedingOrganizationService;
import org.vse.zoo.application.transaction.TransactionManager;
import org.vse.zoo.application.utils.Asserts;
import org.vse.zoo.presentation.feeding.assembler.ScheduleDtoAssembler;
import org.vse.zoo.presentation.feeding.dto.ResultDto;
import org.vse.zoo.presentation.feeding.dto.FeedingScheduleDto;
import org.vse.zoo.presentation.feeding.facade.FeedingFacadeService;

import java.util.UUID;

public class FeedingFacadeServiceImpl implements FeedingFacadeService {
    private final TransactionManager transactionManager;
    private final FeedingOrganizationService feedingService;

    public FeedingFacadeServiceImpl(TransactionManager transactionManager,
                                    FeedingOrganizationService feedingService) {
        this.transactionManager = transactionManager;
        this.feedingService = feedingService;
    }

    @Override
    public ResultDto<FeedingScheduleDto> addSchedule(FeedingScheduleDto scheduleDto) {
        var dtoAssembler = new ScheduleDtoAssembler();
        var tx = transactionManager.createWriteTx();
        tx.begin();
        try {
            Asserts.notNull(scheduleDto, "scheduleDto")
                    .setUid(UUID.randomUUID());
            var entity = dtoAssembler.fromDto(scheduleDto);
            feedingService.addFeedingSchedule(entity);
            var resVal = dtoAssembler.toDto(feedingService.findByUid(entity.getUid()));
            return dtoAssembler.toResultDto(resVal);
        } catch (Exception e) {
            return dtoAssembler.toErrorResultDto(e);
        } finally {
            tx.end();
        }
    }

    @Override
    public ResultDto<Void> deleteSchedule(UUID feedingScheduleUid) {
        var dtoAssembler = new ScheduleDtoAssembler();
        var tx = transactionManager.createWriteTx();
        tx.begin();
        try {
            feedingService.deleteFeedingSchedule(
                    Asserts.notNull(feedingScheduleUid, "feedingScheduleUid")
            );
            return dtoAssembler.toVoidResultDto();
        } catch (Exception e) {
            return dtoAssembler.toErrorResultDto(e);
        } finally {
            tx.end();
        }
    }
}
