package com.example.android.soccernews;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class SportsNewsAdapter extends ArrayAdapter<SportsNews> {


    public SportsNewsAdapter(Context context, ArrayList<SportsNews> earthquakes) {
        super(context, 0, earthquakes);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;

        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.sports_news_list_item, parent, false);
        }


        ImageView newsImage = listItemView.findViewById(R.id.news_image);
        TextView newsTitle = listItemView.findViewById(R.id.news_title);
        TextView newsDate = listItemView.findViewById(R.id.news_date);
        TextView articleAuthor = listItemView.findViewById(R.id.article_author);
        TextView newsSourceName = listItemView.findViewById(R.id.news_source_name);
        ImageButton shareImageButton = listItemView.findViewById(R.id.share);


        final SportsNews currentSportsNews = getItem(position);


        Glide.with(getContext()).load(currentSportsNews.getImageURL()).fitCenter().into(newsImage);
        newsTitle.setText(currentSportsNews.getTitle());
        newsDate.setText(currentSportsNews.getDate());
        newsSourceName.setText(currentSportsNews.getSourceName());

        if (currentSportsNews.getAuthorName().contains("href") || currentSportsNews.getAuthorName().contains("null")) {
            articleAuthor.setVisibility(View.GONE);
        } else {
            articleAuthor.setText("Author: " + currentSportsNews.getAuthorName());
        }


        shareImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = currentSportsNews.getURL();
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                getContext().startActivity(Intent.createChooser(sharingIntent, "Share via"));
            }
        });

        return listItemView;
    }
}
