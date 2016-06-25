
package com.kzai.nytimessearch.GSONTopArticles;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

public class TopArticles {
    public List<Result> results = new ArrayList<Result>();

    public List<Result> getResults() {
        return results;
    }

    public static TopArticles parseJSON(String response) {
        Gson gson = new GsonBuilder().create();
        TopArticles result = gson.fromJson(response, TopArticles.class);
        return result;
    }
}
