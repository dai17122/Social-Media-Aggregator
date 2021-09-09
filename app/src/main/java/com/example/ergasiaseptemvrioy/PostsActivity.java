package com.example.ergasiaseptemvrioy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

public class PostsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts);
        getSupportActionBar().hide();
        Bundle extras = getIntent().getExtras();
        String searchTerm = extras.getString(MainActivity.SEARCH_TERM_NAME);
        TwitterPostsAdapter adapter = new TwitterPostsAdapter(this, R.layout.post_item, new ArrayList<Post>(), findViewById(R.id.PostListView));
        TwitterCustomHashtag search = new TwitterCustomHashtag(adapter);
        search.setSearchTerm(searchTerm).getPosts();

    }
}
