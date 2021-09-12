package com.example.ergasiaseptemvrioy;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;

import java.io.File;
import java.security.SecureRandom;
import java.util.Base64;

public class TwitterPost extends AsyncTask<String, Void, String> {

    private String filePath;
    private String postBody;
    private Context context;
    private String mediaId;

    private final String TWITTER_MEDIA_UPLOAD_URL = "https://upload.twitter.com/1.1/media/upload.json";
    private final String TWITTER_POST_UPLOAD_URL = "https://api.twitter.com/1.1/statuses/update.json";

    public TwitterPost(Context context, String postBody, String filePath) {
        this.context = context;
        this.filePath = filePath;
        this.postBody = postBody;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void uploadTwitterPost() {
        this.doInBackground();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected String doInBackground(String... strings) {
        uploadMediaFile();
        return "";
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void uploadMediaFile() {

        String signature = new OAuth1AuthorizationHeaderBuilder()
            .withMethod("POST")
            .withURL(TWITTER_MEDIA_UPLOAD_URL)
            .withConsumerSecret(BuildConfig.TWITTER_API_KEY_SECRET)
            .withTokenSecret(BuildConfig.TWITTER_ACCESS_TOKEN_SECRET)
            .withParameter("oauth_consumer_key", BuildConfig.TWITTER_API_KEY)
            .withParameter("oauth_token", BuildConfig.TWITTER_ACCESS_TOKEN)
            .withParameter("oauth_nonce", nonce())
            .build();

        AndroidNetworking.upload(TWITTER_MEDIA_UPLOAD_URL)
            .addHeaders("authorization", signature)
            .addMultipartFile("media", new File(filePath))
            .addMultipartParameter("command", "STATUS")
            .build()
            .getAsString(new StringRequestListener() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(context, response + "", Toast.LENGTH_LONG).show();
                    Log.d("response", response + "");
                    mediaId = new JsonParser().parseTwitterMediaID(response);
                    uploadPost(mediaId);
                }
                @Override
                public void onError(ANError anError) {
                    Toast.makeText(context, "There was an error in twitter " + anError.getErrorBody(), Toast.LENGTH_LONG).show();
                }
            });
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void uploadPost(String mediaId) {

        String signature = new OAuth1AuthorizationHeaderBuilder()
            .withMethod("POST")
            .withURL(TWITTER_POST_UPLOAD_URL)
            .withConsumerSecret(BuildConfig.TWITTER_API_KEY_SECRET)
            .withTokenSecret(BuildConfig.TWITTER_ACCESS_TOKEN_SECRET)
            .withParameter("oauth_consumer_key", BuildConfig.TWITTER_API_KEY)
            .withParameter("oauth_token", BuildConfig.TWITTER_ACCESS_TOKEN)
            .withParameter("oauth_nonce", nonce())
            .withParameter("status", postBody)
            .withParameter("media_ids", mediaId)
            .build();

        AndroidNetworking.post(TWITTER_POST_UPLOAD_URL)
            .addHeaders("authorization", signature)
            .addBodyParameter("status", postBody)
            .addBodyParameter("media_ids", mediaId)

            .build()
            .getAsString(new StringRequestListener() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(context, response + "", Toast.LENGTH_LONG).show();
                    Log.d("response", response + "");
                }

                @Override
                public void onError(ANError anError) {
                    Toast.makeText(context,  anError.getErrorBody(), Toast.LENGTH_LONG).show();
                }
            });
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private String nonce() {
        byte[] nonce = new byte[12];
        new SecureRandom().nextBytes(nonce);
        StringBuilder result = new StringBuilder();
        for (byte temp : nonce) {
            result.append(String.format("%02x", temp));
        }
        byte[] encodedBytes = Base64.getEncoder().encode(result.toString().getBytes());
        return new String(encodedBytes);
    }


}
