package com.simtig.view.highlight;

import android.graphics.Color;

import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by hieuxit on 5/20/16.
 */
public class ColorParser {
    /**
     * Parse the color string, and return the corresponding color-int.
     * If the string cannot be parsed, throws an IllegalArgumentException
     * exception. Supported formats are:
     * #RGB ~ #RRGGBB
     * #ARGB ~ #AARRGGBB
     * #RRGGBB
     * #AARRGGBB
     * or one of the following names:
     * 'red', 'blue', 'green', 'black', 'white', 'gray', 'cyan', 'magenta',
     * 'yellow', 'lightgray', 'darkgray', 'grey', 'lightgrey', 'darkgrey',
     * 'aqua', 'fuchsia', 'lime', 'maroon', 'navy', 'olive', 'purple',
     * 'silver', 'teal'.
     */
    public static int parse(String colorString) {
        if (colorString.charAt(0) == '#') {

            String hexa = colorString.substring(1);

            int a, r, g, b;
            if (hexa.length() == 3) {
                r = Integer.parseInt(hexa.substring(0, 1), 16);
                g = Integer.parseInt(hexa.substring(1, 2), 16);
                b = Integer.parseInt(hexa.substring(2, 3), 16);
                return 0xff000000 | r << 20 | r << 16 | g << 12 | g << 8 | b << 4 | b;
            }
            if (hexa.length() == 4) {
                a = Integer.parseInt(hexa.substring(0, 1), 16);
                r = Integer.parseInt(hexa.substring(1, 2), 16);
                g = Integer.parseInt(hexa.substring(2, 3), 16);
                b = Integer.parseInt(hexa.substring(3, 4), 16);
                return a << 28 | a << 24 | r << 20 | r << 16 | g << 12 | g << 8 | b << 4 | b;
            }

            if (hexa.length() == 6) {
                return 0xff000000 | Integer.parseInt(hexa, 16);
            }

            if (hexa.length() == 8) {
                return Integer.parseInt(hexa, 16);
            }

            throw new IllegalArgumentException("color must be one of #rgb, #argb, #rrggbb, #aarrggbb");
        } else {
            Integer color = sColorNameMap.get(colorString.toLowerCase(Locale.ROOT));
            if (color != null) {
                return color;
            }
        }
        throw new IllegalArgumentException("Unknown color");
    }

    private static final Map<String, Integer> sColorNameMap;

    static {
        sColorNameMap = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        sColorNameMap.put("black", Color.BLACK);
        sColorNameMap.put("darkgray", Color.DKGRAY);
        sColorNameMap.put("gray", Color.GRAY);
        sColorNameMap.put("lightgray", Color.LTGRAY);
        sColorNameMap.put("white", Color.WHITE);
        sColorNameMap.put("red", Color.RED);
        sColorNameMap.put("green", Color.GREEN);
        sColorNameMap.put("blue", Color.BLUE);
        sColorNameMap.put("yellow", Color.YELLOW);
        sColorNameMap.put("cyan", Color.CYAN);
        sColorNameMap.put("magenta", Color.MAGENTA);
        sColorNameMap.put("aqua", 0xFF00FFFF);
        sColorNameMap.put("fuchsia", 0xFFFF00FF);
        sColorNameMap.put("darkgrey", Color.DKGRAY);
        sColorNameMap.put("grey", Color.GRAY);
        sColorNameMap.put("lightgrey", Color.LTGRAY);
        sColorNameMap.put("lime", 0xFF00FF00);
        sColorNameMap.put("maroon", 0xFF800000);
        sColorNameMap.put("navy", 0xFF000080);
        sColorNameMap.put("olive", 0xFF808000);
        sColorNameMap.put("purple", 0xFF800080);
        sColorNameMap.put("silver", 0xFFC0C0C0);
        sColorNameMap.put("teal", 0xFF008080);

    }
}
