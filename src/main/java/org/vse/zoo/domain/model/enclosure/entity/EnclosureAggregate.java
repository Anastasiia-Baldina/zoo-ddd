package org.vse.zoo.domain.model.enclosure.entity;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.vse.zoo.application.utils.Asserts;
import org.vse.zoo.domain.model.enclosure.valobj.Capacity;
import org.vse.zoo.domain.model.enclosure.valobj.Cleaning;
import org.vse.zoo.domain.model.enclosure.valobj.Compatibility;
import org.vse.zoo.domain.model.enclosure.valobj.EnclosureSize;
import org.vse.zoo.domain.model.enclosure.valobj.EnclosureType;
import org.vse.zoo.domain.shared.EntityAggregate;
import org.vse.zoo.application.exception.BusinessLogicException;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

public class EnclosureAggregate implements EntityAggregate<Enclosure> {
    @NotNull
    private final UUID uid;
    @Nullable
    private Capacity capacity;
    @Nullable
    private EnclosureType type;
    @Nullable
    private EnclosureSize size;
    @Nullable
    private Cleaning cleaning;
    @NotNull
    private final Collection<Compatibility> compatibilities;

    public EnclosureAggregate(UUID uid) {
        this.uid = Asserts.notNull(uid, "uid");
        compatibilities = new ArrayList<>();
    }

    public EnclosureAggregate(Enclosure entity) {
        Asserts.notNull(entity, "entity");
        uid = entity.getUid();
        capacity = entity.getCapacity();
        type = entity.getType();
        size = entity.getSize();
        cleaning = entity.getCleaning();
        compatibilities = new ArrayList<>(entity.getCompatibilities());
    }

    @NotNull
    @Override
    public Enclosure buildRootEntity() {
        return new Enclosure(
                uid,
                Asserts.notNull(capacity, "capacity"),
                Asserts.notNull(type, "type"),
                Asserts.notNull(size, "size"),
                cleaning,
                List.copyOf(compatibilities));
    }

    public EnclosureAggregate setType(String enclosureType) {
        this.type = EnclosureType.builder()
                .setValue(enclosureType)
                .setUpdateTime(Instant.now())
                .build()
                .valid();
        return this;
    }

    public EnclosureAggregate setSize(EnclosureSize size) {
        this.size = size.toBuilder()
                .setUpdateTime(Instant.now())
                .build();
        return this;
    }

    public EnclosureAggregate setMaxAnimalCount(int maxAnimalCount) {
        Asserts.ensure(maxAnimalCount, x -> x > 0, "maxAnimalCount > 0");
        if (capacity != null) {
            if (capacity.getAnimals().size() > maxAnimalCount) {
                var err = ("Количество животных в вальере %s превышает " +
                        "максимально допустимое количество %s")
                        .formatted(capacity.getAnimals().size(), maxAnimalCount);
                throw new BusinessLogicException(err);
            }
            capacity = capacity.toBuilder()
                    .setMaxCount(maxAnimalCount)
                    .setUpdateTime(Instant.now())
                    .build()
                    .valid();
        } else {
            capacity = Capacity.builder()
                    .setMaxCount(maxAnimalCount)
                    .setAnimals(List.of())
                    .setUpdateTime(Instant.now())
                    .build()
                    .valid();
        }
        return this;
    }

    public EnclosureAggregate setCompatibilities(List<String> animalTypes) {
        if (!compatibilities.isEmpty()) {
            throw new BusinessLogicException("Список разрешенных типов животных уже заполнен." +
                    " Воспользуйтесь операцией добавления");
        }
        var uniqueTypes = new HashSet<>(animalTypes);
        for (var animalType : uniqueTypes) {
            var newCompatibility = Compatibility.builder()
                    .setAnimalType(animalType)
                    .setUpdateTime(Instant.now())
                    .build()
                    .valid();
            compatibilities.add(newCompatibility);
        }
        return this;
    }

    public EnclosureAggregate addCompatibility(String animalType) {
        Asserts.notEmpty(animalType, "animalType");
        var isExists = compatibilities.stream()
                .map(Compatibility::getAnimalType)
                .filter(x -> x.equals(animalType))
                .findFirst();
        if (isExists.isPresent()) {
            var err = "%s уже присутствует в списке разрешенных типов для заселения в вольер"
                    .formatted(animalType);
            throw new BusinessLogicException(err);
        }
        var newCompatibility = Compatibility.builder()
                .setAnimalType(animalType)
                .setUpdateTime(Instant.now())
                .build()
                .valid();
        compatibilities.add(newCompatibility);
        return this;
    }

    public EnclosureAggregate cleanEnclosure() {
        if (capacity == null) {
            throw new BusinessLogicException("Нет данных о размещенных в вольере животных");
        }
        if (!capacity.getAnimals().isEmpty()) {
            throw new BusinessLogicException("Для проведения уборки вольер должен быть пуст.");
        }
        cleaning = new Cleaning(Instant.now()).valid();
        return this;
    }

    public EnclosureAggregate removeAnimal(UUID animalUid) {
        if (capacity == null) {
            throw new BusinessLogicException("Нет данных о размещенных в вольере животных");
        }
        if (!capacity.getAnimals().contains(animalUid)) {
            var err = "Животное с идентификтором %s отсутствует в вольере".formatted(animalUid);
            throw new BusinessLogicException(err);
        }
        var newAnimals = new ArrayList<>(capacity.getAnimals());
        newAnimals.remove(animalUid);
        capacity = capacity.toBuilder()
                .setAnimals(newAnimals)
                .setUpdateTime(Instant.now())
                .build()
                .valid();
        return this;
    }

    public EnclosureAggregate putAnimalIn(UUID animalUid, String animalType) {
        Asserts.notNull(animalUid, "animalUid");
        Asserts.notNull(animalType, "animalType");
        if (capacity == null) {
            throw new BusinessLogicException("Нет данных о размещенных в вольере животных");
        }
        var isCompatible = compatibilities.stream()
                .map(Compatibility::getAnimalType)
                .anyMatch(x -> x.equals(animalType));
        if (!isCompatible) {
            var err = "Животное %s не пристутсвует в списке разрешенных к заселению в этот вольер"
                    .formatted(animalType);
            throw new BusinessLogicException(err);
        }
        if (capacity.getAnimals().contains(animalUid)) {
            var err = "Животное с идентификтором %s уже находится в данном вольере".formatted(animalUid);
            throw new BusinessLogicException(err);
        }
        if (capacity.getMaxCount() <= capacity.getAnimals().size()) {
            var err = "Достигнуто ограничение в %s животных для данного вольера".formatted(capacity.getMaxCount());
            throw new BusinessLogicException(err);
        }
        var newAnimals = new ArrayList<>(capacity.getAnimals());
        newAnimals.add(animalUid);
        capacity = capacity.toBuilder()
                .setAnimals(newAnimals)
                .setUpdateTime(Instant.now())
                .build()
                .valid();
        return this;
    }

    public void makeDeleteChecks() {
        if(capacity != null && !capacity.getAnimals().isEmpty()) {
            var errMsg = "Невозможно удалить непустой вольер %s"
                    .formatted(uid);
            throw new BusinessLogicException(errMsg);
        }
    }
}
