package org.vse.zoo.presentation;

import org.vse.zoo.domain.model.animal.entity.AnimalAggregate;
import org.vse.zoo.domain.model.animal.entity.Animal;
import org.vse.zoo.domain.model.animal.valobj.HealthState;
import org.vse.zoo.domain.model.animal.valobj.Location;
import org.vse.zoo.domain.model.animal.valobj.Profile;
import org.vse.zoo.domain.model.animal.valobj.Requirement;
import org.vse.zoo.domain.model.enclosure.entity.EnclosureAggregate;
import org.vse.zoo.domain.model.enclosure.entity.Enclosure;
import org.vse.zoo.domain.model.enclosure.valobj.EnclosureSize;
import org.vse.zoo.domain.model.feeding.entity.FeedingScheduleAggregate;
import org.vse.zoo.domain.model.feeding.entity.FeedingSchedule;
import org.vse.zoo.domain.model.feeding.valobj.Recipient;
import org.vse.zoo.domain.model.feeding.valobj.Schedule;
import org.vse.zoo.domain.model.feeding.valobj.Timer;
import org.vse.zoo.presentation.animal.dto.AnimalDto;
import org.vse.zoo.presentation.animal.dto.AnimalUidDto;
import org.vse.zoo.presentation.enclosure.dto.EnclosureDto;
import org.vse.zoo.presentation.enclosure.dto.EnclosureUidDto;
import org.vse.zoo.presentation.enclosure.dto.TransferDto;
import org.vse.zoo.presentation.feeding.dto.FeedingScheduleUidDto;
import org.vse.zoo.presentation.feeding.dto.FeedingScheduleDto;
import org.vse.zoo.presentation.statistics.dto.AnimalTypeDto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

public final class DataUtils {
    private DataUtils() {
    }

    public static AnimalDto monkeyMarusiaDto(UUID enclosureUid) {
        var dto = new AnimalDto();
        dto.setUid(UUID.randomUUID());
        dto.setFoodCount(500);
        dto.setFoodType("банан");
        dto.setBirthdate(LocalDate.of(2010, 5, 12));
        dto.setHealthy(true);
        dto.setGender("самка");
        dto.setNickname("Маруся");
        dto.setAnimalType("обезьяна");
        dto.setEnclosureUid(enclosureUid);

        return dto;
    }

    public static Animal monkeyMarusia(UUID enclosureUid) {
        return new AnimalAggregate(UUID.randomUUID())
                .setHealthState(HealthState.builder()
                        .setHealthy(true)
                        .build())
                .setRequirement(Requirement.builder()
                        .setFoodCount(500)
                        .setFoodType("банан")
                        .build())
                .setProfile(Profile.builder()
                        .setBirthdate(LocalDate.of(2010, 5, 12))
                        .setNickname("Маруся")
                        .setAnimalType("обезьяна")
                        .setGender("самка")
                        .build())
                .setLocation(Location.builder()
                        .setEnclosureUid(enclosureUid)
                        .build())
                .buildRootEntity();
    }

    public static Enclosure monkeyEnclosure() {
        return monkeyEnclosure(UUID.randomUUID(), 1);
    }

    public static Enclosure monkeyEnclosure(UUID enclosureUid, int maxCount) {
        return new EnclosureAggregate(enclosureUid)
                .setCompatibilities(List.of("обезьяна"))
                .setType("вольер")
                .setMaxAnimalCount(maxCount)
                .setSize(EnclosureSize.builder()
                        .setWidth(10)
                        .setLength(10)
                        .setHeight(10)
                        .build())
                .buildRootEntity();
    }

    public static Enclosure crocodileEnclosure() {
        return new EnclosureAggregate(UUID.randomUUID())
                .setCompatibilities(List.of("крокодил"))
                .setType("вольер")
                .setMaxAnimalCount(1)
                .setSize(EnclosureSize.builder()
                        .setWidth(10)
                        .setLength(10)
                        .setHeight(10)
                        .build())
                .buildRootEntity();
    }

    public static EnclosureDto monkeyEnclosureDto() {
        var dto = new EnclosureDto();
        dto.setType("вольер");
        dto.setMaxCount(1);
        dto.setWidth(10);
        dto.setLength(10);
        dto.setHeight(10);
        dto.setAcceptableAnimalTypes(List.of("обезьяна"));

        return dto;
    }

    public static Enclosure overflowEnclosure(UUID uid) {
        return new EnclosureAggregate(uid)
                .setCompatibilities(List.of("обезьяна"))
                .setType("вольер")
                .setMaxAnimalCount(1)
                .setSize(EnclosureSize.builder()
                        .setWidth(10)
                        .setLength(10)
                        .setHeight(10)
                        .build())
                .putAnimalIn(UUID.randomUUID(), "обезьяна")
                .buildRootEntity();
    }

    public static FeedingSchedule monkeySchedule(UUID monkeyUid) {
        return new FeedingScheduleAggregate(UUID.randomUUID())
                .setRecipient(Recipient.builder()
                        .setAnimalUid(monkeyUid)
                        .build())
                .setSchedule(Schedule.builder()
                        .setIntervalDays(1)
                        .setMealtime(LocalTime.now())
                        .build())
                .setTimer(Timer.builder()
                        .setEnabled(false)
                        .setNextTriggerTime(LocalDateTime.now())
                        .build())
                .buildRootEntity();
    }

    public static FeedingScheduleDto scheduleDto(UUID animalUid) {
        var dto = new FeedingScheduleDto();
        dto.setEnabled(true);
        dto.setMealtime(LocalTime.now());
        dto.setIntervalDays(1);
        dto.setAnimalUid(animalUid);
        return dto;
    }

    public static AnimalUidDto animalUidDto(UUID uid) {
        var dto = new AnimalUidDto();
        dto.setUid(uid);
        return dto;
    }

    public static EnclosureUidDto enclosureUidDto(UUID uid) {
        var dto = new EnclosureUidDto();
        dto.setUid(uid);
        return dto;
    }

    public static TransferDto transferDto(UUID animalUid, UUID dstUid) {
        var dto = new TransferDto();
        dto.setAnimalUid(animalUid);
        dto.setDestinationEnclosureUid(dstUid);
        return dto;
    }

    public static FeedingScheduleUidDto feedingScheduleUidDto(UUID uid) {
        var dto = new FeedingScheduleUidDto();
        dto.setUid(uid);
        return dto;
    }

    public static AnimalTypeDto animalTypeDto(String animalType) {
        var dto = new AnimalTypeDto();
        dto.setValue(animalType);
        return dto;
    }
}
