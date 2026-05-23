package com.example.parkingfinder;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

public class SignupActivity extends AppCompatActivity {

    EditText name,email,password;
    Button signupBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        name = findViewById(R.id.name);
        email = findViewById(R.id.emailSignup);
        password = findViewById(R.id.passwordSignup);
        signupBtn = findViewById(R.id.signupBtn);

        signupBtn.setOnClickListener(v -> {
            if(name.getText().toString().isEmpty() ||
                    email.getText().toString().isEmpty() ||
                    password.getText().toString().isEmpty()){
                Toast.makeText(this,"Fill all fields",Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this,"Account Created",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, LoginActivity.class));
                finish();
            }
        });
    }
}