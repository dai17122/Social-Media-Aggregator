package com.example.ergasiaseptemvrioy;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class TwitterPostsAdapter extends ArrayAdapter<Post> {

    private List<Post> postList;
    private final LayoutInflater inflater;
    private final int layoutResource;
    private final ListView postListView;
    private final Context context;

    public TwitterPostsAdapter(@NonNull Context context, int resource, @NonNull List<Post> objects, ListView listView) {
        super(context, resource, objects);
        this.postList = objects;
        layoutResource = resource;
        inflater = LayoutInflater.from(context);
        postListView = listView;
        this.context = context;
    }

    @Override
    public int getCount() {
        return this.postList.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(layoutResource, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Post currentPost = this.postList.get(position);
        viewHolder.username.setText(currentPost.getUserName() + "");
        viewHolder.postBody.setText(currentPost.getPostBody() + "");
        viewHolder.hashtags.setText(currentPost.getHashTags() + "");
        URL newurl = null;
//        try {
//            newurl = new URL(currentPost.getUserPhotoUrl());
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
//        Bitmap mIcon_val;
//        try {
//            mIcon_val = BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
//            viewHolder.profilePhoto.setImageBitmap(mIcon_val);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }



        return convertView;
    }

    public void setPostList(List<Post> posts) {
        this.postList = posts;
        this.postListView.setAdapter(this);
    }

    private class ViewHolder {


        final TextView username;
        final TextView hashtags;
        final TextView postBody;

        ViewHolder(View view) {

            username = view.findViewById(R.id.username);
            hashtags = view.findViewById(R.id.hashtags);
            postBody = view.findViewById(R.id.twitterPostBody);
        }
    }

}
