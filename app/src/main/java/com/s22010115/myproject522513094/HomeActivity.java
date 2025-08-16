package com.s22010115.myproject522513094;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {

    private Button btnFindOpenLabs, btnBookLabTime, btnExploreLabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btnFindOpenLabs = findViewById(R.id.btnFindOpenLabs);
        btnBookLabTime = findViewById(R.id.btnBookLabTime);
        btnExploreLabs = findViewById(R.id.btnExploreLabs);

        btnFindOpenLabs.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, FindOpenLabsActivity.class);
            startActivity(intent);
        });

        btnBookLabTime.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, BookingActivity.class);
            startActivity(intent);
        });

        btnExploreLabs.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, ExploreLabsActivity.class);
            startActivity(intent);
        });

        // Handle Bottom Navigation
        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigationView);
        bottomNav.setSelectedItemId(R.id.nav_home); // highlight current
        bottomNav.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.nav_home) {
                // Already on Home
                return true;
            } else if (itemId == R.id.nav_booking) {
                startActivity(new Intent(HomeActivity.this, ViewBookingActivity.class));
                return true;
            } else if (itemId == R.id.nav_profile) {
                startActivity(new Intent(HomeActivity.this, ProfileActivity.class));
                return true;
            }

            return false;
        });

    }
}
