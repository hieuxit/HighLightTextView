package io.fruitful.highlightext;

import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.StringRes;

/**
 * Created by hieuxit on 10/6/15.
 */
public class Tag {

    public static final int NOT_SET_INTEGER = 0;

    private Builder builder;

    Builder getBuilder() {
        return builder;
    }

    /**
     * Hide it
     */
    private Tag() {
    }

    public static class Builder {

        int id;
        String text;
        @StringRes
        int textId;
        boolean bold, italic, underline;
        Integer textColor; // null for not set and value for text color
        @ColorRes
        int textColorId;
        Integer backgroundColor; // null for not set and value for background color
        @ColorRes
        int backgroundColorId;
        float textSize;
        @DimenRes
        int textSizeId;

        boolean highlight;

        /**
         * Set tag id for identify tag later (click on tag)
         */
        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder text(String text) {
            this.text = text;
            return this;
        }

        /**
         * Make sure you do not call {@link #text(String)} before, because it overrides {@link #text(int)}
         */
        public Builder text(@StringRes int resId) {
            this.textId = resId;
            return this;
        }

        public Builder bold(boolean bold) {
            this.bold = bold;
            if (bold) {
                flagAsHighLight();
            }
            return this;
        }

        public Builder italic(boolean italic) {
            this.italic = italic;
            if (italic) {
                flagAsHighLight();
            }
            return this;
        }

        public Builder underline(boolean underline) {
            this.underline = underline;
            if (underline) {
                flagAsHighLight();
            }
            return this;
        }

        public Builder textColor(int textColor) {
            this.textColor = textColor;
            flagAsHighLight();
            return this;
        }

        /**
         * Make sure you do not call {@link #textColor(int)} before, because it overrides {@link #textColorId(int)}
         */
        public Builder textColorId(@ColorRes int textColorId) {
            this.textColorId = textColorId;
            flagAsHighLight();
            return this;
        }

        public Builder backgroundColor(int backgroundColor) {
            this.backgroundColor = backgroundColor;
            flagAsHighLight();
            return this;
        }

        /**
         * Make sure you do not call {@link #backgroundColor(int)} before, because it overrides {@link #backgroundColorId(int)}
         */
        public Builder backgroundColorId(@ColorRes int backgroundColorId) {
            this.backgroundColorId = backgroundColorId;
            flagAsHighLight();
            return this;
        }

        public Builder textSize(float size) {
            this.textSize = size;
            flagAsHighLight();
            return this;
        }

        /**
         * Make sure you do not call {@link #textSize(float)} before, because it overrides {@link #textSizeId(int)}
         */
        public Builder textSizeId(@DimenRes int textSizeId) {
            this.textSizeId = textSizeId;
            flagAsHighLight();
            return this;
        }

        /**
         * Whenever user call one of method to make a highlight text
         * (except {@link #text(String)}, {@link #text(int)}, {@link #id(int)}
         * toggle this flag to {@code true}
         */
        private void flagAsHighLight() {
            if (!highlight) {
                highlight = true;
            }
        }

        public Tag build() {
            Tag tag = new Tag();
            tag.builder = this;
            return tag;
        }
    }
}
