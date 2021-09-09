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
    private Context context;
    private boolean ok;
    private String errors;

    public FacebookRequest(String postBody, Context context) {
        this.postBody = postBody;
        this.context = context;
    }

    public String uploadPost() {
        return this.doInBackground();
    }


    @Override
    protected String doInBackground(String... strings) {

        AndroidNetworking.post("https://graph.facebook.com/v11.0/108449131580569/feed")
            .setPriority(Priority.HIGH)
            .addBodyParameter("message", postBody)
            .addBodyParameter("access_token", BuildConfig.FACEBOOK_PAGE_ACCESS_TOKEN)
            .build()
            .getAsString(new StringRequestListener() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(context, "Success!!", Toast.LENGTH_LONG).show();
                }
                @Override
                public void onError(ANError anError) {
                    Toast.makeText(context, "An error happened while trying to upload post to facebook", Toast.LENGTH_LONG).show();
                }
            });
        return "";
    }
}
