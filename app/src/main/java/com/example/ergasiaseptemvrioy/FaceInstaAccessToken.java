package com.example.ergasiaseptemvrioy;

import android.os.AsyncTask;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;

public class FaceInstaAccessToken extends AsyncTask<String, Void, String> {

    private final String APP_ID = BuildConfig.FACEBOOK_APP_ID;
    private final String APP_SECRET = BuildConfig.FACEBOOK_APP_SECRET;
    private final String FACEBOOK_API_URL = "https://graph.facebook.com/oauth/access_token";
    private final String CLIENT_ID_PARAM_NAME = "client_id";
    private final String CLIENT_SECRET_PARAM_NAME = "client_secret";
    private final String GRANT_TYPE_PARAM_NAME = "grant_type";
    private final String CLIENT_CREDENTIALS_VALUE = "client_credentials";
    private String accessToken;
    private JsonParser parser;


    public FaceInstaAccessToken() {
        this.parser = new JsonParser();
    }

    public String generateAccessToken() {
        return this.doInBackground();
    }

    public String getAccessToken() {
        return this.accessToken;
    }

    @Override
    protected String doInBackground(String... strings) {
        AndroidNetworking.get(FACEBOOK_API_URL)
            .addQueryParameter(CLIENT_ID_PARAM_NAME, APP_ID)
            .addQueryParameter(CLIENT_SECRET_PARAM_NAME, APP_SECRET)
            .addQueryParameter(GRANT_TYPE_PARAM_NAME, CLIENT_CREDENTIALS_VALUE)
            .setPriority(Priority.HIGH)
            .build()
            .getAsString(new StringRequestListener() {
                @Override
                public void onResponse(String response) {
                    accessToken = parser.parserAccessToken(response);
                }

                @Override
                public void onError(ANError anError) {

                }
            });
        return "";
    }

    @Override
    protected void onPostExecute(String s) {

    }
}
