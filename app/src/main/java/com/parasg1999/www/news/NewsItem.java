package com.parasg1999.www.news;

/**
 * Created by parasg1999 on 8/3/18.
 */

public class NewsItem {

    private String mSource, mDescription, mTitle, mUrl, mDate;

    public NewsItem(String source, String title, String description, String url, String date) {
        mSource = source;
        mTitle = title;
        mDate = date;
        mDescription = description;
        mUrl = url;
    }

    public String getSource() {
        return mSource;
    }

    public String getDate() {
        return mDate;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getUrl() {
        return mUrl;
    }


}
