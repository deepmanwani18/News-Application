package com.parasg1999.www.news;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class NewsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<NewsItem>>{

    private TextView mEmptyStateTextView;
    private NewsAdapter mAdapter;

    private static final int NEWS_LOADER_ID = 1;

    private static final String NEWS_REQUEST_URL =
            "https://newsapi.org/v2/top-headlines?country=in&apiKey=2c5c292ae1f4450e9b7501fad4880749";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_activity);

        ListView newsItemListView = (ListView) findViewById(R.id.list);

        mAdapter = new NewsAdapter(this, new ArrayList<NewsItem>());

        newsItemListView.setAdapter(mAdapter);

        newsItemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NewsItem currentNewsItem = mAdapter.getItem(position);

                Uri newsItemUri = Uri.parse(currentNewsItem.getUrl());
                Intent webIntent = new Intent(Intent.ACTION_VIEW, newsItemUri);
                startActivity(webIntent);
            }
        });

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if(networkInfo != null && networkInfo.isConnected()) {
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(NEWS_LOADER_ID, null, this);
        } else {
            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);

            mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
            mEmptyStateTextView.setText("Error");
        }

        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        newsItemListView.setEmptyView(mEmptyStateTextView);
    }

    @Override
    public Loader<ArrayList<NewsItem>> onCreateLoader(int id, Bundle args) {
        return new NewsLoader(this, Uri.parse(NEWS_REQUEST_URL).toString());
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<NewsItem>> loader, ArrayList<NewsItem> data) {
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        TextView mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        mEmptyStateTextView.setText("Error");

        // Clear the adapter of previous earthquake data
        mAdapter.clear();

        if (data != null && !data.isEmpty()) {
            mAdapter.addAll(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<NewsItem>> loader) {
        mAdapter.clear();
    }
}
