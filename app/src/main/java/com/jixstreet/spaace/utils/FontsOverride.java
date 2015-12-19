package com.jixstreet.spaace.utils;

/**
 * Created by M Agung Satrio - agung.satrio@inmagine.com on 8/12/2015.
 * Override all font in application
 */

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class FontsOverride {
    private static final String DEFAULT_NORMAL_BOLD_FONT_FILENAME = "brandon_bold.otf";
    private static final String DEFAULT_NORMAL_BOLD_ITALIC_FONT_FILENAME = "brandon_regular.otf";
    private static final String DEFAULT_NORMAL_ITALIC_FONT_FILENAME = "brandon_medium.otf";
    private static final String DEFAULT_NORMAL_NORMAL_FONT_FILENAME = "brandon_regular.otf";

    private static Map<String, Typeface> TYPEFACE = new HashMap<>();

    public static void overrideFonts(Context context) {
        try {
            FontsOverride.setDefaultFonts(context);
        } catch (NoSuchFieldException e) {
            FontsOverride.logFontError(e);
        } catch (IllegalAccessException e) {
            FontsOverride.logFontError(e);
        } catch (Throwable e) {
            FontsOverride.logFontError(e);
        }
    }

    public static void setDefaultFonts(Context context) throws NoSuchFieldException, IllegalAccessException {
        final Typeface bold = getFonts(context, DEFAULT_NORMAL_BOLD_FONT_FILENAME);
        final Typeface italic = getFonts(context, DEFAULT_NORMAL_ITALIC_FONT_FILENAME);
        final Typeface boldItalic = getFonts(context, DEFAULT_NORMAL_BOLD_ITALIC_FONT_FILENAME);
        final Typeface normal = getFonts(context, DEFAULT_NORMAL_NORMAL_FONT_FILENAME);

        Field defaultField = Typeface.class.getDeclaredField("DEFAULT");
        defaultField.setAccessible(true);
        defaultField.set(null, normal);

        Field defaultBoldField = Typeface.class.getDeclaredField("DEFAULT_BOLD");
        defaultBoldField.setAccessible(true);
        defaultBoldField.set(null, bold);

        Field sDefaults = Typeface.class.getDeclaredField("sDefaults");
        sDefaults.setAccessible(true);
        sDefaults.set(null, new Typeface[]{normal, bold, italic, boldItalic});

    }

    public static void logFontError(Throwable e) {
        Log.e("font_override", "Error overriding font", e);
    }

    public static Typeface getFonts(Context context, String name) {
        Typeface typeface = TYPEFACE.get(name);
        if (typeface == null) {
            typeface = Typeface.createFromAsset(context.getAssets(), "fonts/" + name);
            TYPEFACE.put(name, typeface);
        }
        return typeface;
    }
}