package com.example.android.soccernews;

public class SportsNews {

    private String mImageURL;
    private String mTitle;
    private String mDate;
    private String mSourceName;
    private String mURL;
    private String mAuthorName;

    public SportsNews(String ImageURL, String Title, String Date, String Source, String URL, String Author) {
        this.mImageURL = ImageURL;
        this.mTitle = Title;
        this.mDate = Date;
        this.mSourceName = Source;
        this.mURL = URL;
        this.mAuthorName = Author;
    }



    public String getImageURL() {
        return mImageURL;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getDate() {
        return mDate;
    }


    public String getSourceName() {
        return mSourceName;
    }


    public String getURL() {
        return mURL;
    }


    public String getAuthorName() {
        return mAuthorName;
    }
}
