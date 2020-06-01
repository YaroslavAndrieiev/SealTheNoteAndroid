package com.example.mainandroidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
Button btrRegister;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btrRegister=findViewById(R.id.btnRegister);
        btrRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                goToLogin();
            }
        });
    }

    private void goToLogin() {
        try {
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
