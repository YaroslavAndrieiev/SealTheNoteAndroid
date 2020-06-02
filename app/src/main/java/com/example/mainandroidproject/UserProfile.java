package com.example.mainandroidproject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.gson.Gson;

public class UserProfile extends AppCompatActivity {

    private ImageButton menuBtn;
    private ImageButton userProfile;
    private ImageButton userPhoto;

    private final static  int REQUEST_CODE_USER_PHOTO = 7;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        userPhoto = (ImageButton) findViewById(R.id.bntPersonCircle);
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
        userPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, REQUEST_CODE_USER_PHOTO);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_USER_PHOTO && resultCode == RESULT_OK && null != data) {
            //1)Создание картинки
            //2)Сохранение пути к выделенной картинке из галереи
            //3)Установка изображения на ImageView

            Uri selectedImage = data.getData();
            userPhoto.setImageURI(selectedImage);
            userPhoto.setScaleType(ImageButton.ScaleType.FIT_CENTER);
        }
    }
}
