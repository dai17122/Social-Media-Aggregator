package com.example.ergasiaseptemvrioy;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONObject;

import java.io.File;


public class FacebookPost extends AsyncTask<String, Void, String> {

    private static final String FACEBOOK_API_UPLOAD_URL = "https://graph.facebook.com/v11.0/108449131580569/photos";
    private static final String POST_BODY_PARAMETER_NAME = "message";
    private static final String FACEBOOK_PAGE_ACCESS_TOKEN_PARAMETER_NAME = "access_token";
    private static final String IMAGE_PARAMETER_NAME = "image";


    private final String postBody;
    private String imagePath;
    private final Context context;




    public FacebookPost(String postBody, Context context) {
        this.postBody = postBody;
        this.context = context;

    }

    public void uploadPost() {
        this.doInBackground();
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Override
    protected String doInBackground(String... strings) {
        AndroidNetworking.upload(FACEBOOK_API_UPLOAD_URL)
            .addMultipartFile(IMAGE_PARAMETER_NAME, new File(imagePath))
            .addMultipartParameter(FACEBOOK_PAGE_ACCESS_TOKEN_PARAMETER_NAME, BuildConfig.FACEBOOK_PAGE_ACCESS_TOKEN)
            .addMultipartParameter(POST_BODY_PARAMETER_NAME, postBody)
            .build()
            .getAsJSONObject(new JSONObjectRequestListener() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.d("path", response.toString() + "");
                    Toast.makeText(context, response.toString(), Toast.LENGTH_LONG).show();
                }

                @Override
                public void onError(ANError anError) {
                    Log.d("path", anError.getErrorBody() + "");
                    Toast.makeText(context, anError.getErrorBody(), Toast.LENGTH_LONG).show();
                }
            });
        return "";
    }
}

