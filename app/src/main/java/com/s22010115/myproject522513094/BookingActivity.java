package com.s22010115.myproject522513094;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.text.SimpleDateFormat;
import java.util.*;

public class BookingActivity extends AppCompatActivity {

    private EditText nameEditText, regNumberEditText;
    private Button dateButton, timeButton, confirmButton;
    private Spinner locationSpinner;

    private Calendar selectedCalendar = Calendar.getInstance();
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

    private MyDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        dbHelper = new MyDatabaseHelper(this);

        // Initialize views
        nameEditText = findViewById(R.id.nameEditText);
        regNumberEditText = findViewById(R.id.regNumberEditText);
        dateButton = findViewById(R.id.dateButton);
        timeButton = findViewById(R.id.timeButton);
        confirmButton = findViewById(R.id.confirmButton);
        locationSpinner = findViewById(R.id.locationSpinner);

        setupLocationSpinner();

        // Set selectedCalendar to current date and time explicitly
        selectedCalendar = Calendar.getInstance();



        // Date button to open DatePickerDialog
        dateButton.setOnClickListener(v -> showDatePicker());

        // Time button to open TimePickerDialog
        timeButton.setOnClickListener(v -> showTimePicker());

        // Confirm booking button listener
        confirmButton.setOnClickListener(v -> confirmBooking());

        // Bottom Navigation
        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigationView);
        bottomNav.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_home) {
                startActivity(new Intent(BookingActivity.this, HomeActivity.class));
                return true;
            } else if (itemId == R.id.nav_booking) {
                startActivity(new Intent(BookingActivity.this, ViewBookingActivity.class));
                return true;
            } else if (itemId == R.id.nav_profile) {
                startActivity(new Intent(BookingActivity.this, ProfileActivity.class));
                return true;
            }
            return false;
        });
    }

    private void setupLocationSpinner() {
        List<String> locations = new ArrayList<>();
        locations.add("Select Location");
        locations.add("Nawala");
        locations.add("Kandy");
        locations.add("Matara");
        locations.add("Jaffna");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, locations);
        locationSpinner.setAdapter(adapter);
    }

    private void showDatePicker() {
        new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
            selectedCalendar.set(year, month, dayOfMonth);
            String dateStr = dateFormat.format(selectedCalendar.getTime());
            dateButton.setText(dateStr);



        }, selectedCalendar.get(Calendar.YEAR), selectedCalendar.get(Calendar.MONTH), selectedCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void showTimePicker() {
        new TimePickerDialog(this, (view, hourOfDay, minute) -> {
            selectedCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            selectedCalendar.set(Calendar.MINUTE, minute);
            timeButton.setText(String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute));
        }, selectedCalendar.get(Calendar.HOUR_OF_DAY), selectedCalendar.get(Calendar.MINUTE), true).show();
    }

    private void confirmBooking() {
        String name = nameEditText.getText().toString().trim();
        String regNumber = regNumberEditText.getText().toString().trim();
        String date = dateButton.getText().toString();
        String time = timeButton.getText().toString();
        String location = locationSpinner.getSelectedItem().toString();

        if (name.isEmpty() || regNumber.isEmpty() || date.equals("Select Date") || time.equals("Select Time") || location.equals("Select Location")) {
            Toast.makeText(this, "Please complete all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // FIXED table creation (for safety during dev)
        db.execSQL("CREATE TABLE IF NOT EXISTS bookings (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, reg_number TEXT, date TEXT, time TEXT, lab_location TEXT)");

        // FIXED insert statement
        db.execSQL("INSERT INTO bookings (name, reg_number, date, time, lab_location) VALUES (?, ?, ?, ?, ?)",
                new Object[]{name, regNumber, date, time, location});


        Toast.makeText(this, "Booking Confirmed!", Toast.LENGTH_SHORT).show();

        // Clear fields after booking
        nameEditText.setText("");
        regNumberEditText.setText("");
        dateButton.setText("Select Date");
        timeButton.setText("Select Time");
        locationSpinner.setSelection(0);

    }
}
