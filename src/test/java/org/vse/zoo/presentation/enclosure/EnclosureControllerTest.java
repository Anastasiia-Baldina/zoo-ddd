package org.vse.zoo.presentation.enclosure;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.vse.zoo.application.service.DomainEventHandler;
import org.vse.zoo.application.service.DomainEventObserver;
import org.vse.zoo.domain.event.AnimalMovedEvent;
import org.vse.zoo.domain.model.animal.service.AnimalRepository;
import org.vse.zoo.domain.model.enclosure.entity.EnclosureAggregate;
import org.vse.zoo.domain.model.enclosure.service.EnclosureRepository;
import org.vse.zoo.presentation.DataUtils;
import org.vse.zoo.presentation.enclosure.dto.EnclosureDto;
import org.vse.zoo.presentation.enclosure.dto.ResultDto;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EnclosureControllerTest {
    @Autowired
    TestRestTemplate restTemplate;
    @Autowired
    AnimalRepository animalRepository;
    @Autowired
    EnclosureRepository enclosureRepository;
    @Autowired
    DomainEventObserver eventObserver;

    @Test
    public void should_add_enclosure() {
        var enclosureDto = DataUtils.monkeyEnclosureDto();
        var rspDto = restTemplate.postForObject(
                "/enclosure/add", enclosureDto, EnclosureResponseDto.class);
        assertTrue(rspDto.isSuccess());
        assertNotNull(rspDto.getValue());
    }

    @Test
    public void should_return_error_on_delete_when_not_exists() {
        var enclosureUid = UUID.randomUUID();
        var expErr = "BusinessLogicException:Вольер с идентификатором %s не найден"
                .formatted(enclosureUid);
        var rspDto = restTemplate.postForObject(
                "/enclosure/delete", DataUtils.enclosureUidDto(enclosureUid), ResultDto.class);
        assertFalse(rspDto.isSuccess());
        assertEquals(expErr, rspDto.getErrorMessage());
    }

    @Test
    public void should_delete_enclosure() {
        var enclosureEntity = DataUtils.monkeyEnclosure(UUID.randomUUID(), 1);
        enclosureRepository.save(enclosureEntity);
        var rspDto = restTemplate.postForObject(
                "/enclosure/delete", DataUtils.enclosureUidDto(enclosureEntity.getUid()), ResultDto.class);
        assertTrue(rspDto.isSuccess());
        assertNull(enclosureRepository.findByUid(enclosureEntity.getUid()));
    }

    @Test
    public void should_list_enclosure() {
        var enclosureEntity = DataUtils.monkeyEnclosure(UUID.randomUUID(), 1);
        enclosureRepository.save(enclosureEntity);
        var rspDto = restTemplate.getForObject("/enclosure/list", EnclosureListResponseDto.class);

        assertTrue(rspDto.isSuccess());
        assertNotNull(rspDto.getValue());
        assertTrue(rspDto.getValue().stream()
                .anyMatch(x -> Objects.equals(enclosureEntity.getUid(), x.getUid())));
    }

    @Test
    public void should_clean_enclosure() {
        var enclosureDto = DataUtils.monkeyEnclosureDto();
        var rspAdd = restTemplate.postForObject(
                "/enclosure/add", enclosureDto, EnclosureResponseDto.class);
        var rspClean = restTemplate.postForObject(
                "/enclosure/clean", DataUtils.enclosureUidDto(rspAdd.getValue().getUid()), EnclosureResponseDto.class);
        assertTrue(rspClean.isSuccess());
    }

    @Test
    public void should_not_clean_enclosure_when_not_empty() {
        var enclosureEntity = DataUtils.monkeyEnclosure(UUID.randomUUID(), 1);
        enclosureEntity = new EnclosureAggregate(enclosureEntity)
                .putAnimalIn(UUID.randomUUID(), "обезьяна")
                .buildRootEntity();
        enclosureRepository.save(enclosureEntity);
        var rspClean = restTemplate.postForObject(
                "/enclosure/clean", DataUtils.enclosureUidDto(enclosureEntity.getUid()), EnclosureResponseDto.class);
        assertFalse(rspClean.isSuccess());
        assertEquals(
                "BusinessLogicException:Для проведения уборки вольер должен быть пуст.",
                rspClean.getErrorMessage());
    }

    @Test
    public void should_transfer_animal() {
        var srcEnclosureEntity = DataUtils.monkeyEnclosure();
        var dstEnclosureEntity = DataUtils.monkeyEnclosure();
        var monkeyEntity = DataUtils.monkeyMarusia(srcEnclosureEntity.getUid());
        srcEnclosureEntity = new EnclosureAggregate(srcEnclosureEntity)
                .putAnimalIn(monkeyEntity.getUid(), monkeyEntity.getProfile().getAnimalType())
                        .buildRootEntity();
        TransferHandler transferHandler = new TransferHandler();
        eventObserver.register(transferHandler);

        animalRepository.save(monkeyEntity);
        enclosureRepository.save(srcEnclosureEntity);
        enclosureRepository.save(dstEnclosureEntity);

        var transferDto = DataUtils.transferDto(monkeyEntity.getUid(), dstEnclosureEntity.getUid());
        var rspDto = restTemplate.postForObject(
                "/enclosure/transfer-animal", transferDto, ResultDto.class);

        monkeyEntity = animalRepository.findByUid(monkeyEntity.getUid());
        srcEnclosureEntity = enclosureRepository.findByUid(srcEnclosureEntity.getUid());
        dstEnclosureEntity = enclosureRepository.findByUid(dstEnclosureEntity.getUid());
        assertTrue(rspDto.isSuccess());
        assertNotNull(srcEnclosureEntity);
        assertNotNull(dstEnclosureEntity);
        assertNotNull(monkeyEntity);
        assertEquals(dstEnclosureEntity.getUid(), monkeyEntity.getLocation().getEnclosureUid());
        assertFalse(srcEnclosureEntity.getCapacity().getAnimals().contains(monkeyEntity.getUid()));
        assertTrue(dstEnclosureEntity.getCapacity().getAnimals().contains(monkeyEntity.getUid()));
        assertEquals(1, transferHandler.events.size());
        var event = transferHandler.events.get(0);
        assertEquals(monkeyEntity.getUid(), event.getAnimal().getUid());
        assertEquals(srcEnclosureEntity.getUid(), event.getSourceEnclosure().getUid());
        assertEquals(dstEnclosureEntity.getUid(), event.getDestinationEnclosure().getUid());
    }

    @Test
    public void should_not_transfer_animal_when_destination_is_not_allow() {
        var srcEnclosureEntity = DataUtils.monkeyEnclosure();
        var dstEnclosureEntity = DataUtils.crocodileEnclosure();
        var monkeyEntity = DataUtils.monkeyMarusia(srcEnclosureEntity.getUid());
        srcEnclosureEntity = new EnclosureAggregate(srcEnclosureEntity)
                .putAnimalIn(monkeyEntity.getUid(), monkeyEntity.getProfile().getAnimalType())
                .buildRootEntity();

        animalRepository.save(monkeyEntity);
        enclosureRepository.save(srcEnclosureEntity);
        enclosureRepository.save(dstEnclosureEntity);

        var transferDto = DataUtils.transferDto(monkeyEntity.getUid(), dstEnclosureEntity.getUid());
        var rspDto = restTemplate.postForObject(
                "/enclosure/transfer-animal", transferDto, ResultDto.class);

        assertFalse(rspDto.isSuccess());
    }

    public static class EnclosureListResponseDto extends ResultDto<List<EnclosureDto>> {
    }

    public static class EnclosureResponseDto extends ResultDto<EnclosureDto> {
    }

    private static class TransferHandler implements DomainEventHandler<AnimalMovedEvent> {
        final List<AnimalMovedEvent> events = new CopyOnWriteArrayList<>();

        @Override
        public void apply(AnimalMovedEvent event) {
            events.add(event);
        }

        @Override
        public Class<AnimalMovedEvent> eventType() {
            return AnimalMovedEvent.class;
        }
    }
}
