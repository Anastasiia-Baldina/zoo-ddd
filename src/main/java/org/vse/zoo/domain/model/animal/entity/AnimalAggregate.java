package org.vse.zoo.domain.model.animal.entity;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.vse.zoo.application.exception.BusinessLogicException;
import org.vse.zoo.application.utils.Asserts;
import org.vse.zoo.domain.model.animal.valobj.Feeding;
import org.vse.zoo.domain.model.animal.valobj.HealthState;
import org.vse.zoo.domain.model.animal.valobj.Location;
import org.vse.zoo.domain.model.animal.valobj.Profile;
import org.vse.zoo.domain.model.animal.valobj.Requirement;
import org.vse.zoo.domain.shared.EntityAggregate;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public class AnimalAggregate implements EntityAggregate<Animal> {
    @NotNull
    private final UUID uid;
    @Nullable
    private Location location;
    @Nullable
    private HealthState healthState;
    @Nullable
    private Requirement requirement;
    @Nullable
    private Profile profile;
    @Nullable
    private Feeding feeding;

    public AnimalAggregate(UUID uid) {
        this.uid = Asserts.notNull(uid, "uid");
    }

    public AnimalAggregate(Animal animalEntity) {
        Asserts.notNull(animalEntity, "animalEntity");
        uid = animalEntity.getUid();
        location = animalEntity.getLocation();
        healthState = animalEntity.getHealthState();
        requirement = animalEntity.getRequirement();
        profile = animalEntity.getProfile();
        feeding = animalEntity.getFeeding();
    }

    @Override
    @NotNull
    public Animal buildRootEntity() {
        return new Animal(
                uid,
                Asserts.notNull(location, "location"),
                Asserts.notNull(healthState, "healthState"),
                Asserts.notNull(requirement, "requirement"),
                Asserts.notNull(profile, "profile"),
                feeding);
    }

    @NotNull
    public AnimalAggregate setProfile(Profile profile) {
        this.profile = Asserts.notNull(profile, "profile")
                .toBuilder()
                .setUpdateTime(Instant.now())
                .build()
                .valid();
        return this;
    }

    @NotNull
    public AnimalAggregate setRequirement(Requirement requirement) {
        this.requirement = Asserts.notNull(requirement, "requirement")
                .toBuilder()
                .setUpdateTime(Instant.now())
                .build()
                .valid();
        return this;
    }

    public AnimalAggregate setHealthState(HealthState healthState) {
        this.healthState = Asserts.notNull(healthState, "healthState")
                .toBuilder()
                .setUpdateTime(Instant.now())
                .build()
                .valid();
        return this;
    }

    @NotNull
    public AnimalAggregate setLocation(Location location) {
        if(this.location != null
                && !Objects.equals(this.location.getEnclosureUid(), location.getEnclosureUid())) {
            var err = "Попытка перемещения животного из вольера %s в вольер %s без соответсвующей операции"
                    .formatted(this.location.getEnclosureUid(), location.getEnclosureUid());
            throw new BusinessLogicException(err);
        }
        this.location = location.toBuilder()
                .setUpdateTime(Instant.now())
                .build()
                .valid();
        return this;
    }

    public void changeLocation(UUID oldEnclosureUid, UUID newEnclosureId) {
        if (location == null || !Objects.equals(location.getEnclosureUid(), oldEnclosureUid)) {
            var err = "Невозможно переместить животное из вольера %s, так как животное там не находится"
                    .formatted(oldEnclosureUid);
            throw new BusinessLogicException(err);
        }
        if(Objects.equals(oldEnclosureUid, newEnclosureId)) {
            var err = "Невозможно переместить животное в вольер %s, так как животное уже там находится"
                    .formatted(newEnclosureId);
            throw new BusinessLogicException(err);
        }
        location = Location.builder()
                .setEnclosureUid(newEnclosureId)
                .setUpdateTime(Instant.now())
                .build()
                .valid();
    }

    public void treat() {
        if (healthState == null) {
            throw new BusinessLogicException("Невозможно лечить животное, так как состояние здоровья не известно");
        }
        if (healthState.isHealthy()) {
            throw new BusinessLogicException("Невозможно лечить животное, так как животное здорово");
        }
        healthState = HealthState.builder()
                .setHealthy(true)
                .setUpdateTime(Instant.now())
                .build()
                .valid();
    }

    public AnimalAggregate declareSick() {
        healthState = HealthState.builder()
                .setHealthy(false)
                .setUpdateTime(Instant.now())
                .build()
                .valid();
        return this;
    }

    @NotNull
    public AnimalAggregate feed() {
        feeding = Feeding.builder()
                .setUpdateTime(Instant.now())
                .build()
                .valid();
        return this;
    }
}
