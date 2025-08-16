package com.s22010115.myproject522513094;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class LocationActivity extends AppCompatActivity {

    private EditText editText_start;
    private EditText getEditText_end;
    private Button btn_direction;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        editText_start= findViewById(R.id.editText_strat);
        getEditText_end= findViewById(R.id.editText_end);
        btn_direction= findViewById(R.id.btn_direction);

        btn_direction.setOnClickListener(v -> {
            String startingPoint = editText_start.getText().toString();
            String endPoint = getEditText_end.getText().toString();

            if (startingPoint.equals("") || endPoint.equals("")){
                Toast.makeText(this, "Please enter starting location & destination",
                        Toast.LENGTH_SHORT).show();
            }else{
                getPath(startingPoint, endPoint);
            }
        });

        // Bottom Navigation
        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigationView);
        bottomNav.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_home) {
                startActivity(new Intent(LocationActivity.this, HomeActivity.class));
                return true;
            } else if (itemId == R.id.nav_booking) {
                startActivity(new Intent(LocationActivity.this, ViewBookingActivity.class));
                return true;
            } else if (itemId == R.id.nav_profile) {
                startActivity(new Intent(LocationActivity.this, ProfileActivity.class));
                return true;
            }
            return false;
        });
    }

    //method opens Google Maps with directions from a starting point to an end point
    private void getPath(String startingPoint, String endPoint){
        try{
            // Construct the URL for Google Maps directions
            Uri uri = Uri.parse("https://www.google.com/maps/dir/" + startingPoint + "/" + "endPoint");
            // Create an intent to open the URL in Google Maps app
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.setPackage("com.google.android.apps.maps");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

        }catch (ActivityNotFoundException exception){
            // If Google Maps app is not installed, open Play Store to install it
            Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps.maps&hl=en&gl=US");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }
}
