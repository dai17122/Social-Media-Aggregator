package com.example.ergasiaseptemvrioy;

import android.widget.EditText;
import android.widget.Switch;

public class UserInput {


    private Switch facebookSwitch, TwitterSwitch, InstagramSwitch;
    private EditText postTextField;

    //the final fields that we want to reach
    private boolean shareToFacebook;
    private boolean shareTwitter;
    private boolean isShareInstagram;
    private String postText;
    private String imagePath;


    public UserInput(Switch facebookSwitch, Switch twitterSwitch, Switch instagramSwitch, EditText postTextField) {
        this.facebookSwitch = facebookSwitch;
        this.TwitterSwitch = twitterSwitch;
        this.InstagramSwitch = instagramSwitch;
        this.postTextField = postTextField;
    }

    public UserInput checkToShareFacebook() {

        this.shareToFacebook = this.facebookSwitch.isChecked();
        return this;
    }

    public UserInput checkToShareTwitter() {

        this.shareTwitter = this.facebookSwitch.isChecked();
        return this;
    }

    public UserInput checkToShareInstagram() {
        this.isShareInstagram = this.InstagramSwitch.isChecked();
        return this;
    }

    public void share(){

        if(this.shareToFacebook){

        }

    }
}
