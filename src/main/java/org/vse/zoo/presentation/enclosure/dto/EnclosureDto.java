package org.vse.zoo.presentation.enclosure.dto;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public class EnclosureDto {
    private UUID uid;
    private int maxCount;
    private List<UUID> animals;
    private int height;
    private int width;
    private int length;
    private String type;
    private List<String> acceptableAnimalTypes;
    private Instant cleaningTime;

    public UUID getUid() {
        return uid;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public int getMaxCount() {
        return maxCount;
    }

    public void setMaxCount(int maxCount) {
        this.maxCount = maxCount;
    }

    public List<UUID> getAnimals() {
        return animals;
    }

    public void setAnimals(List<UUID> animals) {
        this.animals = animals;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getAcceptableAnimalTypes() {
        return acceptableAnimalTypes;
    }

    public void setAcceptableAnimalTypes(List<String> acceptableAnimalTypes) {
        this.acceptableAnimalTypes = acceptableAnimalTypes;
    }

    public Instant getCleaningTime() {
        return cleaningTime;
    }

    public void setCleaningTime(Instant cleaningTime) {
        this.cleaningTime = cleaningTime;
    }
}
