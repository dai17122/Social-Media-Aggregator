package com.example.ergasiaseptemvrioy;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;

import java.io.File;

public class InstagramPost extends AsyncTask<String, Void, String> {

    private final String INSTAGRAM_MEDIA_URL = "https://graph.facebook.com/17841449531539256/media";
    private final String ACCESS_TOKEN_PARAM_NAME = "access_token";
    private final String CAPTION_INSTAGRAM_PARAM_NAME= "caption";
    private final String BACKEND_URL="https://heroku-cloud.herokuapp.com/";
    private final String IMAGE_URL_PARAM_NAME = "image_url";
    private final String BACKEND_FILE_PARAM_NAME = "userFile";
    private final String SUCCESS_MESSAGE= "Successfully uploaded post to Instagram";
    private final String INSTAGRAM_PUBLISH_MEDIA_URL = "https://graph.facebook.com/17841449531539256/media_publish";
    private final String CREATION_ID_PARAM_NAME = "creation_id";
    private final String FAILED_MESSAGE = "Instagram Upload Failed";
    private final Context context;
    private String imageUrl;
    private String url;
    private String postBody;

    public void setImageUrl(String url) {
        this.imageUrl = url;
    }

    public InstagramPost(Context context){
        this.context = context;
    }
    public void upload() {
        this.doInBackground();
    }

    public void setPostBody(String postBody){
        this.postBody = postBody;
    }

    @Override
    protected String doInBackground(String... strings) {
        uploadImgToBackend();
        return "";
    }

    public void uploadImgToBackend() {
        File file = new File(imageUrl);
        AndroidNetworking.upload(BACKEND_URL)
            .addMultipartFile(BACKEND_FILE_PARAM_NAME, file)
            .build()
            .getAsString(new StringRequestListener() {
                @Override
                public void onResponse(String response) {
                    JsonParser parser = new JsonParser();
                    String imgUrl = parser.parseHerokuImageURl(response);
                    createInstagramPost(imgUrl);
                }
                @Override
                public void onError(ANError error) {
                   Toast.makeText(context,FAILED_MESSAGE, Toast.LENGTH_SHORT).show();
                }
            });
    }

    public void createInstagramPost(String url) {

        AndroidNetworking.post(INSTAGRAM_MEDIA_URL)
            .addQueryParameter(ACCESS_TOKEN_PARAM_NAME, BuildConfig.INSTAGRAM_FINAL_ACCESS_TOKEN)
            .addQueryParameter(CAPTION_INSTAGRAM_PARAM_NAME, postBody)
            .addQueryParameter(IMAGE_URL_PARAM_NAME, url)
            .build()
            .getAsString(new StringRequestListener() {
                @Override
                public void onResponse(String response) {
                    String id = new JsonParser().parseInstagramMediaId(response);
                    publishMedia(id);
                }
                @Override
                public void onError(ANError anError) {
                    Toast.makeText(context, FAILED_MESSAGE, Toast.LENGTH_LONG).show();
                }
            });
    }

    public void publishMedia(String id){

        AndroidNetworking.post(INSTAGRAM_PUBLISH_MEDIA_URL)
            .addQueryParameter(ACCESS_TOKEN_PARAM_NAME, BuildConfig.INSTAGRAM_FINAL_ACCESS_TOKEN)
            .addQueryParameter(CREATION_ID_PARAM_NAME, id)
            .build()
            .getAsString(new StringRequestListener() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(context, SUCCESS_MESSAGE, Toast.LENGTH_LONG).show();

                }
                @Override
                public void onError(ANError anError) {
                    Toast.makeText(context, anError.getErrorBody(), Toast.LENGTH_LONG).show();
                }
            });

    }
}
