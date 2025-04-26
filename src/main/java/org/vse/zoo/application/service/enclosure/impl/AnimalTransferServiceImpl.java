package org.vse.zoo.application.service.enclosure.impl;

import org.vse.zoo.application.exception.BusinessLogicException;
import org.vse.zoo.application.service.enclosure.AnimalTransferService;
import org.vse.zoo.application.event.DomainEventPublisher;
import org.vse.zoo.application.transaction.TransactionManager;
import org.vse.zoo.domain.event.AnimalMovedEvent;
import org.vse.zoo.domain.model.animal.entity.AnimalAggregate;
import org.vse.zoo.infrastructure.animal.AnimalRepository;
import org.vse.zoo.domain.model.enclosure.entity.EnclosureAggregate;
import org.vse.zoo.infrastructure.enclosure.EnclosureRepository;

import java.util.UUID;

public class AnimalTransferServiceImpl implements AnimalTransferService {
    private final AnimalRepository animalRepository;
    private final EnclosureRepository enclosureRepository;
    private final DomainEventPublisher eventPublisher;
    private final TransactionManager txManager;

    public AnimalTransferServiceImpl(AnimalRepository animalRepository,
                                     EnclosureRepository enclosureRepository,
                                     DomainEventPublisher eventPublisher, TransactionManager txManager) {
        this.animalRepository = animalRepository;
        this.enclosureRepository = enclosureRepository;
        this.eventPublisher = eventPublisher;
        this.txManager = txManager;
    }

    @Override
    public void transfer(UUID animalUid, UUID destinationEnclosureUid) {
        var tx = txManager.createWriteTx();
        tx.begin();
        try {
            var animal = animalRepository.findByUid(animalUid);
            if (animal == null) {
                var err = "Животное с идентификатором %s не найдено".formatted(animalUid);
                throw new BusinessLogicException(err);
            }
            var destination = enclosureRepository.findByUid(destinationEnclosureUid);
            if (destination == null) {
                var err = "Вольер с идентификатором %s не найден".formatted(animalUid);
                throw new BusinessLogicException(err);
            }
            var source = enclosureRepository.findByUid(animal.getLocation().getEnclosureUid());
            if (source == null) {
                var err = "Вольер с идентификатором %s не найден".formatted(animalUid);
                throw new BusinessLogicException(err);
            }
            var dstEnclosureAggregate = new EnclosureAggregate(destination);
            var sourceEnclosureAggregate = new EnclosureAggregate(source);
            var animalAggregate = new AnimalAggregate(animal);

            sourceEnclosureAggregate.removeAnimal(animalUid);
            dstEnclosureAggregate.putAnimalIn(animalUid, animal.getProfile().getAnimalType());
            animalAggregate.changeLocation(animal.getLocation().getEnclosureUid(), destinationEnclosureUid);

            source = sourceEnclosureAggregate.buildRootEntity();
            destination = dstEnclosureAggregate.buildRootEntity();
            animal = animalAggregate.buildRootEntity();

            enclosureRepository.save(source);
            enclosureRepository.save(destination);
            animalRepository.save(animal);
            eventPublisher.fairEvent(new AnimalMovedEvent(animal, source, destination));
        } finally {
            tx.end();
        }
    }
}
