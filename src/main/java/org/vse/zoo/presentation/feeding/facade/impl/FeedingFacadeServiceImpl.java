package org.vse.zoo.presentation.feeding.facade.impl;

import org.vse.zoo.application.service.feeding.FeedingOrganizationService;
import org.vse.zoo.application.transaction.TransactionManager;
import org.vse.zoo.application.utils.Asserts;
import org.vse.zoo.presentation.feeding.assembler.ScheduleDtoAssembler;
import org.vse.zoo.presentation.feeding.dto.ResultDto;
import org.vse.zoo.presentation.feeding.dto.FeedingScheduleDto;
import org.vse.zoo.presentation.feeding.facade.FeedingFacadeService;

import java.util.List;
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

    @Override
    public ResultDto<List<FeedingScheduleDto>> listSchedule() {
        var dtoAssembler = new ScheduleDtoAssembler();
        var tx = transactionManager.createReadTx();
        tx.begin();
        try {
            var valRes = feedingService.findAll().stream()
                    .map(dtoAssembler::toDto)
                    .toList();
            return dtoAssembler.toResultDto(valRes);
        } catch (Exception e) {
            return dtoAssembler.toErrorResultDto(e);
        } finally {
            tx.end();
        }
    }
}
