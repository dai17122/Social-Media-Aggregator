package com.example.ergasiaseptemvrioy;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;

import java.io.File;

public class InstagramPost extends AsyncTask<String, Void, String> {


    private final Context context;
    private String imageUrl;
    private String url;

    public void setImageUrl(String url) {
        this.imageUrl = url;
    }

    public InstagramPost(Context context){
        this.context = context;
    }
    public void upload() {
        this.doInBackground();
    }

    @Override
    protected String doInBackground(String... strings) {


        uploadImgToBackend();


        return "";

    }


    public void uploadImgToBackend() {
        File file = new File(imageUrl);
        AndroidNetworking.upload("https://heroku-cloud.herokuapp.com/")
            .addMultipartFile("userFile", file)
            .build()
            .getAsString(new StringRequestListener() {

                @Override
                public void onResponse(String response) {
                    JsonParser parser = new JsonParser();
                    String imgUrl = parser.parseHerokuImageURl(response);
                    Toast.makeText(context, "uploaded to backend", Toast.LENGTH_SHORT).show();
                    createInstagramPost(imgUrl);
                }
                @Override
                public void onError(ANError error) {
                    Log.d("upload", error.getMessage());
                }
            });
    }

    public void createInstagramPost(String url) {

        AndroidNetworking.post("https://graph.facebook.com/17841449531539256/media")
            .addQueryParameter("access_token", BuildConfig.INSTAGRAM_FINAL_ACCESS_TOKEN)
            .addQueryParameter("caption", "first post")
            .addQueryParameter("image_url", url)
            .build()
            .getAsString(new StringRequestListener() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(context, "media uploaded"+"", Toast.LENGTH_SHORT).show();

                    String id = new JsonParser().parseInstagramMediaId(response);
                    publishMedia(id);
                }
                @Override
                public void onError(ANError anError) {
                    Toast.makeText(context, anError.getErrorBody(), Toast.LENGTH_LONG).show();
                }
            });
    }



    public void publishMedia(String id){


        AndroidNetworking.post("https://graph.facebook.com/17841449531539256/media_publish")
            .addQueryParameter("access_token", BuildConfig.INSTAGRAM_FINAL_ACCESS_TOKEN)
            .addQueryParameter("creation_id", id)
            .build()
            .getAsString(new StringRequestListener() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(context, response+"", Toast.LENGTH_LONG).show();
                }
                @Override
                public void onError(ANError anError) {
                    Toast.makeText(context, anError.getErrorBody(), Toast.LENGTH_LONG).show();
                }
            });

    }
}
