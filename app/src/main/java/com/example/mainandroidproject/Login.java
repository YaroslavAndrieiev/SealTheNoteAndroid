package com.example.mainandroidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class Login extends AppCompatActivity {

    private TextInputEditText loginText;
    private TextInputEditText passWordText;
    private TextView loginTextView;
    private TextView passwordTextView;
    Button btnLoginOnLoginScreen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginText = (TextInputEditText) findViewById(R.id.login_text_inp);
        passWordText = (TextInputEditText) findViewById(R.id.password_text_inp);
        loginTextView = (TextView) findViewById(R.id.login_text);
        passwordTextView = (TextView) findViewById(R.id.password_text);
        btnLoginOnLoginScreen = findViewById(R.id.btnLogInOnLoginScreen);
        btnLoginOnLoginScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                goToAllNotes();
            }
        });
    }

    private void goToAllNotes() {

        String login = loginText.getText().toString();
        String passWord = passWordText.getText().toString();
        loginTextView.setTextColor(Color.BLACK);
        passwordTextView.setTextColor(Color.BLACK);

        if (login.isEmpty()) {
            loginTextView.setTextColor(Color.RED);
        }  else if (passWord.isEmpty()) {
            passwordTextView.setTextColor(Color.RED);
        } else {
            try {
                Intent intent = new Intent(this, AllNotesV2.class);
                startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


}
