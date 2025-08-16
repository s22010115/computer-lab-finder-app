package com.s22010115.myproject522513094;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ProfileActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "profile_prefs";

    private EditText etName, etReg, etEmail;
    private Button btnEditProfile, btnReview, btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        etName = findViewById(R.id.et_name);
        etReg = findViewById(R.id.et_reg);
        etEmail = findViewById(R.id.et_email);
        btnEditProfile = findViewById(R.id.btn_edit_profile);
        btnReview = findViewById(R.id.btn_review);
        btnSave = findViewById(R.id.btn_save);

        // Disable fields initially
        setEditable(false);
        btnSave.setVisibility(View.GONE);

        // Get data from registration if available
        Intent intent = getIntent();
        String nameFromIntent = intent.getStringExtra("name");
        String regFromIntent = intent.getStringExtra("reg");
        String emailFromIntent = intent.getStringExtra("email");

        if (nameFromIntent != null && !nameFromIntent.isEmpty()) {
            etName.setText(nameFromIntent);
        }

        if (regFromIntent != null && !regFromIntent.isEmpty()) {
            etReg.setText(regFromIntent);
        }

        if (emailFromIntent != null && !emailFromIntent.isEmpty()) {
            etEmail.setText(emailFromIntent);
        }

        // Load previously saved profile data
        loadProfile();

        // Enable editing mode
        btnEditProfile.setOnClickListener(v -> {
            setEditable(true);
            btnSave.setVisibility(View.VISIBLE);
            btnReview.setVisibility(View.GONE);
            Toast.makeText(this, "Editing enabled", Toast.LENGTH_SHORT).show();
        });

        // Save profile data to SharedPreferences
        btnSave.setOnClickListener(v -> saveProfile());

        // Open dialog to write a review
        btnReview.setOnClickListener(v -> showReviewDialog());

        // Handle bottom navigation clicks
        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigationView);
        bottomNav.setSelectedItemId(R.id.nav_profile); // highlight current

        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) {
                startActivity(new Intent(this, HomeActivity.class));
                return true;
            } else if (id == R.id.nav_booking) {
                startActivity(new Intent(this, ViewBookingActivity.class));
                return true;
            } else if (id == R.id.nav_profile) {
                // Already on this screen
                return true;
            }
            return false;
        });
    }

    //Save user profile data into SharedPreferences
    private void saveProfile() {
        String name = etName.getText().toString().trim();
        String reg = etReg.getText().toString().trim();
        String email = etEmail.getText().toString().trim();

        if (name.isEmpty() || reg.isEmpty() || email.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Save data to SharedPreferences
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("name", name);
        editor.putString("reg", reg);
        editor.putString("email", email);
        editor.apply();

        // Disable editing
        setEditable(false);
        btnSave.setVisibility(View.GONE);
        btnReview.setVisibility(View.VISIBLE);

        Toast.makeText(this, "Profile saved successfully!", Toast.LENGTH_SHORT).show();
    }

    //Load saved profile data from SharedPreferences
    private void loadProfile() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        if (etName.getText().toString().isEmpty())
            etName.setText(prefs.getString("name", ""));

        if (etReg.getText().toString().isEmpty())
            etReg.setText(prefs.getString("reg", ""));

        if (etEmail.getText().toString().isEmpty())
            etEmail.setText(prefs.getString("email", ""));
    }

    //Enable or disable the EditText fields
    private void setEditable(boolean enabled) {
        etName.setEnabled(enabled);
        etReg.setEnabled(enabled);
        etEmail.setEnabled(enabled);
    }

    // Show a dialog allowing the user to write a review and give a rating
    private void showReviewDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Write a Review");

        // Inflate custom layout for dialog
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_review, null);
        builder.setView(dialogView);

        // Get references to dialog fields
        EditText etReview = dialogView.findViewById(R.id.et_review);
        RatingBar ratingBar = dialogView.findViewById(R.id.rating_bar);
        ratingBar.setRating(5);

        // Handle Submit button
        builder.setPositiveButton("Submit", (dialog, which) -> {
            String reviewText = etReview.getText().toString().trim();
            float rating = ratingBar.getRating();

            if (!reviewText.isEmpty()) {
                Toast.makeText(this, "Thanks! Stars: " + rating, Toast.LENGTH_SHORT).show();
                // Save or process review as needed
            } else {
                Toast.makeText(this, "Review cannot be empty", Toast.LENGTH_SHORT).show();
            }
        });

        // Handle Cancel button
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        builder.show(); // Show the dialog
    }
}
