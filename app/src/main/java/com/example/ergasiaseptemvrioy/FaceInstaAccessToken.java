package com.example.ergasiaseptemvrioy;
import android.os.AsyncTask;
import android.util.Log;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;

public class FaceInstaAccessToken extends AsyncTask<String, Void, String> {

    private final String APP_ID = BuildConfig.FACEBOOK_APP_ID;
    private final String APP_SECRET = BuildConfig.FACEBOOK_APP_SECRET;
    private final String FACEBOOK_API_URL = "https://graph.facebook.com/oauth/access_token";
    private String accessToken;
    private JsonParser parser;


    public FaceInstaAccessToken(){
        this.parser = new JsonParser();
    }

    public String generateAccessToken(){
      return  this.doInBackground();
    }

    public String getAccessToken(){
        return  this.accessToken;

    }

    @Override
    protected String doInBackground(String... strings) {
        AndroidNetworking.get(FACEBOOK_API_URL)
            .addQueryParameter("client_id", APP_ID)
            .addQueryParameter("client_secret", APP_SECRET)
            .addQueryParameter("grant_type", "client_credentials")
            .setPriority(Priority.HIGH)
            .build()
            .getAsString(new StringRequestListener() {
                @Override
                public void onResponse(String response) {
                   accessToken = parser.parserAccessToken(response);
                }

                @Override
                public void onError(ANError anError) {
                    Log.d("facebook", anError.getErrorBody());
                }
            });
        return "";
    }

    @Override
    protected void onPostExecute(String s) {

    }
}
