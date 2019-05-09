package com.example.android.soccernews;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class QueryUtils {

    public static final String LOG_TAG = QueryUtils.class.getSimpleName();


    private QueryUtils() {
    }

    public static ArrayList<SportsNews> fetchSportsNewsData(String requestUrl) {

        URL url = createUrl(requestUrl);

        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream", e);
        }

        if(requestUrl=="https://content.guardianapis.com/search?q=football&show-tags=contributor&show-fields=thumbnail&api-key=839ec3f2-059f-4d23-a6bb-cb2c8b7cc546")
        {
            ArrayList<SportsNews> sportsNews = extractGuardianSportsNews(jsonResponse);


            return sportsNews;
        }


        ArrayList<SportsNews> sportsNews = extractSportsNews(jsonResponse);


        return sportsNews;
    }


    public static ArrayList<SportsNews> extractGuardianSportsNews(String sportsNewsJSON) {


        if (TextUtils.isEmpty(sportsNewsJSON)) {
            return null;
        }


        ArrayList<SportsNews> sportsNews = new ArrayList<>();


        try {

            JSONObject basejsonObject = new JSONObject(sportsNewsJSON);

            JSONObject jsonObject = basejsonObject.getJSONObject("response");


            JSONArray ArticlesJsonArray = jsonObject.getJSONArray("results");


            for (int i = 0; i < ArticlesJsonArray.length(); i++) {
                JSONObject currentGuardianSportsNews = ArticlesJsonArray.getJSONObject(i);






                sportsNews.add(new SportsNews(

                        currentGuardianSportsNews.getJSONObject("fields").getString("thumbnail"),
                        currentGuardianSportsNews.getString("webTitle"),
                        currentGuardianSportsNews.getString("webPublicationDate"),
                        currentGuardianSportsNews.getString("pillarName"),
                        currentGuardianSportsNews.getString("webUrl"),
                        currentGuardianSportsNews.getJSONArray("tags").getJSONObject(0).getString("firstName")
                ));


            }


        } catch (JSONException e) {

            Log.e("QueryUtils", "Problem parsing the SportsNews JSON results", e);
        }


        return sportsNews;
    }


    public static ArrayList<SportsNews> extractSportsNews(String sportsNewsJSON) {


        if (TextUtils.isEmpty(sportsNewsJSON)) {
            return null;
        }


        ArrayList<SportsNews> sportsNews = new ArrayList<>();


        try {

            JSONObject basejsonObject = new JSONObject(sportsNewsJSON);

            JSONArray sportsNewsArray = basejsonObject.getJSONArray("articles");


            for (int i = 0; i < sportsNewsArray.length(); i++) {
                JSONObject currentSportsNews = sportsNewsArray.getJSONObject(i);


                JSONObject properties = currentSportsNews.getJSONObject("source");

                sportsNews.add(new SportsNews(currentSportsNews.getString("urlToImage"),
                        currentSportsNews.getString("title"),
                        currentSportsNews.getString("publishedAt"),
                        properties.getString("name"),
                        currentSportsNews.getString("url"),
                        currentSportsNews.getString("author")
                ));


            }


        } catch (JSONException e) {

            Log.e("QueryUtils", "Problem parsing the SportsNews JSON results", e);
        }


        return sportsNews;
    }


    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error with creating URL ", e);
        }
        return url;
    }


    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();


            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the SportsNews JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }


    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }


}


