package com.example.ergasiaseptemvrioy;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;


public class CreatePostActivity extends AppCompatActivity {

    int SELECT_PICTURE = 200;
    ImageView IVPreviewImage;
    private String imagePath;
    private Context context;
    public boolean shareFb, shareInsta, shareTwiiter;
    private Switch facebookSwitch;
    private Switch instagramSwitch;
    private Switch twitterSwitch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);
        getSupportActionBar().hide();
        Button submit = (Button) findViewById(R.id.submitButton);
        EditText editText = (EditText) findViewById(R.id.postBody);
        facebookSwitch = (Switch) findViewById(R.id.facebookSwitch);
        twitterSwitch = (Switch) findViewById(R.id.twitterSwitch);
        instagramSwitch = (Switch) findViewById(R.id.instagramSwitch);
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
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                Log.d("switches", facebookSwitch.isChecked() + "");

                String postBody = editText.getText().toString();
                uploadPosts(postBody, context);
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
            IVPreviewImage.setImageURI(selectedUri);
            imagePath = PathUtils.getPath(getApplicationContext(), selectedImageURI);


        }
    }

    private void checkSwitches() {
        shareFb = facebookSwitch.isChecked();
        shareInsta = instagramSwitch.isChecked();
        shareTwiiter = twitterSwitch.isChecked();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void uploadPosts(String postBody, Context context) {
        checkSwitches();

        if (shareFb) {
            FacebookPost request = new FacebookPost(postBody, context);
            request.setImagePath(imagePath);
            request.uploadPost();
        }

        if (shareTwiiter){
            TwitterPost twitterPost = new TwitterPost(context, postBody, imagePath);
            twitterPost.uploadTwitterPost();
        }
        //αν το imageview δν εχει εικονα, τοτε συμπερασμα ο χρηστης δεν εχει επιλεξει εικονα.
        // Αρα δεν θα ανεβασει  τιποτα στο instagram για προλειψη error
        if (shareInsta && IVPreviewImage.getDrawable() != null){
            InstagramPost cl = new InstagramPost(context);
            cl.setImageUrl(imagePath);
            cl.setPostBody(postBody);
            cl.upload();
        }else{
            Toast.makeText(context, "not uploading to instagram", Toast.LENGTH_SHORT).show();
        }

    }

}
