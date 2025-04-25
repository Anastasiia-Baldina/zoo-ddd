package org.vse.zoo.presentation.feeding.dto;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

public class FeedingScheduleDto {
    private UUID uid;
    private UUID animalUid;
    private int intervalDays;
    private LocalTime mealtime;
    private boolean enabled;
    private LocalDateTime nextTriggerTime;

    public UUID getUid() {
        return uid;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public UUID getAnimalUid() {
        return animalUid;
    }

    public void setAnimalUid(UUID animalUid) {
        this.animalUid = animalUid;
    }

    public int getIntervalDays() {
        return intervalDays;
    }

    public void setIntervalDays(int intervalDays) {
        this.intervalDays = intervalDays;
    }

    public LocalTime getMealtime() {
        return mealtime;
    }

    public void setMealtime(LocalTime mealtime) {
        this.mealtime = mealtime;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public LocalDateTime getNextTriggerTime() {
        return nextTriggerTime;
    }

    public void setNextTriggerTime(LocalDateTime nextTriggerTime) {
        this.nextTriggerTime = nextTriggerTime;
    }
}
