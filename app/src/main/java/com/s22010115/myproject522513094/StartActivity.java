package com.s22010115.myproject522513094;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.widget.Button;
import android.widget.TextView;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        TextView appNameTextView = findViewById(R.id.appName);

        String fullText = "Computer_LabFinder"; // Full text
        SpannableString spannableString = new SpannableString(fullText);

        // Find the index of the "LabFinder" part
        int start = fullText.indexOf("LabFinder");
        int end = start + "LabFinder".length();

        // Log the indexes to ensure they are correct
        Log.d("StartActivity", "Start index: " + start + ", End index: " + end);

        // Check if the substring is found and apply the color
        if (start != -1) {
            spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#3700B3")), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        // Apply the spannable string to the TextView
        appNameTextView.setText(spannableString);

        // Initialize Login button and set onClick listener
        Button loginButton = findViewById(R.id.btnLogin);
        loginButton.setOnClickListener(v -> navigateToLogin());

        // Initialize Register button and set onClick listener
        Button regButton = findViewById(R.id.btnRegister);
        regButton.setOnClickListener(v -> navigateToRegister());
    }

    // Method to navigate to LoginActivity and RegisterActivity
    private void navigateToLogin() {
        Intent intent = new Intent(StartActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    private void navigateToRegister() {
        Intent intent = new Intent(StartActivity.this, RegisterActivity.class);
        startActivity(intent);
    }
}




