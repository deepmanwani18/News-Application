package com.parasg1999.www.news;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.ArrayList;

public class NewsLoader extends AsyncTaskLoader<ArrayList<NewsItem>> {

    private static final String LOG_TAG = NewsLoader.class.getName();

    private String mUrl;

    public NewsLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public ArrayList<NewsItem> loadInBackground() {
        if(mUrl == null) {
            return null;
        }

        ArrayList<NewsItem> newsItems = Utility.fetchData(mUrl);
        return newsItems;
    }
}
