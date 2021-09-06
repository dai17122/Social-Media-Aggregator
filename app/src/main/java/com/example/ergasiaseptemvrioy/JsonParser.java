package com.example.ergasiaseptemvrioy;

import android.util.Log;


import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class JsonParser {

    public static final String TAG = "JSON_PARSE";
    public static final String HASHTAG_LITERAL = "name";
    public static final String HASHTAG_QUERY_URL = "url";

    public List<Trend> parseTrends(String jsonResponse, List<Trend> trends) {

        try {
            JSONObject parent = new JSONArray(jsonResponse).getJSONObject(0);
            JSONArray trendsArray = parent.getJSONArray("trends");
            for (int i = 0; i < trendsArray.length(); i++) {
                String name = trendsArray.getJSONObject(i).getString(HASHTAG_LITERAL);
                String queryUrl = trendsArray.getJSONObject(i).getString(HASHTAG_QUERY_URL);
                Trend tr = new Trend();
                tr.setName(name).setQueryUrl(queryUrl);
                trends.add(tr);
            }

        } catch (Exception e) {
            Log.d("JSON_PARSE", e.getMessage());
        }
        return trends;
    }


    public List<Post> parsePosts(String jsonResponse) {
        List<Post> posts = new ArrayList<Post>();
        try {
            JSONArray statuses = new JSONObject(jsonResponse).getJSONArray("statuses");
            for (int i = 0; i < statuses.length(); i++) {
                JSONObject ob = statuses.getJSONObject(i);
                JSONObject entities = ob.getJSONObject("entities");
                JSONArray hashtagsArray = entities.getJSONArray("hashtags");
                StringBuilder allHashtags = new StringBuilder();
                for (int j = 0; j < hashtagsArray.length(); j++) {
                    JSONObject hashtags = hashtagsArray.getJSONObject(j);
                    allHashtags.append(" #").append(hashtags.getString("text"));
                }

                String createdAt = ob.getString("created_at");
                String postBody = ob.getString("text");
//                String userName = ob.getJSONObject("user").getString("name");
//                String userPhotoUrl = ob.getString("profile_image_url_https");

                Post post = new Post();
                post.setCreatedAt(createdAt + "");
                post.setHashTags(allHashtags + "");
                post.setPostBody(postBody + "");
//                 post.setUserName(userName + "");
//                post.setUserPhotoUrl(userPhotoUrl + "");
                posts.add(post);
            }
        } catch (Exception e) {
            Log.d("PARSING_POSTS_ERROR", e.getMessage());
        }
        return posts;

    }


}
