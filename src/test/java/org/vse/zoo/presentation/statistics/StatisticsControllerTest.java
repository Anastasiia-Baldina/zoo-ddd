package org.vse.zoo.presentation.statistics;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.vse.zoo.domain.model.animal.entity.AnimalAggregate;
import org.vse.zoo.infrastructure.animal.AnimalRepository;
import org.vse.zoo.domain.model.enclosure.entity.EnclosureAggregate;
import org.vse.zoo.infrastructure.enclosure.EnclosureRepository;
import org.vse.zoo.presentation.DataUtils;
import org.vse.zoo.presentation.feeding.dto.ResultDto;
import org.vse.zoo.presentation.statistics.dto.AvailableEnclosureDto;
import org.vse.zoo.presentation.statistics.dto.EmptyEnclosureDto;
import org.vse.zoo.presentation.statistics.dto.SickAnimalDto;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StatisticsControllerTest {
    @Autowired
    TestRestTemplate restTemplate;
    @Autowired
    AnimalRepository animalRepository;
    @Autowired
    EnclosureRepository enclosureRepository;

    @Test
    public void should_find_sick_animal() {
        var healthyAnimal = DataUtils.monkeyMarusia(UUID.randomUUID());
        var sickAnimal = new AnimalAggregate(DataUtils.monkeyMarusia(UUID.randomUUID()))
                .declareSick()
                .buildRootEntity();
        animalRepository.save(healthyAnimal);
        animalRepository.save(sickAnimal);

        var rsp = restTemplate.getForObject(
                "/statistics/sick-animal", SickAnimalResponseDto.class);

        var sickAnimals = rsp.getValue();
        assertTrue(rsp.isSuccess());
        assertNotNull(sickAnimals);
        assertTrue(
                sickAnimals.stream()
                        .anyMatch(x -> Objects.equals(sickAnimal.getUid(), x.getAnimalUid()))
        );
        assertTrue(
                sickAnimals.stream()
                        .noneMatch(x -> Objects.equals(healthyAnimal.getUid(), x.getAnimalUid()))
        );
    }

    @Test
    public void should_find_empty_enclosure() {
        var full = new EnclosureAggregate(DataUtils.crocodileEnclosure())
                .putAnimalIn(UUID.randomUUID(), "крокодил")
                .buildRootEntity();
        var empty = DataUtils.crocodileEnclosure();
        enclosureRepository.save(full);
        enclosureRepository.save(empty);

        var rsp = restTemplate.getForObject(
                "/statistics/empty-enclosure", EmptyEnclosureResponseDto.class);

        var resList = rsp.getValue();
        assertTrue(rsp.isSuccess());
        assertNotNull(resList);
        assertTrue(
                resList.stream()
                        .anyMatch(x -> Objects.equals(empty.getUid(), x.getEnclosureUid()))
        );
        assertTrue(
                resList.stream()
                        .noneMatch(x -> Objects.equals(full.getUid(), x.getEnclosureUid()))
        );
    }

    @Test
    public void should_find_available_enclosure() {
        var full = new EnclosureAggregate(DataUtils.crocodileEnclosure())
                .putAnimalIn(UUID.randomUUID(), "крокодил")
                .buildRootEntity();
        var free = new EnclosureAggregate(DataUtils.crocodileEnclosure())
                .setMaxAnimalCount(2)
                .putAnimalIn(UUID.randomUUID(), "крокодил")
                .buildRootEntity();
        var foreign = DataUtils.monkeyEnclosure();
        enclosureRepository.save(full);
        enclosureRepository.save(free);
        enclosureRepository.save(foreign);

        var rsp = restTemplate.postForObject(
                "/statistics/available-enclosure",
                DataUtils.animalTypeDto("крокодил"),
                AvailableEnclosureResponseDto.class
        );

        var resList = rsp.getValue();
        assertTrue(rsp.isSuccess());
        assertNotNull(resList);
        assertTrue(
                resList.stream()
                        .anyMatch(x ->
                                Objects.equals(free.getUid(), x.getEnclosureUid())
                                        && x.getVacancyCount() == 1)
        );
        assertTrue(
                resList.stream()
                        .noneMatch(x -> Objects.equals(full.getUid(), x.getEnclosureUid()))
        );
        assertTrue(
                resList.stream()
                        .noneMatch(x -> Objects.equals(foreign.getUid(), x.getEnclosureUid()))
        );
    }

    public static class SickAnimalResponseDto extends ResultDto<List<SickAnimalDto>> {
    }

    public static class EmptyEnclosureResponseDto extends ResultDto<List<EmptyEnclosureDto>> {
    }

    public static class AvailableEnclosureResponseDto extends ResultDto<List<AvailableEnclosureDto>> {
    }
}
