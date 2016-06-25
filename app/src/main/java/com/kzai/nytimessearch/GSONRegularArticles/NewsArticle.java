
package com.kzai.nytimessearch.GSONRegularArticles;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class NewsArticle {
    public Response response;

    public Response getResponse() {
        return response;
    }

    public static NewsArticle parseJSON(String response) {
        Gson gson = new GsonBuilder().create();
        NewsArticle result = gson.fromJson(response, NewsArticle.class);
        return result;
    }
}
