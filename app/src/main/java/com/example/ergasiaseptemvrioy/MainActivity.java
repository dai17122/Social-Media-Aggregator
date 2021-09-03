package com.example.ergasiaseptemvrioy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button submit = (Button) findViewById(R.id.submitButton);
        Switch facebookSwitch = findViewById(R.id.facebookSwitch);
        EditText editText = (EditText)findViewById(R.id.postBody);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            String postBody = editText.getText().toString();
            boolean isOn = facebookSwitch.isChecked();
            if (isOn){
                Log.d("STATE", "sharing to facebook");
            }




            }
        });


    }
}
