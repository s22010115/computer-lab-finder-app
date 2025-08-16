package com.s22010115.myproject522513094;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigation = findViewById(R.id.bottomNavigationView);

        // Set a listener for item selection on the bottom navigation
        bottomNavigation.setOnItemSelectedListener(item -> {
            // Get the title of the selected item
            String title = item.getTitle().toString();
            // Handle navigation item selection based on title
            if (title.equals("Home")) {
                Toast.makeText(MainActivity.this, "Home selected", Toast.LENGTH_SHORT).show();
                return true;
            } else if (title.equals("Booking")) {
                Toast.makeText(MainActivity.this, "Booking selected", Toast.LENGTH_SHORT).show();
                return true;
            } else if (title.equals("Profile")) {
                Toast.makeText(MainActivity.this, "Profile selected", Toast.LENGTH_SHORT).show();
                return true;
            }
            // Return false if none matched
            return false;
        });

    }
}



