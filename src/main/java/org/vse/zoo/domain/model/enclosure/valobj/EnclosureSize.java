package org.vse.zoo.domain.model.enclosure.valobj;

import org.jetbrains.annotations.NotNull;
import org.vse.zoo.application.utils.Asserts;
import org.vse.zoo.domain.shared.ValueObject;

import java.time.Instant;

public class EnclosureSize implements ValueObject<EnclosureSize> {
    private final int height;
    private final int width;
    private final int length;
    private final Instant updateTime;

    private EnclosureSize(Builder b) {
        height = b.height;
        width = b.width;
        length = b.length;
        updateTime = b.updateTime;
    }

    @NotNull
    @Override
    public EnclosureSize valid() {
        Asserts.ensure(height, x -> x > 0, "height");
        Asserts.ensure(width, x -> x > 0, "width");
        Asserts.ensure(length, x -> x > 0, "height");
        Asserts.notNull(updateTime, "updateTime");
        return this;
    }

    @Override
    public Instant getUpdateTime() {
        return updateTime;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getLength() {
        return length;
    }

    public static class Builder {
        private int height;
        private int width;
        private int length;
        private Instant updateTime;

        public EnclosureSize build() {
            return new EnclosureSize(this);
        }

        public Builder setHeight(int height) {
            this.height = height;
            return this;
        }

        public Builder setWidth(int width) {
            this.width = width;
            return this;
        }

        public Builder setLength(int length) {
            this.length = length;
            return this;
        }

        public Builder setUpdateTime(Instant updateTime) {
            this.updateTime = updateTime;
            return this;
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return builder()
                .setLength(length)
                .setWidth(width)
                .setHeight(height)
                .setUpdateTime(updateTime);
    }
}
