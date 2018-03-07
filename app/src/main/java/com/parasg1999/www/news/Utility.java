package com.parasg1999.www.news;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class Utility {

    private Utility() {

    }

    public static ArrayList<NewsItem> extractFromJson(String jsonResponse) {
        if (TextUtils.isEmpty(jsonResponse)) {
            return null;
        }

        ArrayList<NewsItem> newsItems = new ArrayList<>();

        try {
            JSONObject baseJsonObject = new JSONObject(jsonResponse);
            JSONArray articles = baseJsonObject.getJSONArray("articles");

            for (int i = 0; i < articles.length(); i++) {
                JSONObject currentNews = articles.getJSONObject(i);
                String source = currentNews.getJSONObject("source").getString("name");

                String title = currentNews.getString("title");
                String description = currentNews.getString("description");
                String url = currentNews.getString("url");
                String date = currentNews.getString("publishedAt");

                NewsItem newsItem = new NewsItem(source, title, description, url, date);
                newsItems.add(newsItem);
            }
        } catch (JSONException e) {
            Log.e("Utility", "Problem parsing JSON Response", e);
        }
        return newsItems;
    }

    public static ArrayList<NewsItem> fetchData(String requestUrl) {
        URL url = createUrl(requestUrl);

        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e("Utility", "Problem with HTTP Request", e);
        }

        ArrayList<NewsItem> newsItems = extractFromJson(jsonResponse);
        return newsItems;
    }

    private static URL createUrl(String input) {
        URL url = null;
        try {
            url = new URL(input);
        } catch (Exception e) {
            Log.e("Utility", "Error making URL", e);
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e("Utility", "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e("Utility", "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }
}
