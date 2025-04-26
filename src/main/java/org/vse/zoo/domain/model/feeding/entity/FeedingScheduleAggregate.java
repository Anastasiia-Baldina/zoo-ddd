package org.vse.zoo.domain.model.feeding.entity;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.vse.zoo.application.exception.BusinessLogicException;
import org.vse.zoo.application.utils.Asserts;
import org.vse.zoo.domain.model.feeding.valobj.Recipient;
import org.vse.zoo.domain.model.feeding.valobj.Schedule;
import org.vse.zoo.domain.model.feeding.valobj.Timer;
import org.vse.zoo.domain.shared.EntityAggregate;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

public class FeedingScheduleAggregate implements EntityAggregate<FeedingSchedule> {
    @NotNull
    private final UUID uid;
    @Nullable
    private Recipient recipient;
    @Nullable
    private Schedule schedule;
    @Nullable
    private Timer timer;

    public FeedingScheduleAggregate(UUID uid) {
        this.uid = Asserts.notNull(uid, "uid");
    }

    public FeedingScheduleAggregate(FeedingSchedule entity) {
        Asserts.notNull(entity, "entity");
        uid = entity.getUid();
        recipient = entity.getRecipient();
        schedule = entity.getSchedule();
        timer = entity.getTimer();
    }

    @NotNull
    @Override
    public FeedingSchedule buildRootEntity() {
        return new FeedingSchedule(
                uid,
                Asserts.notNull(recipient, "recipient"),
                Asserts.notNull(schedule, "schedule"),
                Asserts.notNull(timer, "trigger"));
    }

    public FeedingScheduleAggregate setRecipient(Recipient recipient) {
        this.recipient = recipient.toBuilder()
                .setUpdateTime(Instant.now())
                .build()
                .valid();
        return this;
    }

    public FeedingScheduleAggregate setSchedule(Schedule schedule) {
        this.schedule = schedule.toBuilder()
                .setUpdateTime(Instant.now())
                .build()
                .valid();
        return this;
    }

    public FeedingScheduleAggregate setTimer(Timer timer) {
        this.timer = timer.toBuilder()
                .setUpdateTime(Instant.now())
                .build()
                .valid();
        return this;
    }

    public boolean checkFeeding(LocalDateTime currentDateTime) {
        if (schedule == null) {
            throw new BusinessLogicException("Не задано расписание кормления животного");
        }
        if (timer == null) {
            throw new BusinessLogicException("Не задан таймер кормления животного");
        }
        if (!timer.isEnabled()) {
            throw new BusinessLogicException("Данное животное запрещено кормить");
        }

        if (timer.getNextTriggerTime() == null) {
            var curTime = currentDateTime.toLocalTime();
            if (curTime.isBefore(schedule.getMealtime())) {
                return false;
            }
            var nextTriggerTime = LocalDateTime.of(
                    currentDateTime.toLocalDate().plusDays(schedule.getIntervalDays()),
                    schedule.getMealtime());
            timer = timer.toBuilder()
                    .setNextTriggerTime(nextTriggerTime)
                    .setUpdateTime(Instant.now())
                    .build();
            return true;
        } else if (timer.getNextTriggerTime().isBefore(currentDateTime)) {
            var nextTriggerTime = LocalDateTime.of(
                    timer.getNextTriggerTime().toLocalDate().plusDays(schedule.getIntervalDays()),
                    schedule.getMealtime());
            timer = timer.toBuilder()
                    .setNextTriggerTime(nextTriggerTime)
                    .setUpdateTime(Instant.now())
                    .build();
            return true;
        }
        return false;
    }

    public FeedingScheduleAggregate cancelFeeding() {
        if (timer == null) {
            throw new BusinessLogicException("Не задан таймер кормления животного");
        }
        timer = timer.toBuilder()
                .setEnabled(false)
                .setUpdateTime(Instant.now())
                .build();
        return this;
    }

    public FeedingScheduleAggregate enableFeeding() {
        if (timer == null) {
            throw new BusinessLogicException("Не задан таймер кормления животного");
        }
        timer = timer.toBuilder()
                .setEnabled(true)
                .setUpdateTime(Instant.now())
                .build();
        return this;
    }
}

