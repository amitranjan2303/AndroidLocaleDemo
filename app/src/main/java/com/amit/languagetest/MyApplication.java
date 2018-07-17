package com.amit.languagetest;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.preference.PreferenceManager;

import java.util.Locale;

public class MyApplication extends Application {

    private static MyApplication myApplication;
    DataBaseHelper dataBaseHelper;

    public void onCreate() {
        super.onCreate();
        myApplication = this;
        dataBaseHelper = new DataBaseHelper(this);
        String language = dataBaseHelper.getLanguageCode("1");
        LocaleUtils.setLocale(new Locale(language));
        LocaleUtils.updateConfig(this, getBaseContext().getResources().getConfiguration());
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        LocaleUtils.updateConfig(this, newConfig);
    }

    public static MyApplication getInstance() {
        return myApplication;
    }

    public static class LocaleHelper {
        private static final String SELECT_LANGUAGE = "Locale.Helper.Selected.Language";

        public static Context onAttach(Context context) {
            String lang = getPersistedData(context, Locale.getDefault().getLanguage());
            return setLocale(context, lang);
        }

        public static Context onAttach(Context context, String defaultLanguage) {
            String lang = getPersistedData(context, Locale.getDefault().getLanguage());
            return setLocale(context, defaultLanguage);
        }

        public static Context setLocale(Context context, String lang) {
            persist(context, lang);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                return updateResources(context, lang);
            }
            return updateResourcesLegacy(context, lang);
        }

        @SuppressWarnings("deprecation")
        private static Context updateResourcesLegacy(Context context, String lang) {
            Locale locale = new Locale(lang);
            Locale.setDefault(locale);
            Resources resources = context.getResources();
            Configuration config = resources.getConfiguration();
            config.locale = locale;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                config.setLayoutDirection(locale);
            }
            resources.updateConfiguration(config, resources.getDisplayMetrics());
            return context;
        }

        @TargetApi(Build.VERSION_CODES.N)
        private static Context updateResources(Context context, String lang) {
            Locale locale = new Locale(lang);
            Locale.setDefault(locale);
            Configuration config = context.getResources().getConfiguration();
            config.setLocale(locale);
            config.setLayoutDirection(locale);
            return context.createConfigurationContext(config);
        }

        private static void persist(Context context, String lang) {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(SELECT_LANGUAGE, lang);
            editor.apply();
        }

        private static String getPersistedData(Context context, String language) {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
            return preferences.getString(SELECT_LANGUAGE, language);
        }
    }
}
