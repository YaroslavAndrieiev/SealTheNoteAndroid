package com.example.mainandroidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class UserProfile extends AppCompatActivity {

    private ImageButton menuBtn;
    private ImageButton userProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        menuBtn=findViewById(R.id.btnMenu);
        userProfile=findViewById(R.id.user_profile_btn);
        userProfile.setImageResource(R.drawable.ic_settings);
        menuBtn.setImageResource(R.drawable.ic_btn_arrow_back);


        menuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        userProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intentUserProfile = new Intent(UserProfile.this, Settings.class);
                    startActivity(intentUserProfile);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
