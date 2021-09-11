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


public class FacebookRequest extends AsyncTask<String, Void, String> {

    private String postBody;


    private String imagePath;
    private static final String FACEBOOK_API_BASE_URL = "https://graph.facebook.com/v11.0/";
    private static final String PAGE_ID_URL = "108449131580569/feed";
    private static final String POST_BODY_PARAMETER_NAME = "message";
    private static final String FACEBOOK_PAGE_ACCESS_TOKEN_PARAMETER_NAME = "access_token";
    private static final String SUCCESS_MESSAGE = "Success";
    private static final String ERROR_MESSAGE = "An error happened while trying to upload post to facebook";
    private Context context;

    public FacebookRequest(String postBody, Context context) {
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
        AndroidNetworking.upload("https://graph.facebook.com/v11.0/108449131580569/photos")
            .addMultipartFile("image", new File(imagePath))
            .addMultipartParameter("access_token","EAAClZAtayZBDwBAC50QfINvYC2dlE41CaqwfPa9bGp5K7OylGZBUN7XRBSEkxbWw4H07OwfOzh7dqRFoShh1p3LkGN0LtoeWTEPfduG6TDPOfI56GdZBHufZCj7TVfQHrNxdtcpFn0fLZAjlwRXnTSZC7XthpijXNeXQLCGQhiZBHrK8Jhlo0Y5D")
            .addMultipartParameter("message", postBody)
            .setTag("uploadTest")
            .build()
            .getAsJSONObject(new JSONObjectRequestListener() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.d("path", response.toString()+"");
                    Toast.makeText(context, response.toString(), Toast.LENGTH_LONG).show();
                }
                @Override
                public void onError(ANError anError) {
                    Log.d("path", anError.getErrorBody()+"");
                    Toast.makeText(context, anError.getErrorBody(), Toast.LENGTH_LONG).show();
                }
            });
        return  "";
    }
}

