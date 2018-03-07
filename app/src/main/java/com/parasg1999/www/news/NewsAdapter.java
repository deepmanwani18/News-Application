package com.parasg1999.www.news;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class NewsAdapter extends ArrayAdapter<NewsItem> {

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.news_list_item , parent, false);
        }

        NewsItem currentNews = getItem(position);

        TextView titleView = (TextView) convertView.findViewById(R.id.title_text_view);
        titleView.setText(currentNews.getTitle());

        TextView descriptionView = (TextView) convertView.findViewById(R.id.description_text_view);
        descriptionView.setText(currentNews.getDescription());

        TextView sourceView = (TextView) convertView.findViewById(R.id.source_text_view);
        sourceView.setText(currentNews.getSource());

        TextView dateView = (TextView) convertView.findViewById(R.id.datetime_text_view);
        dateView.setText(currentNews.getDate());

        return convertView;
    }

    public NewsAdapter(@NonNull Context context, ArrayList<NewsItem> newsItems) {
        super(context, 0, newsItems);
    }
}
