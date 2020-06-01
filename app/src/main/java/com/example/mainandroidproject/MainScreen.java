package com.example.mainandroidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainScreen extends AppCompatActivity {

    Button btnLogin;
    Button btnRegister;
//    private int autorizedState=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

//        if (savedInstanceState == null) {
//            // приложение запущено впервые
//            Toast.makeText(MainScreen.this, "ЭТО 1 ЗАПУСК!!", Toast.LENGTH_SHORT).show();
//        }
//        else {
//            // приложение восстановлено из памяти
//            Toast.makeText(MainScreen.this, "ЭТО Н_Е 1 ЗАПУСК!!", Toast.LENGTH_SHORT).show();
//        }

        btnLogin=findViewById(R.id.btnLogIn);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showLoginWindow();
            }
        });

        btnRegister=findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showRegisterWindow();
            }
        });

}

    private void showRegisterWindow() {
        try {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showLoginWindow() {
        try {
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
