package com.example.ergasiaseptemvrioy;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;


public class FacebookRequest extends AsyncTask<String, Void, String> {

    private String postBody;
    private final Context context;
    private static final String FACEBOOK_API_BASE_URL = "https://graph.facebook.com/v11.0/";
    private static final String PAGE_ID_URL = "108449131580569/feed";
    private static final String POST_BODY_PARAMETER_NAME="message";
    private static final String FACEBOOK_PAGE_ACCESS_TOKEN_PARAMETER_NAME="access_token";
    private static final String SUCCESS_MESSAGE = "Success";
    private static final String ERROR_MESSAGE = "An error happened while trying to upload post to facebook";


    public FacebookRequest(String postBody, Context context) {
        this.postBody = postBody;
        this.context = context;
    }

    public String uploadPost() {
        return this.doInBackground();
    }


    @Override
    protected String doInBackground(String... strings) {

        AndroidNetworking.post(FACEBOOK_API_BASE_URL + PAGE_ID_URL)
            .setPriority(Priority.HIGH)
            .addBodyParameter(POST_BODY_PARAMETER_NAME, postBody)
            .addBodyParameter(FACEBOOK_PAGE_ACCESS_TOKEN_PARAMETER_NAME, BuildConfig.FACEBOOK_PAGE_ACCESS_TOKEN)
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
        return "";
    }
}
