package org.vse.zoo.presentation.animal.facade.impl;

import org.vse.zoo.application.exception.BusinessLogicException;
import org.vse.zoo.application.service.AnimalService;
import org.vse.zoo.application.service.EnclosureService;
import org.vse.zoo.application.service.FeedingOrganizationService;
import org.vse.zoo.application.transaction.TransactionManager;
import org.vse.zoo.application.utils.Asserts;
import org.vse.zoo.domain.model.animal.entity.AnimalAggregate;
import org.vse.zoo.presentation.animal.assembler.AnimalDtoAssembler;
import org.vse.zoo.presentation.animal.dto.AnimalDto;
import org.vse.zoo.presentation.animal.dto.ResultDto;
import org.vse.zoo.presentation.animal.facade.AnimalFacadeService;

import java.util.List;
import java.util.UUID;

public class AnimalFacadeServiceImpl implements AnimalFacadeService {
    private final TransactionManager transactionManager;
    private final AnimalService animalService;
    private final EnclosureService enclosureService;
    private final FeedingOrganizationService feedingService;

    public AnimalFacadeServiceImpl(TransactionManager transactionManager,
                                   AnimalService animalService,
                                   EnclosureService enclosureService,
                                   FeedingOrganizationService feedingService) {
        this.transactionManager = transactionManager;
        this.animalService = animalService;
        this.enclosureService = enclosureService;
        this.feedingService = feedingService;
    }

    @Override
    public ResultDto<List<AnimalDto>> listAnimals() {
        var dtoAssembler = new AnimalDtoAssembler();
        var tx = transactionManager.createReadTx();
        tx.begin();
        try {
            var resVal = animalService.findAll().stream()
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
    public ResultDto<AnimalDto> addAnimal(AnimalDto animalDto) {
        var dtoAssembler = new AnimalDtoAssembler();
        var tx = transactionManager.createWriteTx();
        tx.begin();
        try {
            Asserts.notNull(animalDto, "animalDto");
            var animalUid = UUID.randomUUID();
            animalDto.setUid(animalUid);
            var animal = dtoAssembler.fromDto(animalDto);
            var animalType = animal.getProfile().getAnimalType();

            enclosureService.putAnimalIn(animalDto.getEnclosureUid(), animalUid, animalType);
            animalService.addAnimal(animal);

            var resVal = dtoAssembler.toDto(animalService.findByUid(animalUid));
            return dtoAssembler.toResultDto(resVal);
        } catch (Exception e) {
            return dtoAssembler.toErrorResultDto(e);
        } finally {
            tx.end();
        }
    }

    @Override
    public ResultDto<Void> removeAnimal(UUID animalUid) {
        var dtoAssembler = new AnimalDtoAssembler();
        var tx = transactionManager.createWriteTx();
        tx.begin();
        try {
            Asserts.notNull(animalUid, "animalUid");
            var animal = animalService.findByUid(animalUid);
            if (animal == null) {
                var err = "Животное с идентификатором %s не найдено"
                        .formatted(animalUid);
                throw new BusinessLogicException(err);
            }
            var enclosureUid = animal.getLocation().getEnclosureUid();

            animalService.removeAnimal(animalUid);
            enclosureService.removeAnimalFrom(enclosureUid, animalUid);
            feedingService.removeAnimalFromAllowance(animalUid);

            return dtoAssembler.toVoidResultDto();
        } catch (Exception e) {
            return dtoAssembler.toErrorResultDto(e);
        } finally {
            tx.end();
        }
    }

    @Override
    public ResultDto<AnimalDto> treatAnimal(UUID animalUid) {
        var dtoAssembler = new AnimalDtoAssembler();
        var tx = transactionManager.createWriteTx();
        tx.begin();
        try {
            Asserts.notNull(animalUid, "animalUid");
            var animal = animalService.findByUid(animalUid);
            if (animal == null) {
                var err = "Животное с идентификатором %s не найдено"
                        .formatted(animalUid);
                throw new BusinessLogicException(err);
            }
            var aggregate = new AnimalAggregate(animal);
            aggregate.treatAnimal();
            animalService.updateAnimal(aggregate.buildRootEntity());
            var resVal = dtoAssembler.toDto(animalService.findByUid(animalUid));
            return dtoAssembler.toResultDto(resVal);
        } catch (Exception e) {
            return dtoAssembler.toErrorResultDto(e);
        } finally {
            tx.end();
        }
    }

    @Override
    public ResultDto<AnimalDto> declareAnimalSick(UUID animalUid) {
        var dtoAssembler = new AnimalDtoAssembler();
        var tx = transactionManager.createWriteTx();
        tx.begin();
        try {
            Asserts.notNull(animalUid, "animalUid");
            var animal = animalService.findByUid(animalUid);
            if (animal == null) {
                var err = "Животное с идентификатором %s не найдено"
                        .formatted(animalUid);
                throw new BusinessLogicException(err);
            }
            var aggregate = new AnimalAggregate(animal);
            aggregate.declareSick();
            animalService.updateAnimal(aggregate.buildRootEntity());
            var resVal = dtoAssembler.toDto(animalService.findByUid(animalUid));
            return dtoAssembler.toResultDto(resVal);
        } catch (Exception e) {
            return dtoAssembler.toErrorResultDto(e);
        } finally {
            tx.end();
        }
    }
}
