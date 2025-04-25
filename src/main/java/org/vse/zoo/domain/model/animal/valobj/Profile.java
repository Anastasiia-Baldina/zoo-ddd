package org.vse.zoo.domain.model.animal.valobj;

import org.jetbrains.annotations.NotNull;
import org.vse.zoo.application.utils.Asserts;
import org.vse.zoo.domain.shared.ValueObject;

import java.time.Instant;
import java.time.LocalDate;

public class Profile implements ValueObject<Profile> {
    private final String nickname;
    private final String animalType;
    private final String gender;
    private final LocalDate birthdate;
    private final Instant updateTime;

    private Profile(Builder b) {
        nickname = b.nickname;
        animalType = b.animalType;
        gender = b.gender;
        updateTime = b.updateTime;
        birthdate = b.birthdate;
    }

    @NotNull
    @Override
    public Profile valid() {
        Asserts.notNull(nickname, "Profile.nickname");
        Asserts.notNull(animalType, "Profile.type");
        Asserts.notNull(gender, "Profile.gender");
        Asserts.notNull(birthdate, "Profile.birthdate");
        Asserts.notNull(updateTime, "Profile.updateTime");
        return this;
    }

    @Override
    public Instant getUpdateTime() {
        return updateTime;
    }

    public String getNickname() {
        return nickname;
    }

    public String getAnimalType() {
        return animalType;
    }

    public String getGender() {
        return gender;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public static class Builder {
        private  String nickname;
        private  String animalType;
        private  String gender;
        private LocalDate birthdate;
        private Instant updateTime;

        public Profile build() {
            return new Profile(this);
        }

        public Builder setNickname(String nickname) {
            this.nickname = nickname;
            return this;
        }

        public Builder setAnimalType(String animalType) {
            this.animalType = animalType;
            return this;
        }

        public Builder setGender(String gender) {
            this.gender = gender;
            return this;
        }

        public Builder setUpdateTime(Instant updateTime) {
            this.updateTime = updateTime;
            return this;
        }

        public Builder setBirthdate(LocalDate birthdate) {
            this.birthdate = birthdate;
            return this;
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return builder()
                .setNickname(nickname)
                .setAnimalType(animalType)
                .setGender(gender)
                .setBirthdate(birthdate)
                .setUpdateTime(updateTime);
    }
}
