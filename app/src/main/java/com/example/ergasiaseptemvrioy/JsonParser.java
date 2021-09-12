package com.example.ergasiaseptemvrioy;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
                String userName = ob.getJSONObject("user").getString("name");
                String userPhotoUrl = ob.getJSONObject("user").getString("profile_image_url_https");

                Post post = new Post();
                post.setCreatedAt(createdAt + "");
                post.setHashTags(allHashtags + "");
                post.setPostBody(postBody + "");
                 post.setUserName(userName + "");
                post.setUserPhotoUrl(userPhotoUrl + "");
                posts.add(post);
            }
        } catch (Exception e) {
            Log.d("PARSING_POSTS_ERROR", e.getMessage());
        }
        return posts;

    }

    public String parserAccessToken(String jsonResponse) {
        try {
            return new JSONObject(jsonResponse).getString("access_token");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String parseTwitterMediaID(String jsonResponse) {

        try {
            return new JSONObject(jsonResponse).getString("media_id_string");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String parseHerokuImageURl(String jsonResponse) {
        try {
            return new JSONObject(jsonResponse).getString("url");
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
    }

    public String parseInstagramMediaId(String jsonReponse){
        try {
            return new JSONObject(jsonReponse).getString("id");
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }
}
