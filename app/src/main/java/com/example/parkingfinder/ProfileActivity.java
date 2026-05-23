package com.example.parkingfinder;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import com.example.parkingfinder.LoginActivity;
import com.example.parkingfinder.R;

public class ProfileActivity extends AppCompatActivity {

    TextView username, email;
    Button logoutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        logoutBtn = findViewById(R.id.logoutBtn);

        // 👉 demo data (later Firebase use karu shakto)
        username.setText("Shlok Patil");
        email.setText("shlok@gmail.com");

        logoutBtn.setOnClickListener(v -> {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
    }}