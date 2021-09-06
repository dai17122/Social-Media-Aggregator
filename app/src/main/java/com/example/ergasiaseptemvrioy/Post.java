package com.example.ergasiaseptemvrioy;

public class Post {

    private String postBody;
    private String createdAt;
    private String userName;
    private String userPhotoUrl;
    private String hashTags;

    public void setPostBody(String postBody) {
        this.postBody = postBody;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserPhotoUrl(String userPhotoUrl) {
        this.userPhotoUrl = userPhotoUrl;
    }

    public void setHashTags(String hashTags) {
        this.hashTags = hashTags;
    }

    public String getPostBody() {
        return postBody;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserPhotoUrl() {
        return userPhotoUrl;
    }

    public String getHashTags() {
        return hashTags;
    }
}
