package com.example.android.soccernews;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;


public class MainSportsNewsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sports_news_activity_main);


        ViewPager viewPager = findViewById(R.id.viewpager);


        SimpleFragmentPagerAdapter adapter = new SimpleFragmentPagerAdapter(getSupportFragmentManager());


        viewPager.setAdapter(adapter);


        TabLayout tabLayout = findViewById(R.id.sliding_tabs);


        tabLayout.setupWithViewPager(viewPager);


    }


}
