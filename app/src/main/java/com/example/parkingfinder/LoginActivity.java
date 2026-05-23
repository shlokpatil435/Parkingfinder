package com.example.parkingfinder;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import com.example.parkingfinder.MainActivity;
import com.example.parkingfinder.R;

public class LoginActivity extends AppCompatActivity {

    EditText email, password;
    Button loginBtn;
    TextView goSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        loginBtn = findViewById(R.id.loginBtn);
        goSignup = findViewById(R.id.goSignup);

        loginBtn.setOnClickListener(v -> {
            if(email.getText().toString().isEmpty() ||
                    password.getText().toString().isEmpty()){
                Toast.makeText(this,"Enter all fields",Toast.LENGTH_SHORT).show();
            } else {
                startActivity(new Intent(this, MainActivity.class));
                finish();
            }
        });

        goSignup.setOnClickListener(v ->
                startActivity(new Intent(this, SignupActivity.class)));
    }
}