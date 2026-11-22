package tn.isim.monbudget.util;

import android.content.Context;

public class PrefsManager {
    private static final String PREFS = "monbudget_prefs";
    private static final String KEY_NAME = "user_name";
    private static final String KEY_DARK = "dark_theme";

    public static void setUserName(Context c, String n) {
        c.getSharedPreferences(PREFS, Context.MODE_PRIVATE).edit().putString(KEY_NAME, n).apply();
    }
    public static String getUserName(Context c) {
        return c.getSharedPreferences(PREFS, Context.MODE_PRIVATE).getString(KEY_NAME, "Étudiant");
    }
    public static void setDark(Context c, boolean d) {
        c.getSharedPreferences(PREFS, Context.MODE_PRIVATE).edit().putBoolean(KEY_DARK, d).apply();
    }
    public static boolean isDark(Context c) {
        return c.getSharedPreferences(PREFS, Context.MODE_PRIVATE).getBoolean(KEY_DARK, false);
    }
}