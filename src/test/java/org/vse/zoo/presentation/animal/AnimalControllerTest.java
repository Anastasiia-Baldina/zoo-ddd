package org.vse.zoo.presentation.animal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.vse.zoo.infrastructure.animal.AnimalRepository;
import org.vse.zoo.infrastructure.enclosure.EnclosureRepository;
import org.vse.zoo.infrastructure.feeding.FeedingRepository;
import org.vse.zoo.presentation.DataUtils;
import org.vse.zoo.presentation.animal.dto.*;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AnimalControllerTest {
    @Autowired
    TestRestTemplate restTemplate;
    @Autowired
    AnimalRepository animalRepository;
    @Autowired
    EnclosureRepository enclosureRepository;
    @Autowired
    FeedingRepository feedingRepository;

    @Test
    public void should_not_add_animal_when_enclosure_does_not_exists() {
        var enclosureUid = UUID.randomUUID();
        var expErrMsg = "BusinessLogicException:Вольер с идентификатором %s не найден"
                .formatted(enclosureUid);
        var rsp = restTemplate.postForObject(
                "/animal/add",
                DataUtils.monkeyMarusiaDto(enclosureUid),
                AnimalResponseDto.class);
        assertFalse(rsp.isSuccess());
        assertEquals(expErrMsg, rsp.getErrorMessage());
    }

    @Test
    public void should_add_animal() {
        var enclosureUid = UUID.randomUUID();
        enclosureRepository.save(DataUtils.monkeyEnclosure(enclosureUid, 1));
        var rsp = restTemplate.postForObject(
                "/animal/add",
                DataUtils.monkeyMarusiaDto(enclosureUid),
                AnimalResponseDto.class);
        assertTrue(rsp.isSuccess());
        assertTrue(
                enclosureRepository.findByUid(enclosureUid)
                        .getCapacity()
                        .getAnimals()
                        .contains(rsp.getValue().getUid())
        );
    }

    @Test
    public void should_not_add_animal_when_enclosure_overflow() {
        var enclosure = DataUtils.overflowEnclosure(UUID.randomUUID());
        enclosureRepository.save(enclosure);
        var expErr = "BusinessLogicException:Достигнуто ограничение в 1 животных для данного вольера";

        var rsp = restTemplate.postForObject(
                "/animal/add",
                DataUtils.monkeyMarusiaDto(enclosure.getUid()),
                AnimalResponseDto.class);

        assertFalse(rsp.isSuccess());
        assertEquals(expErr, rsp.getErrorMessage());
    }

    @Test
    public void validate_list_animals() {
        var enclosureUid = UUID.randomUUID();
        enclosureRepository.save(DataUtils.monkeyEnclosure(enclosureUid, 1));
        var rspAdd = restTemplate.postForObject(
                "/animal/add",
                DataUtils.monkeyMarusiaDto(enclosureUid),
                AnimalResponseDto.class);
        assertTrue(rspAdd.isSuccess());
        var rspList = restTemplate.getForObject(
                "/animal/list",
                AnimalListResponseDto.class);
        assertTrue(rspList.isSuccess());
        assertTrue(
                rspList.getValue().stream()
                        .anyMatch(x -> Objects.equals(x.getUid(), rspAdd.getValue().getUid()))
        );
    }

    @Test
    public void should_return_error_on_delete_when_animal_not_exists() {
        AnimalUidDto dto = new AnimalUidDto();
        dto.setUid(UUID.randomUUID());
        var expErr = "BusinessLogicException:Животное с идентификатором %s не найдено"
                .formatted(dto.getUid());
        var rsp = restTemplate.postForObject(
                "/animal/delete",
                dto,
                ResultDto.class);
        assertFalse(rsp.isSuccess());
        assertEquals(expErr, rsp.getErrorMessage());
    }

    @Test
    public void should_delete_animal() {
        var enclosureUid = UUID.randomUUID();
        enclosureRepository.save(DataUtils.monkeyEnclosure(enclosureUid, 1));
        var rspAdd = restTemplate.postForObject(
                "/animal/add",
                DataUtils.monkeyMarusiaDto(enclosureUid),
                AnimalResponseDto.class);
        var schedule = DataUtils.monkeySchedule(rspAdd.getValue().getUid());
        feedingRepository.save(schedule);

        var rspDel = restTemplate.postForObject(
                "/animal/delete",
                DataUtils.animalUidDto(rspAdd.getValue().getUid()),
                ResultDto.class
        );
        assertTrue(rspDel.isSuccess());
        assertNull(animalRepository.findByUid(rspAdd.getValue().getUid()));
        assertNull(feedingRepository.findByUid(schedule.getUid()));
        var enclosure = enclosureRepository.findByUid(enclosureUid);
        assertFalse(enclosure.getCapacity().getAnimals().contains(rspAdd.getValue().getUid()));
    }

    @Test
    public void should_treat_animal() {
        var enclosureUid = UUID.randomUUID();
        enclosureRepository.save(DataUtils.monkeyEnclosure(enclosureUid, 1));
        var rspAdd = restTemplate.postForObject(
                "/animal/add",
                DataUtils.monkeyMarusiaDto(enclosureUid),
                AnimalResponseDto.class);
        var rsp = restTemplate.postForObject(
                "/animal/treat",
                DataUtils.animalUidDto(rspAdd.getValue().getUid()),
                AnimalResponseDto.class);
        assertFalse(rsp.isSuccess());
        rsp = restTemplate.postForObject(
                "/animal/sick",
                DataUtils.animalUidDto(rspAdd.getValue().getUid()),
                AnimalResponseDto.class);
        assertTrue(rsp.isSuccess());
        assertFalse(rsp.getValue().isHealthy());
        rsp = restTemplate.postForObject(
                "/animal/treat",
                DataUtils.animalUidDto(rspAdd.getValue().getUid()),
                AnimalResponseDto.class);
        assertTrue(rsp.isSuccess());
        assertTrue(rsp.getValue().isHealthy());
    }

    public static class AnimalListResponseDto extends ResultDto<List<AnimalDto>> {
    }

    public static class AnimalResponseDto extends ResultDto<AnimalDto> {
    }
}
