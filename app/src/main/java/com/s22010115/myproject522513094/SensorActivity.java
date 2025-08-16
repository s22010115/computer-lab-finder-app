package com.s22010115.myproject522513094;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SensorActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor accelerometer, magnetometer;

    private TextView txtAzimuth, txtPitch, txtRoll;
    private float[] gravity, geomagnetic;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);

        txtAzimuth = findViewById(R.id.txtAzimuth);
        txtPitch = findViewById(R.id.txtPitch);
        txtRoll = findViewById(R.id.txtRoll);

        // Initialize sensor manager and get sensors
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        // Bottom Navigation
        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigationView);
        bottomNav.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_home) {
                startActivity(new Intent(SensorActivity.this, HomeActivity.class));
                return true;
            } else if (itemId == R.id.nav_booking) {
                startActivity(new Intent(SensorActivity.this, ViewBookingActivity.class));
                return true;
            } else if (itemId == R.id.nav_profile) {
                startActivity(new Intent(SensorActivity.this, ProfileActivity.class));
                return true;
            }
            return false;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Register listeners for accelerometer and magnetometer
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);
        sensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Unregister sensor listeners to save battery when activity is paused
        sensorManager.unregisterListener(this);
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        // Store sensor values when changed
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
            gravity = event.values;
        if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)
            geomagnetic = event.values;

        // If both sensors have data, calculate orientation
        if (gravity != null && geomagnetic != null) {
            float[] R = new float[9];
            float[] I = new float[9];
            // Get rotation matrix from gravity and magnetic data
            if (SensorManager.getRotationMatrix(R, I, gravity, geomagnetic)) {
                float[] orientation = new float[3]; // Array to hold azimuth, pitch, and roll
                SensorManager.getOrientation(R, orientation);
                // Convert radians to degrees
                float azimuth = (float) Math.toDegrees(orientation[0]); // Direction
                float pitch = (float) Math.toDegrees(orientation[1]); // Up/down tilt
                float roll = (float) Math.toDegrees(orientation[2]); // Left/right tilt

                // Display values on screen
                txtAzimuth.setText("Azimuth: " + Math.round(azimuth));
                txtPitch.setText("Pitch: " + Math.round(pitch));
                txtRoll.setText("Roll: " + Math.round(roll));
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Not used but required by interface
    }
}

