package com.amit.languagetest;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends BaseActivity {

    TextView mText;
    DataBaseHelper dataBaseHelper;
    String language = "en";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        dataBaseHelper = new DataBaseHelper(this);
        language = dataBaseHelper.getLanguageCode("1");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mText = findViewById(R.id.m_tv);
        findViewById(R.id.m_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (language.equalsIgnoreCase("en")) {
                    dataBaseHelper.insertVideos("1", "hi");
                } else if (language.equalsIgnoreCase("hi")) {
                    dataBaseHelper.insertVideos("1", "en");
                }
                BaseActivity.getInstance().appCreate(); //TODO you can resart app
            }
        });
    }

}