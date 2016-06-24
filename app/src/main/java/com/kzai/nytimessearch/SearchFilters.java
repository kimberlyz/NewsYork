package com.kzai.nytimessearch;

import org.parceler.Parcel;

import java.util.Calendar;

/**
 * Created by kzai on 6/24/16.
 */
@Parcel
public class SearchFilters {

    public void setBeginDate(Calendar c) {
        this.beginDate = beginDate;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public void setNewsDesk(String newsDesk) {
        this.newsDesk = newsDesk;
    }

    String beginDate;
    String sort;
    String newsDesk;



    public SearchFilters() {

    }



}
