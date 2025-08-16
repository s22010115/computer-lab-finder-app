package com.s22010115.myproject522513094;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class FindOpenLabsActivity extends AppCompatActivity {

    private static final String TAG = "FindOpenLabsActivity";

    private Spinner locationSpinner;
    private RecyclerView recyclerViewLabs;
    private TextView noLabsTextView;

    private MyDatabaseHelper dbHelper;
    private LabAdapter labAdapter;
    private ArrayList<Lab> labList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_open_labs);

        dbHelper = new MyDatabaseHelper(this);

        locationSpinner = findViewById(R.id.spinnerLocation);
        recyclerViewLabs = findViewById(R.id.recyclerViewLabs);
        noLabsTextView = findViewById(R.id.textViewNoLabs);

        recyclerViewLabs.setLayoutManager(new LinearLayoutManager(this));
        labList = new ArrayList<>();
        labAdapter = new LabAdapter(labList);
        recyclerViewLabs.setAdapter(labAdapter);

        String[] locations = {"Nawala", "Kandy", "Matara", "Jaffna"};

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                locations
        );
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        locationSpinner.setAdapter(spinnerAdapter);

        // Initially load labs for the first location
        showLabsByLocation(locations[0]);

        locationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedLocation = parent.getItemAtPosition(position).toString();
                showLabsByLocation(selectedLocation);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        // Bottom Navigation
        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigationView);
        bottomNav.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_home) {
                startActivity(new Intent(FindOpenLabsActivity.this, HomeActivity.class));
                return true;
            } else if (itemId == R.id.nav_booking) {
                startActivity(new Intent(FindOpenLabsActivity.this, ViewBookingActivity.class));
                return true;
            } else if (itemId == R.id.nav_profile) {
                startActivity(new Intent(FindOpenLabsActivity.this, ProfileActivity.class));
                return true;
            }
            return false;
        });
    }

    private void showLabsByLocation(String location) {
        Log.d(TAG, "Searching labs in location: " + location);

        // Query the database for labs in the given location
        Cursor cursor = dbHelper.getLabsByLocation(location);

        // Clear the current lab list to prepare for new data
        labList.clear();

        // Check if the cursor contains any results
        if (cursor != null && cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndexOrThrow(MyDatabaseHelper.COLUMN_LAB_NAME));
                String loc = cursor.getString(cursor.getColumnIndexOrThrow(MyDatabaseHelper.COLUMN_LAB_LOCATION));
                int total = cursor.getInt(cursor.getColumnIndexOrThrow(MyDatabaseHelper.COLUMN_TOTAL_COMPUTERS));
                int available = cursor.getInt(cursor.getColumnIndexOrThrow(MyDatabaseHelper.COLUMN_AVAILABLE_COMPUTERS));
                String open = cursor.getString(cursor.getColumnIndexOrThrow(MyDatabaseHelper.COLUMN_OPEN_TIME));
                String close = cursor.getString(cursor.getColumnIndexOrThrow(MyDatabaseHelper.COLUMN_CLOSE_DAY));
                String status = cursor.getString(cursor.getColumnIndexOrThrow(MyDatabaseHelper.COLUMN_STATUS));

                // Create a Lab object using the retrieved data
                Lab lab = new Lab(name, loc, total, available, open, close, status);
                // Add the lab to the list
                labList.add(lab);
            } while (cursor.moveToNext());

            // Close the cursor to release resources
            cursor.close();
        }

        labAdapter.notifyDataSetChanged();

        // Show or hide the RecyclerView and "no labs found" message
        if (labList.isEmpty()) {
            recyclerViewLabs.setVisibility(View.GONE);
            noLabsTextView.setVisibility(View.VISIBLE);
            noLabsTextView.setText("No labs found at location: " + location);
        } else {
            // Labs found: show the list and hide the message
            noLabsTextView.setVisibility(View.GONE);
            recyclerViewLabs.setVisibility(View.VISIBLE);
        }
    }
}
