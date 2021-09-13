package com.example.ergasiaseptemvrioy;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;

import java.io.File;
import java.security.SecureRandom;
import java.util.Base64;

public class TwitterPost extends AsyncTask<String, Void, String> {


    private final String TWITTER_MEDIA_UPLOAD_URL = "https://upload.twitter.com/1.1/media/upload.json";
    private final String TWITTER_POST_UPLOAD_URL = "https://api.twitter.com/1.1/statuses/update.json";
    private final String HTTP_METHOD = "POST";
    private final String NONCE_STRING_FORMAT = "%02x";
    private final String OAUTH_CONSUMER_KEY_PARAM_NAME = "oauth_consumer_key";
    private final String OAUTH_TOKEN_PARAM_NAME = "oauth_token";
    private final String OAUTH_NONCE_PARAM_NAME = "oauth_nonce";
    private final String STATUS_PARAM_NAME = "status";
    private final String AUTHORIZATION_HEADER_PARAM_NAME = "authorization";
    private final String MEDIA_ID_PARAM_NAME = "media_ids";
    private final String SUCCESS_MESSAGE = "Successfully uploaded Post to Twitter";
    private final String ERROR_MESSAGE = "There was an error uploading post to Twitter";
    private final String MEDIA_PARAM_NAME = "media";
    private final String COMMAND_PARAM_NAME = "command";
    private final String COMMAND_VALUE_STATUS = "STATUS";
    private String filePath;
    private String postBody;
    private Context context;
    private String mediaId;


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
            .withMethod(HTTP_METHOD)
            .withURL(TWITTER_MEDIA_UPLOAD_URL)
            .withConsumerSecret(BuildConfig.TWITTER_API_KEY_SECRET)
            .withTokenSecret(BuildConfig.TWITTER_ACCESS_TOKEN_SECRET)
            .withParameter(OAUTH_CONSUMER_KEY_PARAM_NAME, BuildConfig.TWITTER_API_KEY)
            .withParameter(OAUTH_TOKEN_PARAM_NAME, BuildConfig.TWITTER_ACCESS_TOKEN)
            .withParameter(OAUTH_NONCE_PARAM_NAME, nonce())
            .build();

        AndroidNetworking.upload(TWITTER_MEDIA_UPLOAD_URL)
            .addHeaders(AUTHORIZATION_HEADER_PARAM_NAME, signature)
            .addMultipartFile(MEDIA_PARAM_NAME, new File(filePath))
            .addMultipartParameter(COMMAND_PARAM_NAME, COMMAND_VALUE_STATUS)
            .build()
            .getAsString(new StringRequestListener() {
                @Override
                public void onResponse(String response) {
                    mediaId = new JsonParser().parseTwitterMediaID(response);
                    uploadPost(mediaId);
                }
                @Override
                public void onError(ANError anError) {
                    Toast.makeText(context, ERROR_MESSAGE, Toast.LENGTH_LONG).show();
                }
            });
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void uploadPost(String mediaId) {

        String signature = new OAuth1AuthorizationHeaderBuilder()
            .withMethod(HTTP_METHOD)
            .withURL(TWITTER_POST_UPLOAD_URL)
            .withConsumerSecret(BuildConfig.TWITTER_API_KEY_SECRET)
            .withTokenSecret(BuildConfig.TWITTER_ACCESS_TOKEN_SECRET)
            .withParameter(OAUTH_CONSUMER_KEY_PARAM_NAME, BuildConfig.TWITTER_API_KEY)
            .withParameter(OAUTH_TOKEN_PARAM_NAME, BuildConfig.TWITTER_ACCESS_TOKEN)
            .withParameter(OAUTH_NONCE_PARAM_NAME, nonce())
            .withParameter(STATUS_PARAM_NAME, postBody)
            .withParameter(MEDIA_ID_PARAM_NAME, mediaId)
            .build();

        AndroidNetworking.post(TWITTER_POST_UPLOAD_URL)
            .addHeaders(AUTHORIZATION_HEADER_PARAM_NAME, signature)
            .addBodyParameter(STATUS_PARAM_NAME, postBody)
            .addBodyParameter(MEDIA_ID_PARAM_NAME, mediaId)

            .build()
            .getAsString(new StringRequestListener() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(context, SUCCESS_MESSAGE, Toast.LENGTH_LONG).show();
                }

                @Override
                public void onError(ANError anError) {
                    Toast.makeText(context, ERROR_MESSAGE, Toast.LENGTH_LONG).show();
                }
            });
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private String nonce() {
        byte[] nonce = new byte[12];
        new SecureRandom().nextBytes(nonce);
        StringBuilder result = new StringBuilder();
        for (byte temp : nonce) {
            result.append(String.format(NONCE_STRING_FORMAT, temp));
        }
        byte[] encodedBytes = Base64.getEncoder().encode(result.toString().getBytes());
        return new String(encodedBytes);
    }


}
