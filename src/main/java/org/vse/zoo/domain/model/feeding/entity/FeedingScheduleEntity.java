package org.vse.zoo.domain.model.feeding.entity;

import org.jetbrains.annotations.NotNull;
import org.vse.zoo.domain.model.feeding.valobj.Recipient;
import org.vse.zoo.domain.model.feeding.valobj.Schedule;
import org.vse.zoo.domain.model.feeding.valobj.Timer;
import org.vse.zoo.domain.shared.DomainEntity;

import java.util.UUID;

public class FeedingScheduleEntity implements DomainEntity {
    @NotNull
    private final UUID uid;
    @NotNull
    private final Recipient recipient;
    @NotNull
    private final Schedule schedule;
    @NotNull
    private final Timer timer;

    public FeedingScheduleEntity(@NotNull UUID uid,
                                 @NotNull Recipient recipient,
                                 @NotNull Schedule schedule,
                                 @NotNull Timer timer) {
        this.uid = uid;
        this.recipient = recipient;
        this.schedule = schedule;
        this.timer = timer;
    }

    @NotNull
    @Override
    public UUID getUid() {
        return uid;
    }

    @NotNull
    public Recipient getRecipient() {
        return recipient;
    }

    @NotNull
    public Schedule getSchedule() {
        return schedule;
    }

    @NotNull
    public Timer getTimer() {
        return timer;
    }
}
