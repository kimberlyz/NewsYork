package com.kzai.nytimessearch.GSONRegularArticles;

public class Multimedium {
    public String url;
    public static String rootPath = "http://www.nytimes.com/";

    public String getUrl() {
        return rootPath + url;
    }
}
