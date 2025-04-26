package org.vse.zoo.presentation.animal.assembler;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.vse.zoo.domain.model.animal.entity.AnimalAggregate;
import org.vse.zoo.domain.model.animal.entity.Animal;
import org.vse.zoo.domain.model.animal.valobj.HealthState;
import org.vse.zoo.domain.model.animal.valobj.Location;
import org.vse.zoo.domain.model.animal.valobj.Profile;
import org.vse.zoo.domain.model.animal.valobj.Requirement;
import org.vse.zoo.presentation.animal.dto.AnimalDto;
import org.vse.zoo.presentation.animal.dto.ResultDto;

public class AnimalDtoAssembler {

    @Nullable
    public AnimalDto toDto(@Nullable Animal entity) {
        if(entity == null) {
            return null;
        }
        var profile = entity.getProfile();
        var healthState = entity.getHealthState();
        var requirement = entity.getRequirement();

        var dto = new AnimalDto();
        dto.setUid(entity.getUid());
        dto.setAnimalType(profile.getAnimalType());
        dto.setNickname(profile.getNickname());
        dto.setBirthdate(profile.getBirthdate());
        dto.setGender(profile.getGender());
        dto.setEnclosureUid(entity.getLocation().getEnclosureUid());
        dto.setHealthy(healthState.isHealthy());
        dto.setFoodType(requirement.getFoodType());
        dto.setFoodCount(requirement.getFoodCount());

        return dto;
    }

    @NotNull
    public Animal fromDto(@NotNull AnimalDto dto) {
        var animalUid = dto.getUid();
        var profile = Profile.builder()
                .setAnimalType(dto.getAnimalType())
                .setNickname(dto.getNickname())
                .setGender(dto.getGender())
                .setBirthdate(dto.getBirthdate())
                .build();
        var requirement = Requirement.builder()
                .setFoodType(dto.getFoodType())
                .setFoodCount(dto.getFoodCount())
                .build();
        var healthState = HealthState.builder()
                .setHealthy(dto.isHealthy())
                .build();
        var location = Location.builder()
                .setEnclosureUid(dto.getEnclosureUid())
                .build();

        return new AnimalAggregate(animalUid)
                .setProfile(profile)
                .setRequirement(requirement)
                .setHealthState(healthState)
                .setLocation(location)
                .buildRootEntity();
    }

    @NotNull
    public <T> ResultDto<T> toResultDto(T resultValue) {
        ResultDto<T> res = new ResultDto<>();
        res.setSuccess(true);
        res.setValue(resultValue);

        return res;
    }

    @NotNull
    public ResultDto<Void> toVoidResultDto() {
        ResultDto<Void> res = new ResultDto<>();
        res.setSuccess(true);
        res.setValue(null);

        return res;
    }

    @NotNull
    public <T> ResultDto<T> toErrorResultDto(Exception e) {
        ResultDto<T> res = new ResultDto<>();
        res.setSuccess(false);
        res.setErrorMessage(e.getClass().getSimpleName() + ':' + e.getMessage());

        return res;
    }
}
