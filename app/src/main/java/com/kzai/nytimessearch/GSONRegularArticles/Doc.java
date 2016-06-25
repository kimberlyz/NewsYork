
package com.kzai.nytimessearch.GSONRegularArticles;

import java.util.ArrayList;
import java.util.List;

public class Doc {
    public String web_url;
    public List<Multimedium> multimedia = new ArrayList<Multimedium>();
    public Headline headline;

    public String getWebUrl() {
        return web_url;
    }

    public List<Multimedium> getMultimedia() {
        return multimedia;
    }

    public Headline getHeadline() {
        return headline;
    }
}
