package com.s22010115.myproject522513094;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class FeedbackActivity extends AppCompatActivity {

    private RatingBar ratingBar;
    private EditText editFeedback;
    private Button btnSubmit;

    private static final String PREFS_NAME = "FeedbackPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback); // Load layout

        // Initialize views
        ratingBar = findViewById(R.id.ratingBar);
        editFeedback = findViewById(R.id.editFeedback);
        btnSubmit = findViewById(R.id.btnSubmit);

        // Handle submit button click
        btnSubmit.setOnClickListener(v -> {
            float rating = ratingBar.getRating();
            String comment = editFeedback.getText().toString().trim();

            // Validation
            if (comment.isEmpty()) {
                editFeedback.setError("Please enter feedback");
                return;
            }

            // Save feedback locally using SharedPreferences
            saveFeedbackLocally(rating, comment);

            Toast.makeText(this, "Feedback saved locally!", Toast.LENGTH_SHORT).show();
            finish(); // Close the activity
        });
    }

    // Method to save feedback using SharedPreferences
    private void saveFeedbackLocally(float rating, String comment) {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        // Generate a unique key using timestamp
        String key = "feedback_" + System.currentTimeMillis();
        String value = "Rating: " + rating + " | Comment: " + comment;

        editor.putString(key, value);
        editor.apply(); // Save changes
    }
}


