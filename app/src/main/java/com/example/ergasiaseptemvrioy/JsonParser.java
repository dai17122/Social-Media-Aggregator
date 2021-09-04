package com.example.ergasiaseptemvrioy;

import android.util.Log;


import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class JsonParser {

    public static final String TAG = "JSON_PARSE";
    public static final String HASHTAG_LITERAL = "name";
    public static final String HASHTAG_QUERY_URL = "url";

    public List<Trend> parseTrends(String jsonResponse) {
        List<Trend> trendsList = new ArrayList<>();
        try {
            JSONObject parent = new JSONArray(jsonResponse).getJSONObject(0);
            JSONArray trendsArray = parent.getJSONArray("trends");
            for (int i = 0; i < trendsArray.length(); i++) {
                String name = trendsArray.getJSONObject(i).getString(HASHTAG_LITERAL);
                String queryUrl = trendsArray.getJSONObject(i).getString(HASHTAG_QUERY_URL);
                Trend tr = new Trend();
                tr.setName(name).setQueryUrl(queryUrl);
                trendsList.add(tr);
            }
        } catch (Exception e) {
            Log.d("JSON_PARSE", e.getMessage());
        }

        return trendsList;
    }


}
