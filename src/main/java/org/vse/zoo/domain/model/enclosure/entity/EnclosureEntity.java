package org.vse.zoo.domain.model.enclosure.entity;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.vse.zoo.application.utils.Asserts;
import org.vse.zoo.domain.model.enclosure.valobj.*;
import org.vse.zoo.domain.shared.DomainEntity;

import java.util.Collection;
import java.util.UUID;

public class EnclosureEntity implements DomainEntity {
    @NotNull
    private final UUID uid;
    @NotNull
    private final Capacity capacity;
    @NotNull
    private final EnclosureType type;
    @NotNull
    private final EnclosureSize size;
    @Nullable
    private final Cleaning cleaning;
    @NotNull
    private final Collection<Compatibility> compatibilities;

    EnclosureEntity(@NotNull UUID uid,
                    @NotNull Capacity capacity,
                    @NotNull EnclosureType type,
                    @NotNull EnclosureSize size,
                    @Nullable Cleaning cleaning,
                    @NotNull Collection<Compatibility> compatibilities) {
        this.uid = uid;
        this.capacity = capacity;
        this.type = type;
        this.size = size;
        this.compatibilities = Asserts.notNull(compatibilities, "compatibilities")
                .stream()
                .map(Compatibility::valid)
                .toList();
        this.cleaning = cleaning;
    }

    @NotNull
    @Override
    public UUID getUid() {
        return uid;
    }

    @NotNull
    public Capacity getCapacity() {
        return capacity;
    }

    @NotNull
    public EnclosureType getType() {
        return type;
    }

    @NotNull
    public EnclosureSize getSize() {
        return size;
    }

    @NotNull
    public Collection<Compatibility> getCompatibilities() {
        return compatibilities;
    }

    @Nullable
    public Cleaning getCleaning() {
        return cleaning;
    }
}
