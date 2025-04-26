package org.vse.zoo.application.service.enclosure;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.vse.zoo.domain.model.enclosure.entity.Enclosure;

import java.util.List;
import java.util.UUID;

public interface EnclosureService {

    void addEnclosure(@NotNull Enclosure enclosure);

    void updateEnclosure(@NotNull Enclosure enclosure);

    @Nullable
    Enclosure findByUid(@NotNull UUID enclosureUid);

    void putAnimalIn(@NotNull UUID enclosureUid,
                     @NotNull UUID animalUid,
                     @NotNull String animalType);

    void removeAnimalFrom(@NotNull UUID enclosureUid, @NotNull UUID animalUid);

    @NotNull
    List<Enclosure> findAll();

    void delete(@NotNull UUID enclosureUid);

    void cleanEnclosure(@NotNull UUID enclosureUid);
}
