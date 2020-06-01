package com.example.mainandroidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {

    Button btrRegister;
    private TextInputEditText firstName;
    private TextInputEditText lastName;
    private TextInputEditText birthday;
    private TextInputEditText email;

    private TextView firstNameTextView;
    private TextView lastNameTextView;
    private TextView birthdayTextView;
    private TextView emailTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firstName = (TextInputEditText) findViewById(R.id.first_name_text_inp);
        lastName = (TextInputEditText) findViewById(R.id.last_name_text_inp);
        birthday = (TextInputEditText) findViewById(R.id.birthday_text_inp);
        lastName = (TextInputEditText) findViewById(R.id.last_name_text_inp);
        email = (TextInputEditText) findViewById(R.id.email_text_inp);

        firstNameTextView = (TextView) findViewById(R.id.first_name_text);
        lastNameTextView = (TextView) findViewById(R.id.last_name_text);
        birthdayTextView = (TextView) findViewById(R.id.birthday_text);
        emailTextView = (TextView) findViewById(R.id.email_text);

        btrRegister=findViewById(R.id.btnRegister);
        btrRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                goToLogin();
            }
        });
    }

    private void goToLogin() {

        String firstNameString = firstName.getText().toString();
        String lastNameString = lastName.getText().toString();
        String birthdayString = birthday.getText().toString();
        String emailString = email.getText().toString();

        firstNameTextView.setTextColor(Color.BLACK);
        lastNameTextView.setTextColor(Color.BLACK);
        birthdayTextView.setTextColor(Color.BLACK);
        emailTextView.setTextColor(Color.BLACK);

        if (firstNameString.isEmpty()) {
            firstNameTextView.setTextColor(Color.RED);
        }  else if (lastNameString.isEmpty()) {
            lastNameTextView.setTextColor(Color.RED);
        } else if (birthdayString.isEmpty()) {
            birthdayTextView.setTextColor(Color.RED);
        } else if (emailString.isEmpty()) {
            emailTextView.setTextColor(Color.RED);
        } else {
            try {
                Intent intent = new Intent(this, Login.class);
                startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
