package com.amit.languagetest;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.util.Locale;

public class BaseActivity extends AppCompatActivity {

    private DataBaseHelper dataBaseHelper;
    private String language = "en";

    private static BaseActivity instance;

    public BaseActivity() {
        instance = this;
        LocaleUtils.updateConfig(this);
    }


    public static BaseActivity getInstance() {
        return instance;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        dataBaseHelper = new DataBaseHelper(this);
        language = dataBaseHelper.getLanguageCode("1");
        Locale.setDefault(new Locale(language));
        Configuration config = new Configuration();
        config.locale = new Locale(language);
        Resources resources = getResources();
        resources.updateConfiguration(config, resources.getDisplayMetrics());
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        LocaleUtils.updateConfig(MyApplication.getInstance(), newConfig);
    }

    public void appCreate() {
        recreate();
    }
}