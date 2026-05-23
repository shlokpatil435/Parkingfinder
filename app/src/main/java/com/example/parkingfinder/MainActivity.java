package com.example.parkingfinder;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    TextView txtSlots;
    Button btnBook, btnExit, btnReset, btnLogout;

    SharedPreferences prefs;
    int availableSlots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtSlots = findViewById(R.id.txtSlots);
        btnBook = findViewById(R.id.btnBook);
        btnExit = findViewById(R.id.btnExit);
        btnReset = findViewById(R.id.btnReset);
        btnLogout = findViewById(R.id.btnLogout);

        ImageView profileIcon = findViewById(R.id.profileIcon);

        profileIcon.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, ProfileActivity.class));
        });

        prefs = getSharedPreferences("ParkingData", MODE_PRIVATE);
        availableSlots = prefs.getInt("slots", 10);

        txtSlots.setText("Available Slots: " + availableSlots);

        btnBook.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, MapActivity.class);
            startActivity(i);
        });

        btnExit.setOnClickListener(v -> {

            availableSlots++;
            prefs.edit().putInt("slots", availableSlots).apply();

            txtSlots.setText("Available Slots: " + availableSlots);

            Toast.makeText(this,
                    "Parking Exited",
                    Toast.LENGTH_SHORT).show();
        });

        btnReset.setOnClickListener(v -> {

            availableSlots = 10;
            prefs.edit().putInt("slots", availableSlots).apply();

            txtSlots.setText("Available Slots: " + availableSlots);

            Toast.makeText(this,
                    "Parking Reset",
                    Toast.LENGTH_SHORT).show();
        });

        btnLogout.setOnClickListener(v -> {

            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();

        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        availableSlots = prefs.getInt("slots", 10);
        txtSlots.setText("Available Slots: " + availableSlots);
    }
}