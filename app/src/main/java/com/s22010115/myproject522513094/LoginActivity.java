package com.s22010115.myproject522513094;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class LoginActivity extends AppCompatActivity {

    private EditText emailEditText, passwordEditText;
    private Button loginButton;
    private MyDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize views
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        TextView registerTextView = findViewById(R.id.registerTextView);

        // Set click listener for the Register text view
        registerTextView.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

        // Initialize database helper
        dbHelper = new MyDatabaseHelper(this);

        // Set click listener for the login button
        loginButton.setOnClickListener(v -> loginUser());
    }

    // Method to handle login functionality
    private void loginUser() {
        // Get text from email and password fields
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        // Check if email or password is empty
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Open database in readable mode
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // SQL query to find matching email and password
        String query = "SELECT * FROM " + MyDatabaseHelper.TABLE_USERS +
                " WHERE " + MyDatabaseHelper.COLUMN_EMAIL + "=? AND " +
                MyDatabaseHelper.COLUMN_PASSWORD + "=?";

        // Run the query with the provided email and password
        Cursor cursor = db.rawQuery(query, new String[]{email, password});

        // Check if user exists
        if (cursor.moveToFirst()) {
            // Login successful, go to HomeActivity
            Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
            finish();
        } else {
            // Show error if credentials are invalid
            Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show();
        }

        // Close the cursor and database
        cursor.close();
        db.close();
    }
}

