package com.kzai.nytimessearch;

import android.content.Context;

import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by kzai on 6/24/16.
 */
@Parcel
public class SearchArticle {

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

    public SearchArticle() {

    }

    public SearchArticle(Doc doc) {
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

    public static ArrayList<SearchArticle> fromNewsArticles(NewsArticle newsArticle) {
        ArrayList<SearchArticle> results = new ArrayList<>();

        androidColors =  context.getResources().getIntArray(R.array.androidcolors);

        for (Doc doc: newsArticle.getResponse().getDocs()) {
            results.add(new SearchArticle(doc));
        }
        return results;
    }
}
