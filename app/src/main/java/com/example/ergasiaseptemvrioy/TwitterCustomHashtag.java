package com.example.ergasiaseptemvrioy;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;

import java.util.ArrayList;
import java.util.List;

public class TwitterCustomHashtag extends AsyncTask<String, Void, List<Post>> {


    private final static String TWITTER_HASHTAG_SEARCH_URL = "https://api.twitter.com/1.1/search/tweets.json?q=%23";
    protected final String TWITTER_AUTH_HEADER_TYPE = "Authorization";
    protected final String TWITTER_AUTH_HEADER_VALUE = "Bearer " + BuildConfig.TWITTER_BEARER_TOKEN;
    protected final String HASHTAG_STRING_LITERAL = "#";
    protected final String EMPTY_STRING = "";
    protected final String SPACE_STRING_LITERAL = " ";
    protected final String TAG = "FETCHING_POSTS";
    protected String searchTerm;
    protected List<Post> posts = new ArrayList<>();
    private final TwitterPostsAdapter adapter;

    public TwitterCustomHashtag(TwitterPostsAdapter adapter) {
        this.adapter = adapter;
    }

    public void getPosts() {
        this.doInBackground();
    }

    @Override
    protected List<Post> doInBackground(String... strings) {


        this.checkSearchTerm();

        AndroidNetworking.get(TWITTER_HASHTAG_SEARCH_URL + this.searchTerm)
            .addHeaders(TWITTER_AUTH_HEADER_TYPE, TWITTER_AUTH_HEADER_VALUE)
            .setPriority(Priority.HIGH)
            .build()
            .getAsString(new StringRequestListener() {
                @Override
                public void onResponse(String response) {
                    JsonParser parser = new JsonParser();
                    posts = parser.parsePosts(response);
                    Log.d("posts", posts.toString());
                    adapter.setPostList(new JsonParser().parsePosts(response));
                }

                @Override
                public void onError(ANError anError) {
                    Log.d(TAG, anError.getErrorBody());
                }
            });
        return posts;
    }

    public TwitterCustomHashtag setSearchTerm(String term) {
        this.searchTerm = term;
        return this;
    }

    private void checkSearchTerm() {
        if (this.searchTerm.contains("#")) {
            this.searchTerm = this.searchTerm.replace(HASHTAG_STRING_LITERAL, EMPTY_STRING);
        }
        if (this.searchTerm.contains(" ")) {
            this.searchTerm = this.searchTerm.replace(SPACE_STRING_LITERAL, EMPTY_STRING);
        }
    }


}
