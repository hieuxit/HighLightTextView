package io.fruitful.highlightext;

import android.text.TextPaint;
import android.text.style.ClickableSpan;

/**
 * Created by hieuxit on 10/7/15.
 */
public abstract class NoCustomizeClickableSpan extends ClickableSpan {

    private Tag tag;
    private int position;

    public NoCustomizeClickableSpan(Tag tag, int position) {
        this.tag = tag;
        this.position = position;
    }

    public Tag getTag() {
        return tag;
    }

    public int getPosition() {
        return position;
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        // do not call super.updateDrawState to mark text underline
    }
}