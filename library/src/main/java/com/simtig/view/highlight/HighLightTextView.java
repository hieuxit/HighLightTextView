package com.simtig.view.highlight;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.simtig.view.R;

import java.util.List;

/**
 * TextView support highlight, clickable a part of TextView. You do not need handle it by code using Spannable, or Html.fromHtml.
 * Just input into 'tag_text' field on xml layout or using method {@link #setHighlightText(String)}
 * <p>
 * The text tag must be define well form.
 * <ul>
 * <li>In xml it's must be define in a {@code <![CDATA[ ]]>} tag. Because if no, android remove xml tag when read it from xml</li>
 * <li>Text input contains normal text and highlight text - that cover on a <b>'tag'</b> xml like
 * <quote>{@code <![CDATA[ This is normal text with <tag>highlight text</tag> ]]>}</quote>
 * </li>
 * <li>The supported attribute on <b>tag</b> to style the highlight text is:</li>
 * <ul>
 * <li>color e.g color = "#ff00ff" or color = "#ff00ffff" (Supported color with start with '#' and next 6 or 8 hexa characters )</li>
 * <li>size e.g size = "14sp" or size = "14dp" (Supported dimension type: dp, dip, px, pt, in, mm)</li>
 * <li>background e.g same as color.Only color supported </li>
 * <li>style e.g style="bold|italic|underline" </li>
 * </ul>
 * </li>
 * </ul>
 */
public class HighLightTextView extends TextView {

    private String mHighlightText;
    private OnTagClickListener mTagClickListener;

    public HighLightTextView(Context context) {
        this(context, null);
    }

    public HighLightTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HighLightTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.HighLightTextView,
                    defStyleAttr, 0);
            mHighlightText = a.getString(R.styleable.HighLightTextView_highlightText);
            a.recycle();
        }
        setHighlightText(mHighlightText);
        setMovementMethod(LinkMovementMethod.getInstance());
    }

    public void setHighlightText(@StringRes int resId) {
        String highlightText = getResources().getString(resId);
        setHighlightText(highlightText);
    }

    public void setHighlightText(String highlightText) {
        this.mHighlightText = highlightText;
        List<Tag> tagList = TagParser.parse(highlightText, getResources().getDisplayMetrics());
        if (tagList == null) {
            setText(null);
            return;
        }
        Resources res = getResources();
        SpannableStringBuilder builder = new SpannableStringBuilder();
        int tagCount = tagList.size();
        int tagIndex = 0;
        for (int i = 0; i < tagCount; i++) {
            Tag tag = tagList.get(i);
            Tag.Builder tagBuilder = tag.getBuilder();

            // read from builder
            String text = !TextUtils.isEmpty(tagBuilder.text) ? tagBuilder.text : (tagBuilder.textId > 0 ?
                    res.getString(tagBuilder.textId) : "");

            float textSize = tagBuilder.textSize > 0 ? tagBuilder.textSize : (tagBuilder.textSizeId > 0 ?
                    res.getDimensionPixelSize(tagBuilder.textSizeId) : getTextSize());

            int textColor = tagBuilder.textColor != null ? tagBuilder.textColor : (tagBuilder.textColorId > 0 ?
                    ContextCompat.getColor(getContext(), tagBuilder.textColorId) : getCurrentTextColor());

            int backgroundColor = tagBuilder.backgroundColor != null ? tagBuilder.backgroundColor : (tagBuilder.backgroundColorId > 0 ?
                    ContextCompat.getColor(getContext(), tagBuilder.backgroundColorId) : 0);


            if (!tagBuilder.highlight) {
                builder.append(text);
            } else {
                Spannable tagSpannable = new SpannableString(text);

                tagSpannable.setSpan(new NoCustomizeClickableSpan(tag, tagIndex) {
                    @Override
                    public void onClick(View widget) {
                        if (mTagClickListener != null) {
                            mTagClickListener.onTagClick(widget, getTag(), getPosition());
                        }
                    }
                }, 0, tagSpannable.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                tagSpannable.setSpan(new TagSpan(textSize, textColor, tagBuilder.bold,
                                tagBuilder.italic, tagBuilder.underline, backgroundColor),
                        0, tagSpannable.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                builder.append(tagSpannable);
                tagIndex++;
            }
        }
        setText(builder);
    }

    public void setTagClickListener(OnTagClickListener mTagClickListener) {
        this.mTagClickListener = mTagClickListener;
    }

    public interface OnTagClickListener {
        void onTagClick(View view, Tag tag, int position);
    }
}
