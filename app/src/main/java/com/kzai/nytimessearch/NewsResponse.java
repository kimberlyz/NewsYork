package com.kzai.nytimessearch;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kzai on 6/24/16.
 */
public class NewsResponse {

    @SerializedName("docs")
    List<NewsArticle> newsArticles;

    // public constructor is necessary for collections
    public NewsResponse() {
        newsArticles = new ArrayList<>();
    }

    public static NewsResponse parseJSON(String response) {
        Gson gson = new GsonBuilder().create();
        NewsResponse newsResponse = gson.fromJson(response, NewsResponse.class);
        return newsResponse;
    }
}
