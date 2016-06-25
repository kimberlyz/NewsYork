
package com.kzai.nytimessearch.GSONTopArticles;

import java.util.ArrayList;
import java.util.List;

public class Result {
    public String title;
    public String url;
    public List<TopMultimedium> multimedia = new ArrayList<>();

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public List<TopMultimedium> getMultimedia() {
        return multimedia;
    }
}
