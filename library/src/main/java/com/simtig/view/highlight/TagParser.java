package com.simtig.view.highlight;

import android.text.TextUtils;
import android.util.DisplayMetrics;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by hieuxit on 10/6/15.
 */
public class TagParser {
    // Pattern for tag
    private static final Pattern TAG_PATTERN = Pattern.compile("<tag\\b[^>]*>(.*?)</tag>");

    public static List<Tag> parse(String input, DisplayMetrics dm) {

        if (TextUtils.isEmpty(input)) return null;
        Matcher matcher = TAG_PATTERN.matcher(input);
        List<Tag> result = new ArrayList<>();

        int start = 0;
        int end = input.length();

        while (matcher.find()) {
            int matcherStart = matcher.start();
            int matcherEnd = matcher.end();
            if (start < matcherStart) {
                // add a normal tag
                Tag tag = new Tag.Builder().text(input.substring(start, matcherStart))
                        .build();
                result.add(tag);
            }

            try {
                Tag tag = tagDeserialize(input.substring(matcherStart, matcherEnd), dm);
                result.add(tag);
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            start = matcherEnd;
        }
        return result;
    }

    private static Tag tagDeserialize(String xml, DisplayMetrics dm) throws XmlPullParserException, IOException {
        StringReader reader = null;
        String ns = null;
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser parser = factory.newPullParser();
            reader = new StringReader(xml);
            parser.setInput(reader);
            parser.nextTag();
            parser.require(XmlPullParser.START_TAG, ns, "tag");

            // get color attribute
            String colorString = parser.getAttributeValue(ns, "color");
            Integer color = null;
            if (!TextUtils.isEmpty(colorString)) {
                color = ColorParser.parse(colorString);
            }

            // get background color attribute
            String backgroundString = parser.getAttributeValue(ns, "background");
            Integer background = null;
            if (!TextUtils.isEmpty(backgroundString)) {
                background = ColorParser.parse(backgroundString);
            }

            // get text size attribute
            int size = Tag.NOT_SET_INTEGER;
            String sizeString = parser.getAttributeValue(ns, "size");
            if (!TextUtils.isEmpty(sizeString)) {
                size = DimensionConverter.stringToDimensionPixelSize(sizeString, dm);
            }

            // get text style attribute
            String styleString = parser.getAttributeValue(ns, "style");
            boolean bold = false, italic = false, underline = false;
            if (!TextUtils.isEmpty(styleString)) {
                String[] styleParts = styleString.trim().split("\\s*\\|\\s*");
                for (int i = styleParts.length - 1; i >= 0; i--) {
                    if ("bold".equalsIgnoreCase(styleParts[i])) {
                        bold = true;
                    } else if ("italic".equalsIgnoreCase(styleParts[i])) {
                        italic = true;
                    } else if ("underline".equalsIgnoreCase(styleParts[i])) {
                        underline = true;
                    }
                }
            }

            String text = parser.nextText();
            Tag.Builder builder = new Tag.Builder()
                    .text(text)
                    .textSize(size)
                    .bold(bold)
                    .italic(italic)
                    .underline(underline);
            if (color != null) {
                builder.textColor(color.intValue());
            }
            if (background != null) {
                builder.backgroundColor(background.intValue());
            }
            return builder.build();
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }

}