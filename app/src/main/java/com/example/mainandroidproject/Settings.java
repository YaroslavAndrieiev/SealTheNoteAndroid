package com.example.mainandroidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class Settings extends AppCompatActivity {
    private ImageButton menuBtn;
    private ImageButton userProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        menuBtn=findViewById(R.id.btnMenu);
        userProfile=findViewById(R.id.user_profile_btn);
        userProfile.setImageResource(R.drawable.ic_settings);
        menuBtn.setImageResource(R.drawable.ic_btn_arrow_back);
        userProfile.setVisibility(View.INVISIBLE);

        menuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
