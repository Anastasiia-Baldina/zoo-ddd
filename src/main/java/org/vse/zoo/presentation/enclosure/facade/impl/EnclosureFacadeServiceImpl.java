package org.vse.zoo.presentation.enclosure.facade.impl;

import org.vse.zoo.application.service.enclosure.AnimalTransferService;
import org.vse.zoo.application.service.enclosure.EnclosureService;
import org.vse.zoo.application.transaction.TransactionManager;
import org.vse.zoo.application.utils.Asserts;
import org.vse.zoo.presentation.enclosure.assembler.EnclosureDtoAssembler;
import org.vse.zoo.presentation.enclosure.dto.EnclosureDto;
import org.vse.zoo.presentation.enclosure.dto.ResultDto;
import org.vse.zoo.presentation.enclosure.dto.TransferDto;
import org.vse.zoo.presentation.enclosure.facade.EnclosureFacadeService;

import java.util.List;
import java.util.UUID;

public class EnclosureFacadeServiceImpl implements EnclosureFacadeService {
    private final TransactionManager transactionManager;
    private final EnclosureService enclosureService;
    private final AnimalTransferService transferService;

    public EnclosureFacadeServiceImpl(TransactionManager transactionManager,
                                      EnclosureService enclosureService,
                                      AnimalTransferService transferService) {
        this.transactionManager = transactionManager;
        this.enclosureService = enclosureService;
        this.transferService = transferService;
    }

    @Override
    public ResultDto<List<EnclosureDto>> findAll() {
        var dtoAssembler = new EnclosureDtoAssembler();
        var tx = transactionManager.createReadTx();
        tx.begin();
        try {
            var resVal = enclosureService.findAll().stream()
                    .map(dtoAssembler::toDto)
                    .toList();
            return dtoAssembler.toResultDto(resVal);
        } catch (Exception e) {
            return dtoAssembler.toErrorResultDto(e);
        } finally {
            tx.end();
        }
    }

    @Override
    public ResultDto<EnclosureDto> addEnclosure(EnclosureDto enclosureDto) {
        var dtoAssembler = new EnclosureDtoAssembler();
        var tx = transactionManager.createWriteTx();
        tx.begin();
        try {
            Asserts.notNull(enclosureDto, "enclosureDto");
            enclosureDto.setUid(UUID.randomUUID());
            var entity = dtoAssembler.fromDto(enclosureDto);
            enclosureService.addEnclosure(entity);
            var resVal = dtoAssembler.toDto(
                    enclosureService.findByUid(entity.getUid()));
            return dtoAssembler.toResultDto(resVal);
        } catch (Exception e) {
            return dtoAssembler.toErrorResultDto(e);
        } finally {
            tx.end();
        }
    }

    @Override
    public ResultDto<Void> removeEnclosure(UUID enclosureUid) {
        var dtoAssembler = new EnclosureDtoAssembler();
        var tx = transactionManager.createWriteTx();
        tx.begin();
        try {
            enclosureService.delete(Asserts.notNull(enclosureUid, "enclosureUid"));
            return dtoAssembler.toVoidResultDto();
        } catch (Exception e) {
            return dtoAssembler.toErrorResultDto(e);
        } finally {
            tx.end();
        }
    }

    @Override
    public ResultDto<EnclosureDto> cleanEnclosure(UUID enclosureUid) {
        var dtoAssembler = new EnclosureDtoAssembler();
        var tx = transactionManager.createWriteTx();
        tx.begin();
        try {
            enclosureService.cleanEnclosure(
                    Asserts.notNull(enclosureUid, "enclosureUid")
            );
            var resVal = dtoAssembler.toDto(enclosureService.findByUid(enclosureUid));
            return dtoAssembler.toResultDto(resVal);
        } catch (Exception e) {
            return dtoAssembler.toErrorResultDto(e);
        } finally {
            tx.end();
        }
    }

    @Override
    public ResultDto<Void> transferAnimal(TransferDto transferDto) {
        var dtoAssembler = new EnclosureDtoAssembler();
        var tx = transactionManager.createWriteTx();
        tx.begin();
        try {
            Asserts.notNull(transferDto, "transferDto");
            transferService.transfer(
                    Asserts.notNull(transferDto.getAnimalUid(), "TransferDto.animalUid"),
                    Asserts.notNull(transferDto.getDestinationEnclosureUid(), "TransferDto.destinationEnclosureUid")
            );
        } catch (Exception e) {
            return dtoAssembler.toErrorResultDto(e);
        } finally {
            tx.end();
        }
        return dtoAssembler.toVoidResultDto();
    }
}
