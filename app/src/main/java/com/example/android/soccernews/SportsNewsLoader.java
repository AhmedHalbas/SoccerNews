package com.example.android.soccernews;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

import java.util.ArrayList;

public class SportsNewsLoader extends AsyncTaskLoader<ArrayList<SportsNews>> {

    String mURL;



    public SportsNewsLoader(@NonNull Context context, String URL) {
        super(context);

        mURL = URL;

    }


    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Nullable
    @Override

    public ArrayList<SportsNews> loadInBackground() {


        if (mURL == null) {
            return null;
        }



        ArrayList<SportsNews> result = QueryUtils.fetchSportsNewsData(mURL);
        return result;


    }
}
