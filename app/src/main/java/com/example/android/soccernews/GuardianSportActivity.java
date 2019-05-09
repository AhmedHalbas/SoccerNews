package com.example.android.soccernews;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public class GuardianSportActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sport_news_category);

        getSupportFragmentManager().beginTransaction().replace(R.id.container, new GuardianSportFragment()).commit();
    }
}
