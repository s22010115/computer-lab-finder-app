package com.s22010115.myproject522513094;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ViewBookingActivity extends AppCompatActivity {

    private TextView txtName, txtRegNo, txtTime, txtDate, txtLocation;
    private MyDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_booking);

        txtName = findViewById(R.id.txtName);
        txtRegNo = findViewById(R.id.txtRegNo);
        txtTime = findViewById(R.id.txtTime);
        txtDate = findViewById(R.id.txtDate);
        txtLocation = findViewById(R.id.txtLocation);

        // Initialize and set up delete button
        AppCompatButton btnDelete = findViewById(R.id.btn_direction);
        btnDelete.setOnClickListener(v -> deleteLatestBooking());

        // Initialize database helper
        dbHelper = new MyDatabaseHelper(this);
        displayLatestBooking(); // Show the most recent booking

        // Setup bottom navigation
        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigationView);
        bottomNav.setSelectedItemId(R.id.nav_booking); // highlight current
        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) {
                startActivity(new Intent(ViewBookingActivity.this, HomeActivity.class));
                return true;
            } else if (id == R.id.nav_booking) {
                return true; // already on booking screen
            } else if (id == R.id.nav_profile) {
                startActivity(new Intent(ViewBookingActivity.this, ProfileActivity.class));
                return true;
            }
            return false;
        });
    }

    // Method to display the latest booking entry from the database
    private void displayLatestBooking() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Query to get the most recent booking
        Cursor cursor = db.rawQuery("SELECT * FROM bookings ORDER BY id DESC LIMIT 1", null);

        if (cursor.moveToFirst()) {
            // Extract booking data from cursor
            String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            String reg = cursor.getString(cursor.getColumnIndexOrThrow("reg_number"));
            String date = cursor.getString(cursor.getColumnIndexOrThrow("date"));
            String time = cursor.getString(cursor.getColumnIndexOrThrow("time"));
            String location = cursor.getString(cursor.getColumnIndexOrThrow("lab_location"));

            // Set the text views with the data
            txtName.setText("Name: " + name);
            txtRegNo.setText("Registration No: " + reg);
            txtDate.setText(date);
            txtTime.setText(time);
            txtLocation.setText(location);
        } else {
            // No booking found
            Toast.makeText(this, "No booking found", Toast.LENGTH_LONG).show();
        }

        cursor.close();
        db.close();
    }

    // Method to delete the most recent booking from the database
    private void deleteLatestBooking() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Delete the booking with the highest ID (latest booking)
        int rowsDeleted = db.delete("bookings", "id = (SELECT MAX(id) FROM bookings)", null);

        if (rowsDeleted > 0) {
            // Successfully deleted
            Toast.makeText(this, "Booking deleted successfully", Toast.LENGTH_SHORT).show();
            // Clear the displayed fields
            txtName.setText("");
            txtRegNo.setText("");
            txtDate.setText("");
            txtTime.setText("");
            txtLocation.setText("");
        } else {
            // No booking to delete
            Toast.makeText(this, "No booking to delete", Toast.LENGTH_SHORT).show();
        }

        db.close();
    }
}


