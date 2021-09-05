package com.example.ergasiaseptemvrioy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.jacksonandroidnetworking.JacksonParserFactory;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        //Initializing the networking library
        AndroidNetworking.initialize(getApplicationContext());
        AndroidNetworking.setParserFactory(new JacksonParserFactory());

        TrendHashtagAdapter adapter = new TrendHashtagAdapter(this, R.layout.hashtag_item, new ArrayList<Trend>(), findViewById(R.id.HashtagListView));
        TwitterWorldWideTrends twitterWorldWideTrends = new TwitterWorldWideTrends(adapter);
        twitterWorldWideTrends.getTrends();

        Button submit = (Button)findViewById(R.id.submitHashtag);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText  searchValue = (EditText)findViewById(R.id.searchHashtags);
                String term = searchValue.getText().toString();
                if (term.length()  == 0){
                    TextView errors = (TextView)findViewById(R.id.errorMessages);
                    errors.setText("Please write a valid search term");
                }else{

                }
            }
        });




    }
}
