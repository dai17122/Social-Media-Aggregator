package com.example.ergasiaseptemvrioy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jacksonandroidnetworking.JacksonParserFactory;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String WRONG_TEXT_MESSAGE = "Please write a valid search term";
    public static final String SEARCH_TERM_NAME="searchTerm";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        //Initializing the networking library
        AndroidNetworking.initialize(getApplicationContext());
        AndroidNetworking.setParserFactory(new JacksonParserFactory());

        FaceInstaAccessToken fb = new FaceInstaAccessToken();
        fb.generateAccessToken();

        TrendHashtagAdapter adapter = new TrendHashtagAdapter(this, R.layout.hashtag_item, new ArrayList<Trend>(), findViewById(R.id.HashtagListView));
        TwitterWorldWideTrends twitterWorldWideTrends = new TwitterWorldWideTrends(adapter);
        twitterWorldWideTrends.getTrends();
        Context context = this;
        Button submit = (Button) findViewById(R.id.submitHashtag);


        FloatingActionButton addPost = (FloatingActionButton)findViewById(R.id.addPost);

       addPost.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
            Intent intent = new Intent(context, CreatePostActivity.class);
            startActivity(intent);
           }
       });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText searchValue = (EditText) findViewById(R.id.searchHashtags);
                String term = searchValue.getText().toString();
                if (term.length() == 0) {
                    TextView errors = (TextView) findViewById(R.id.errorMessages);
                    errors.setText(WRONG_TEXT_MESSAGE);
                } else {
                    Intent intent = new Intent(context, PostsActivity.class);
                    intent.putExtra(SEARCH_TERM_NAME, term);
                    startActivity(intent);
                }
            }
        });
    }
}
