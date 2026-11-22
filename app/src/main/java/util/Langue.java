package tn.isim.monbudget.util;

import android.content.Context;

public class Langue {
    public static String t(Context c, String fr, String en) {
        // Récupère la langue du téléphone
        String lang = c.getResources().getConfiguration().locale.getLanguage();
        if (lang.equals("en")) {
            return en;
        }
        return fr; // Par défaut : français
    }
}