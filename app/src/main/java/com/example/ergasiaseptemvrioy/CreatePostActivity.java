package com.example.ergasiaseptemvrioy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CreatePostActivity extends AppCompatActivity {

    private static String FACEBOOK_PAGE_ID = "108449131580569";
    private static String FACEBOOK_BASE_URL_API = "https://graph.facebook.com/v11.0/";
    private static String FACEBOOK_UPLOAD_POST_URL = FACEBOOK_BASE_URL_API + "/" + FACEBOOK_PAGE_ID + "/feed";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);
        getSupportActionBar().hide();

        Button submit = (Button) findViewById(R.id.submitButton);
        Switch facebookSwitch = findViewById(R.id.facebookSwitch);
        Switch twitterSwitch = findViewById(R.id.twitterSwitch);
        Switch instagramSwitch = findViewById(R.id.instagramSwitch);
        EditText editText = (EditText) findViewById(R.id.postBody);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                UserInput userInput = new UserInput(facebookSwitch, twitterSwitch, instagramSwitch, editText);
//                userInput.checkToShareFacebook()
//                    .checkToShareTwitter()
//                    .checkToShareInstagram()
//                    .share();
                String postBody = editText.getText().toString();
                FacebookRequest request = new FacebookRequest(postBody, getApplicationContext());
                String response = request.uploadPost();
            }
        });
    }
}
