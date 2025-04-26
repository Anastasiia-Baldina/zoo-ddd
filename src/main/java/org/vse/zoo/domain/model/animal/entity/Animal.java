package org.vse.zoo.domain.model.animal.entity;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.vse.zoo.domain.model.animal.valobj.*;
import org.vse.zoo.domain.shared.DomainEntity;

import java.util.UUID;

public class Animal implements DomainEntity {
    @NotNull
    private final UUID uid;
    @NotNull
    private final Location location;
    @NotNull
    private final HealthState healthState;
    @NotNull
    private final Requirement requirement;
    @NotNull
    private final Profile profile;
    @Nullable
    private final Feeding feeding;

    Animal(@NotNull UUID uid,
           @NotNull Location location,
           @NotNull HealthState healthState,
           @NotNull Requirement requirement,
           @NotNull Profile profile,
           @Nullable Feeding feeding) {
        this.uid = uid;
        this.location = location;
        this.healthState = healthState;
        this.requirement = requirement;
        this.profile = profile;
        this.feeding = feeding;
    }

    @NotNull
    @Override
    public UUID getUid() {
        return uid;
    }

    @NotNull
    public Location getLocation() {
        return location;
    }

    @NotNull
    public HealthState getHealthState() {
        return healthState;
    }

    @NotNull
    public Requirement getRequirement() {
        return requirement;
    }

    @NotNull
    public Profile getProfile() {
        return profile;
    }

    @Nullable
    public Feeding getFeeding() {
        return feeding;
    }
}
