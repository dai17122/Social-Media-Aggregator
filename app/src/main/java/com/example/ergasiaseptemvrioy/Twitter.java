package com.example.ergasiaseptemvrioy;

import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.androidnetworking.interfaces.StringRequestListener;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.net.URL;
import java.util.List;

public class Twitter extends AsyncTask<String, Void, String> {

    protected final String TWITTER_TRENDS_URL = "https://api.twitter.com/1.1/trends/place.json?id=1";
    protected final String TWITTER_AUTH_HEADER_TYPE = "Authorization";
    protected final String TWITTER_AUTH_HEADER_VALUE = "Bearer " + BuildConfig.TWITTER_BEARER_TOKEN;
    protected static final String TAG = "TWITTER_REQUEST";
    public Twitter getTrends() {
        this.doInBackground();
        return this;
    }


    @Override
    protected String doInBackground(String... strings) {

        AndroidNetworking.get(this.TWITTER_TRENDS_URL)
            .addHeaders(this.TWITTER_AUTH_HEADER_TYPE, this.TWITTER_AUTH_HEADER_VALUE)
            .setPriority(Priority.HIGH)
            .build()
            .getAsString(new StringRequestListener() {
                @Override
                public void onResponse(String response) {
                    JsonParser parser = new JsonParser();
                    parser.parseTrends(response);
                }

                @Override
                public void onError(ANError anError) {
                    Log.d(TAG, anError.getErrorBody());
                }
            });


        return "";
    }
}
