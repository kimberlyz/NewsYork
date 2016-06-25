package com.kzai.nytimessearch;

import android.content.Context;

import com.kzai.nytimessearch.GSONRegularArticles.Doc;
import com.kzai.nytimessearch.GSONRegularArticles.Multimedium;
import com.kzai.nytimessearch.GSONRegularArticles.NewsArticle;
import com.kzai.nytimessearch.GSONTopArticles.Result;
import com.kzai.nytimessearch.GSONTopArticles.TopArticles;
import com.kzai.nytimessearch.GSONTopArticles.TopMultimedium;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by kzai on 6/20/16.
 */
@Parcel
public class Article {

    String webUrl;
    String headline;
    String thumbnail;
    static Context context;
    static int[] androidColors;
    Integer backgroundColor;

    public String getWebUrl() {
        return webUrl;
    }

    public String getHeadline() {
        return headline;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public Integer getBackgroundColor() {
        return backgroundColor;
    }

    public static void setContext(Context c) {
        context = c;
    }

    public Article() {

    }

    public Article(Doc doc) {
        this.webUrl = doc.getWebUrl();
        this.headline = doc.getHeadline().getMain();

        List<Multimedium> multimedia = doc.getMultimedia();

        if (multimedia.size() > 0) {
            this.thumbnail = doc.getMultimedia().get(0).getUrl();
        } else {
            this.thumbnail = "";
        }
        this.backgroundColor = androidColors[new Random().nextInt(androidColors.length)];
    }

    public Article(Result result) {
        this.webUrl = result.getUrl();
        this.headline = result.getTitle();

        List<TopMultimedium> multimedia = result.getMultimedia();
        Integer multimediaSize = multimedia.size();
        if (multimediaSize > 1) {
            this.thumbnail = multimedia.get(1).getUrl();
        } else if (multimediaSize > 0) {
            this.thumbnail = multimedia.get(0).getUrl();
        } else {
            this.thumbnail = "";
        }
        this.backgroundColor = androidColors[new Random().nextInt(androidColors.length)];
    }

    public static ArrayList<Article> fromNewsArticles(NewsArticle newsArticle) {
        ArrayList<Article> results = new ArrayList<>();

        androidColors =  context.getResources().getIntArray(R.array.androidcolors);

        for (Doc doc: newsArticle.getResponse().getDocs()) {
            results.add(new Article(doc));
        }
        return results;
    }

    public static ArrayList<Article> fromTopArticles(TopArticles topArticles) {
        ArrayList<Article> results = new ArrayList<>();

        androidColors =  context.getResources().getIntArray(R.array.androidcolors);

        for (Result result: topArticles.getResults()) {
            results.add(new Article(result));
        }
        return results;
    }



    public Article(JSONObject jsonObject, Boolean b) {
        try {
            this.webUrl = jsonObject.getString("url");
            this.headline = jsonObject.getString("title");

            JSONArray multimedia = jsonObject.getJSONArray("multimedia");
            Integer multimediaLength = multimedia.length();

            if (multimediaLength > 1) {
                JSONObject multimediaJson = multimedia.getJSONObject(1);
                this.thumbnail = multimediaJson.getString("url");
            } else if (multimediaLength > 0) {
                JSONObject multimediaJson = multimedia.getJSONObject(0);
                this.thumbnail = multimediaJson.getString("url");
            } else {
                this.thumbnail = "";
            }
            this.backgroundColor = androidColors[new Random().nextInt(androidColors.length)];
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Article> fromJSONArrayTopStories(JSONArray array) {
        ArrayList<Article> results = new ArrayList<>();
        androidColors =  context.getResources().getIntArray(R.array.androidcolors);

        for (int x = 0; x < array.length(); x++) {
            try {
                results.add(new Article(array.getJSONObject(x), true));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return results;
    }


}
