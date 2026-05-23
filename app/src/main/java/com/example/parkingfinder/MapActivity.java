package com.example.parkingfinder;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;

import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

public class MapActivity extends AppCompatActivity
        implements OnMapReadyCallback, PaymentResultListener {

    private GoogleMap mMap;
    private Button btnConfirm, btnNavigate;
    private LatLng selectedLocation = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        btnConfirm = findViewById(R.id.btnConfirm);
        btnNavigate = findViewById(R.id.btnNavigate);

        btnConfirm.setVisibility(View.GONE);
        btnNavigate.setVisibility(View.GONE);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.map);

        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        } else {
            Toast.makeText(this, "Map Fragment Error", Toast.LENGTH_SHORT).show();
        }

        // 👉 CHANGE: Confirm button → Payment
        btnConfirm.setOnClickListener(v -> startPayment());

        btnNavigate.setOnClickListener(v -> startNavigation());
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

        if (googleMap == null) {
            Toast.makeText(this, "Map not loaded", Toast.LENGTH_SHORT).show();
            return;
        }

        mMap = googleMap;

        LatLng roha1 = new LatLng(18.4368, 73.1192);
        LatLng roha2 = new LatLng(18.4395, 73.1150);
        LatLng naga1 = new LatLng(18.5440, 73.1340);
        LatLng naga2 = new LatLng(18.5510, 73.1405);

        mMap.addMarker(new MarkerOptions().position(roha1).title("Roha Parking 1"));
        mMap.addMarker(new MarkerOptions().position(roha2).title("Roha Parking 2"));
        mMap.addMarker(new MarkerOptions().position(naga1).title("Nagothane Parking 1"));
        mMap.addMarker(new MarkerOptions().position(naga2).title("Nagothane Parking 2"));

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(roha1, 13));

        mMap.setOnMarkerClickListener(marker -> {
            selectedLocation = marker.getPosition();
            btnConfirm.setVisibility(View.VISIBLE);
            btnNavigate.setVisibility(View.GONE);
            return true;
        });
    }

    // ✅ PAYMENT METHOD
    private void startPayment() {

        if (selectedLocation == null) {
            Toast.makeText(this, "Select parking first", Toast.LENGTH_SHORT).show();
            return;
        }

        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_test_SXv5x9QbgfWV3V");

        try {
            JSONObject options = new JSONObject();

            options.put("name", "Smart Parking");
            options.put("description", "Parking Slot Booking");
            options.put("currency", "INR");
            options.put("amount", 5000); // ₹50

            JSONObject preFill = new JSONObject();
            preFill.put("email", "test@razorpay.com");
            preFill.put("contact", "9999999999");

            options.put("prefill", preFill);

            checkout.open(this, options);

        } catch (Exception e) {
            Toast.makeText(this, "Payment Error", Toast.LENGTH_SHORT).show();
        }
    }

    // ✅ PAYMENT SUCCESS
    @Override
    public void onPaymentSuccess(String razorpayPaymentID) {

        Toast.makeText(this, "Payment Successful 🎉", Toast.LENGTH_SHORT).show();

        confirmParking();
    }

    // ❌ PAYMENT FAILED
    @Override
    public void onPaymentError(int code, String response) {

        Toast.makeText(this, "Payment Failed ❌", Toast.LENGTH_SHORT).show();
    }

    // 🔹 Parking confirm
    private void confirmParking() {

        if (selectedLocation == null) return;

        SharedPreferences prefs = getSharedPreferences("ParkingData", MODE_PRIVATE);
        int slots = prefs.getInt("slots", 10);

        if (slots > 0) {
            slots--;
            prefs.edit().putInt("slots", slots).apply();
        }

        Toast.makeText(this, "Parking Confirmed ✔", Toast.LENGTH_SHORT).show();

        btnConfirm.setVisibility(View.GONE);
        btnNavigate.setVisibility(View.VISIBLE);
    }

    // 🔹 Navigation
    private void startNavigation() {

        if (selectedLocation == null) {
            Toast.makeText(this, "Confirm parking first", Toast.LENGTH_SHORT).show();
            return;
        }

        String uri = "google.navigation:q="
                + selectedLocation.latitude + ","
                + selectedLocation.longitude;

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        intent.setPackage("com.google.android.apps.maps");

        if (intent.resolveActivity(getPackageManager()) != null)
            startActivity(intent);
        else
            Toast.makeText(this, "Google Maps not installed", Toast.LENGTH_SHORT).show();
    }
}