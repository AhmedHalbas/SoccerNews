package com.example.android.soccernews;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

public class TheSportBibleFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<SportsNews>> {

    private static final String THESPORTSBIBLE_REQUEST_URL =


            "https://newsapi.org/v2/top-headlines?sources=the-sport-bible&sortBy=publishedAt&pageSize=15&apiKey=3d5ac9894af14a73a79ec7019e027a42";


    private TextView mEmptyStateTextView;
    private ProgressBar progressBar;
    private View rootView;
    private SwipeRefreshLayout pullToRefresh;
    private SportsNewsAdapter sportsNewsAdapter;
    private ListView sportsnewsListView;


    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.sports_news_list_view, container, false);


        mEmptyStateTextView = rootView.findViewById(R.id.empty_view);
        progressBar = rootView.findViewById(R.id.progress_bar);
        pullToRefresh = rootView.findViewById(R.id.pullToRefresh);


        if (!isNetworkConnected() || !isOnline()) {

            mEmptyStateTextView.setText("NO INTERNET CONNECTION");
            progressBar.setVisibility(View.GONE);

        } else {
            progressBar.setVisibility(View.VISIBLE);
            android.support.v4.app.LoaderManager.getInstance(this).initLoader(5, null, this);
        }


        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                if (!isNetworkConnected() || !isOnline()) {
                    mEmptyStateTextView.setText("NO INTERNET CONNECTION");
                    progressBar.setVisibility(View.GONE);

                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    mEmptyStateTextView.setVisibility(View.GONE);
                    android.support.v4.app.LoaderManager.getInstance(getActivity()).initLoader(1, null, TheSportBibleFragment.this);
                }


                pullToRefresh.setRefreshing(false);
            }
        });


        return rootView;
    }

    @NonNull
    @Override
    public Loader<ArrayList<SportsNews>> onCreateLoader(int i, @Nullable Bundle bundle) {
        return new SportsNewsLoader(getActivity(), THESPORTSBIBLE_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<SportsNews>> loader, ArrayList<SportsNews> sportsNews) {


        if (!isNetworkConnected() || !isOnline()) {
            mEmptyStateTextView.setText("NO INTERNET CONNECTION");
            sportsnewsListView.setEmptyView(mEmptyStateTextView);
            sportsnewsListView.setAdapter(null);
            progressBar.setVisibility(View.GONE);

        }

        if(sportsNews==null)
        {
            mEmptyStateTextView.setText("No Data Found");
            progressBar.setVisibility(View.GONE);
        }


        else {
            UpdateUI(sportsNews);
        }


    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<SportsNews>> loader) {

        sportsNewsAdapter.clear();
    }


    private void UpdateUI(final ArrayList<SportsNews> sportsNews)

    {

        try {


            progressBar.setVisibility(View.GONE);

            sportsnewsListView = rootView.findViewById(R.id.list);


            sportsNewsAdapter = new SportsNewsAdapter(getActivity(), sportsNews);


            sportsnewsListView.setAdapter(sportsNewsAdapter);


            sportsnewsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    SportsNews currentSportsNews = sportsNews.get(i);

                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(currentSportsNews.getURL()));
                    startActivity(intent);


                }
            });

        } catch (NullPointerException e) {
            Toast.makeText(getContext(), "Something is going wrong, please try again!", Toast.LENGTH_LONG).show();
        }


    }


    private boolean isNetworkConnected() {

        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnected();

        return isConnected;

    }


    public boolean isOnline() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return false;
    }
}
