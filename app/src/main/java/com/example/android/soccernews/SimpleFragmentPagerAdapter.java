package com.example.android.soccernews;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {

    public SimpleFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }


    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new TalkSportFragment();
        } else if (position == 1) {
            return new TheSportBibleFragment();
        } else if (position == 2) {
            return new BBCSportFragment();
        } else {
            return new GuardianSportFragment();
        }


    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        switch (position) {
            case 0:
                return "Talk Sport";

            case 1:
                return "The Sport Bible";

            case 2:
                return "BBC Sport";

            case 3:
                return "Guardian Sport";

            default:
                return null;

        }
    }
}