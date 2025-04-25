package org.vse.zoo.presentation.animal.dto;

import java.time.LocalDate;
import java.util.UUID;

public class AnimalDto {
    private UUID uid;
    private String nickname;
    private String animalType;
    private String gender;
    private LocalDate birthdate;
    private boolean healthy;
    private UUID enclosureUid;
    private String foodType;
    private int foodCount;

    public UUID getUid() {
        return uid;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAnimalType() {
        return animalType;
    }

    public void setAnimalType(String animalType) {
        this.animalType = animalType;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public boolean isHealthy() {
        return healthy;
    }

    public void setHealthy(boolean healthy) {
        this.healthy = healthy;
    }

    public UUID getEnclosureUid() {
        return enclosureUid;
    }

    public void setEnclosureUid(UUID enclosureUid) {
        this.enclosureUid = enclosureUid;
    }

    public String getFoodType() {
        return foodType;
    }

    public void setFoodType(String foodType) {
        this.foodType = foodType;
    }

    public int getFoodCount() {
        return foodCount;
    }

    public void setFoodCount(int foodCount) {
        this.foodCount = foodCount;
    }
}
