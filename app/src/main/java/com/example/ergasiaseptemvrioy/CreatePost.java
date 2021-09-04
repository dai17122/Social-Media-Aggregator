package com.example.ergasiaseptemvrioy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

public class CreatePost extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

        Button submit = (Button) findViewById(R.id.submitButton);
        Switch facebookSwitch = findViewById(R.id.facebookSwitch);
        Switch twitterSwitch = findViewById(R.id.twitterSwitch);
        Switch instagramSwitch = findViewById(R.id.instagramSwitch);
        EditText editText = (EditText)findViewById(R.id.postBody);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserInput userInput = new UserInput(facebookSwitch, twitterSwitch, instagramSwitch, editText);
                userInput.checkToShareFacebook()
                    .checkToShareTwitter()
                    .checkToShareInstagram()
                    .share();


                String postBody = editText.getText().toString();
                boolean isOn = facebookSwitch.isChecked();

            }
        });
    }
}
