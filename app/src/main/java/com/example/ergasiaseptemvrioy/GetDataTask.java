package com.example.ergasiaseptemvrioy;

import android.os.AsyncTask;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;

import org.json.JSONArray;

public class GetDataTask extends AsyncTask<String, Void, String> {



    public String getData(){
        Log.d("REST","starting request to remote");
        return this.doInBackground();
    }

    @Override
    protected String doInBackground(String... strings) {
        AndroidNetworking.get("https://jsonplaceholder.typicode.com/todos/")
            .addPathParameter("pageNumber", "0")
            .addQueryParameter("limit", "3")
            .addHeaders("token", "1234")
            .setTag("test")
            .setPriority(Priority.LOW)
            .build()
            .getAsJSONArray(new JSONArrayRequestListener() {
                @Override
                public void onResponse(JSONArray response) {

                    Log.d("REST", "Response= " + response);
                }
                @Override
                public void onError(ANError error) {
                   Log.d("REST", "An error Heppend: "+error);
                }
            });

        return "";
    }

    @Override
    protected void onPostExecute(String s) {
        Log.d("REST", "just got results");
    }
}
