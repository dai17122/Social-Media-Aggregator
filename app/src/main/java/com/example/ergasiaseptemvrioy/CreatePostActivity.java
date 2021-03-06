package com.example.ergasiaseptemvrioy;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
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
    private final String INTENT_TITLE = "Selecte a Picture";
    private final String INSTAGRAM_NO_UPLOAD_MESSAGE = "Instagram needs a photo for a post upload!";
    private final String NO_SOCIAL_MEDIA_SELECTED_MESSAGE = "No Social Media was selected to upload post to";
    private final String INTENT_TYPE = "image/*";


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
                i.setType(INTENT_TYPE);
                i.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(i, INTENT_TITLE), SELECT_PICTURE);
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
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

        if (shareTwiiter) {
            TwitterPost twitterPost = new TwitterPost(context, postBody, imagePath);
            twitterPost.uploadTwitterPost();
        }
        //???? ???? imageview ???? ???????? ????????????, ???????? ???????????????????? ?? ?????????????? ?????? ???????? ???????????????? ????????????.
        // ?????? ?????? ???? ????????????????  ???????????? ?????? instagram ?????? ???????????????? error
        if (shareInsta && IVPreviewImage.getDrawable() != null) {
            InstagramPost cl = new InstagramPost(context);
            cl.setImageUrl(imagePath);
            cl.setPostBody(postBody);
            cl.upload();
        }
        if (shareInsta && IVPreviewImage.getDrawable() == null) {
            Toast.makeText(context, INSTAGRAM_NO_UPLOAD_MESSAGE, Toast.LENGTH_SHORT).show();
        }

        if (!shareInsta && !shareTwiiter && !shareFb) {
            Toast.makeText(context, NO_SOCIAL_MEDIA_SELECTED_MESSAGE, Toast.LENGTH_SHORT).show();
        }


    }
}
