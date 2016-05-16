package com.simtig.view.highlight;

import android.text.TextPaint;
import android.text.style.MetricAffectingSpan;

/**
 * Created by hieuxit on 10/7/15.
 */
public class TagSpan extends MetricAffectingSpan {

    private float textSize;
    private int textColor;
    private boolean bold;
    private boolean italic;
    private boolean underline;
    private int backgroundColor;

    @Override
    public void updateMeasureState(TextPaint p) {
        apply(p, textSize, textColor, bold, italic, underline, backgroundColor);
    }

    @Override
    public void updateDrawState(TextPaint tp) {
        apply(tp, textSize, textColor, bold, italic, underline, backgroundColor);
    }

    private static void apply(TextPaint paint, float textSize, int textColor, boolean bold,
                              boolean italic, boolean underline, int backgroundColor) {
        if (textSize != Tag.NOT_SET_INTEGER) {
            paint.setTextSize(textSize);
        }

        if (textColor != Tag.NOT_SET_INTEGER) {
            paint.setColor(textColor);
        }

        if (bold) {
            paint.setFakeBoldText(true);
        }

        if (italic) {
            paint.setTextSkewX(-0.25f);
        }

        if (underline) {
            paint.setUnderlineText(true);
        }

        if (backgroundColor != Tag.NOT_SET_INTEGER) {
            paint.bgColor = backgroundColor;
        }
    }

    public TagSpan(float textSize, int textColor, boolean bold, boolean italic, boolean underline, int backgroundColor) {
        this.textSize = textSize;
        this.textColor = textColor;
        this.bold = bold;
        this.italic = italic;
        this.underline = underline;
        this.backgroundColor = backgroundColor;
    }

    @Override
    public String toString() {
        return "TagSpan{" +
                "textSize=" + textSize +
                ", textColor=" + textColor +
                ", bold=" + bold +
                ", italic=" + italic +
                ", underline=" + underline +
                ", backgroundColor=" + backgroundColor +
                '}';
    }
}
