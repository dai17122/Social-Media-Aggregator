package com.example.ergasiaseptemvrioy;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;


public class CreatePostActivity extends AppCompatActivity {

    private static final String FACEBOOK_PAGE_ID = "108449131580569";
    private static final String FACEBOOK_BASE_URL_API = "https://graph.facebook.com/v11.0/";
    private static final String FACEBOOK_UPLOAD_POST_URL = FACEBOOK_BASE_URL_API + "/" + FACEBOOK_PAGE_ID + "/feed";
    int SELECT_PICTURE = 200;
    ImageView IVPreviewImage;
    private String imagePath;
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);
        getSupportActionBar().hide();
        Button submit = (Button) findViewById(R.id.submitButton);
        EditText editText = (EditText) findViewById(R.id.postBody);
        IVPreviewImage = findViewById(R.id.IVPreviewImage);

        context = this;
        Button chooser = (Button) findViewById(R.id.choosefile);
        chooser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ActivityCompat.requestPermissions(CreatePostActivity.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    1);
                Intent i = new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
            }


        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String postBody = editText.getText().toString();
                FacebookRequest request = new FacebookRequest(postBody, context);
                request.setImagePath(imagePath);
                request.uploadPost();

            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (data == null) {
                return;
            }
            Uri selectedUri = data.getData();
            Log.d("path", selectedUri.getPath() + "");
            Uri selectedImageURI = data.getData();
            imagePath = PathUtils.getPath(getApplicationContext(), selectedImageURI);


        }
    }

}
