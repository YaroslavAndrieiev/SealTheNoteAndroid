package com.example.mainandroidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Login extends AppCompatActivity {


    Button btnLoginOnLoginScreen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        btnLoginOnLoginScreen=findViewById(R.id.btnLogInOnLoginScreen);
        btnLoginOnLoginScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                goToAllNotes();
            }
        });
    }

    private void goToAllNotes() {
        try {
            Intent intent = new Intent(this, AllNotesV2.class);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
