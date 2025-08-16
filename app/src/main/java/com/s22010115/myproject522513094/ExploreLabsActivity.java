package com.s22010115.myproject522513094;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.*;

public class ExploreLabsActivity extends AppCompatActivity {

    private EditText searchBar;
    private Spinner locationSpinner;
    private TextView toggleWordProcessing, toggleDatabase;
    private LinearLayout wordProcessingList, databaseList;
    private BottomNavigationView bottomNavigation;

    private Button btn_direction, btn_orientation;

    // List to store lab software data
    private List<LabSoftware> labSoftwareList = new ArrayList<>();
    private String selectedLocation = "All";  // Default location filter

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore_labs);

        searchBar = findViewById(R.id.search_bar);
        locationSpinner = findViewById(R.id.spinner_location);
        toggleWordProcessing = findViewById(R.id.toggle_word_processing);
        toggleDatabase = findViewById(R.id.toggle_database);
        wordProcessingList = findViewById(R.id.word_processing_list);
        databaseList = findViewById(R.id.database_list);
        bottomNavigation = findViewById(R.id.bottom_navigation);
        btn_direction = findViewById(R.id.btn_direction);
        btn_orientation = findViewById(R.id.btn_orientation);

        // Open direction activity
        btn_direction.setOnClickListener(v -> {
            Intent intent = new Intent(ExploreLabsActivity.this, LocationActivity.class);
            startActivity(intent);
        });

        // Open orientation sensor activity
        btn_orientation.setOnClickListener(v -> {
            Intent intent = new Intent(ExploreLabsActivity.this, SensorActivity.class);
            startActivity(intent);
        });

        // Bottom Navigation
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_home) {
                startActivity(new Intent(ExploreLabsActivity.this, HomeActivity.class));
                return true;
            } else if (itemId == R.id.nav_booking) {
                startActivity(new Intent(ExploreLabsActivity.this, ViewBookingActivity.class));
                return true;
            } else if (itemId == R.id.nav_profile) {
                startActivity(new Intent(ExploreLabsActivity.this, ProfileActivity.class));
                return true;
            }
            return false;
        });

        // Dummy data
        setupDummyData();

        // Location dropdown setup
        setupLocationSpinner();

        // Toggle sections
        toggleWordProcessing.setOnClickListener(v -> toggleSection(wordProcessingList));
        toggleDatabase.setOnClickListener(v -> toggleSection(databaseList));

        // Search action on keyboard "Enter" or Done
        searchBar.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                    actionId == EditorInfo.IME_ACTION_DONE ||
                    event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                performSearch(searchBar.getText().toString().trim());
                return true;
            }
            return false;
        });

        // Location dropdown selection
        locationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedLocation = parent.getItemAtPosition(position).toString();
                performSearch(searchBar.getText().toString().trim());
            }
            @Override public void onNothingSelected(AdapterView<?> parent) { }
        });
    }

    // Toggle visibility of a section (show/hide)
    private void toggleSection(LinearLayout section) {
        section.setVisibility(section.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
    }

    // Populate location spinner with unique values from data
    private void setupLocationSpinner() {
        Set<String> locations = new HashSet<>();
        locations.add("All");
        // Add unique locations from the lab software list
        for (LabSoftware item : labSoftwareList) {
            locations.add(item.getLocation());
        }

        List<String> locationList = new ArrayList<>(locations);
        Collections.sort(locationList);
        // Set adapter to spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, locationList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        locationSpinner.setAdapter(adapter);
    }

    // Perform search based on keyword and selected location
    private void performSearch(String keyword) {
        wordProcessingList.removeAllViews();
        databaseList.removeAllViews();

        boolean found = false;

        for (LabSoftware item : labSoftwareList) {
            if ((selectedLocation.equals("All") || item.getLocation().equalsIgnoreCase(selectedLocation)) &&
                    item.getName().toLowerCase().contains(keyword.toLowerCase())) {

                // Create a new TextView for each matched item
                TextView softwareView = new TextView(this);
                softwareView.setText(item.getName() + " (" + item.getLocation() + ")");
                softwareView.setPadding(20, 10, 20, 10);

                if (item.getCategory().equalsIgnoreCase("Word Processing")) {
                    wordProcessingList.addView(softwareView);
                } else if (item.getCategory().equalsIgnoreCase("Database")) {
                    databaseList.addView(softwareView);
                }

                found = true;
            }
        }

        if (!found) {
            TextView noResult = new TextView(this);
            noResult.setText("No matching software found.");
            wordProcessingList.addView(noResult);
            databaseList.addView(noResult);
        }

        // Show both sections by default after search
        wordProcessingList.setVisibility(View.VISIBLE);
        databaseList.setVisibility(View.VISIBLE);
    }

    // Dummy data for testing
    private void setupDummyData() {
        labSoftwareList.add(new LabSoftware("Nawala", "Word Processing", "MS Word"));
        labSoftwareList.add(new LabSoftware("Nawala", "Database", "MySQL"));
        labSoftwareList.add(new LabSoftware("Matara", "Word Processing", "Google Docs"));
        labSoftwareList.add(new LabSoftware("Matara", "Database", "Oracle"));
        labSoftwareList.add(new LabSoftware("Kandy", "Database", "PostgreSQL"));
        labSoftwareList.add(new LabSoftware("Kandy", "Word Processing", "LibreOffice Writer"));
    }
}