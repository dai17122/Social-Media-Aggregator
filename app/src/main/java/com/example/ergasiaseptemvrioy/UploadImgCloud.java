package com.example.ergasiaseptemvrioy;

import android.os.AsyncTask;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

public class UploadImgCloud extends AsyncTask<String, Void, String> {


    private String imageUrl;


    public void setImageUrl(String url){
        this.imageUrl = url;
    }

    public void upload(){
        this.doInBackground();
    }
    @Override
    protected String doInBackground(String... strings) {

        File file = new File(imageUrl);
        AndroidNetworking.upload("https://heroku-cloud.herokuapp.com/")
            .addMultipartFile("userFile",file)
            .build()
            .getAsString(new StringRequestListener() {

                @Override
                public void onResponse(String response) {
                    try {
                        String url = new JSONObject(response).getString("url");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(ANError error) {
                    Log.d("upload", error.getMessage());
                }
            });
        return "";

    }
}
