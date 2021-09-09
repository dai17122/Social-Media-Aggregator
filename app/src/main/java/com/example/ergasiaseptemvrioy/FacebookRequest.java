package com.example.ergasiaseptemvrioy;

import android.os.AsyncTask;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;


public class FacebookRequest extends AsyncTask<String, Void, Boolean> {

    private String postBody;
    private boolean ok = false;
    public FacebookRequest(String postBody) {
        this.postBody = postBody;
    }

    public Boolean uploadPost() {
        return this.doInBackground();
    }


    @Override
    protected Boolean doInBackground(String... strings) {


        AndroidNetworking.post("https://graph.facebook.com/v11.0/108449131580569/feed")
            .setPriority(Priority.HIGH)
            .addBodyParameter("message", postBody)
            .addBodyParameter("access_token", BuildConfig.FACEBOOK_PAGE_ACCESS_TOKEN)
            .build()
            .getAsString(new StringRequestListener() {
                @Override
                public void onResponse(String response) {
                    ok = true;
                }

                @Override
                public void onError(ANError anError) {
                }
            });
        return ok;
    }
}
