package com.kzai.nytimessearch;

import com.google.gson.annotations.SerializedName;

/**
 * Created by kzai on 6/24/16.
 */
public class NewsArticle {

    @SerializedName("web_url")
    String webUrl;

    String headline;
    String thumbnail;

    public String getWebUrl() {
        return webUrl;
    }
    public String getHeadline() {
        return headline;
    }
    public String getThumbnail() {
        return thumbnail;
    }
}
