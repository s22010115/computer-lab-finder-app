package com.s22010115.myproject522513094;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "MyDatabaseHelper";

    private static final String DATABASE_NAME = "mydatabase.db";
    private static final int DATABASE_VERSION = 2;

    // Users table columns
    public static final String TABLE_USERS = "users";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_REG_NUMBER = "reg_number";

    // Labs table columns
    public static final String TABLE_LABS = "labs";
    public static final String COLUMN_LAB_ID = "id";
    public static final String COLUMN_LAB_NAME = "name";
    public static final String COLUMN_LAB_LOCATION = "location";
    public static final String COLUMN_TOTAL_COMPUTERS = "total_computers";
    public static final String COLUMN_AVAILABLE_COMPUTERS = "available_computers";
    public static final String COLUMN_STATUS = "status";
    public static final String COLUMN_OPEN_TIME = "open_time";
    public static final String COLUMN_CLOSE_DAY = "close_day";

    // Bookings table columns
    public static final String TABLE_BOOKINGS = "bookings";
    public static final String COLUMN_BOOKING_ID = "id";
    public static final String COLUMN_BOOKING_NAME = "name";
    public static final String COLUMN_BOOKING_REG_NUMBER = "reg_number";
    public static final String COLUMN_BOOKING_EMAIL = "email";
    public static final String COLUMN_BOOKING_LAB_LOCATION = "lab_location";
    public static final String COLUMN_BOOKING_DATE = "date";
    public static final String COLUMN_BOOKING_TIME = "time";

    // SQL to create USERS table
    private static final String CREATE_TABLE_USERS =
            "CREATE TABLE " + TABLE_USERS + "(" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_EMAIL + " TEXT NOT NULL UNIQUE, " +
                    COLUMN_PASSWORD + " TEXT NOT NULL, " +
                    COLUMN_NAME + " TEXT, " +
                    COLUMN_REG_NUMBER + " TEXT);";

    // SQL to create LABS table
    private static final String CREATE_TABLE_LABS =
            "CREATE TABLE " + TABLE_LABS + "(" +
                    COLUMN_LAB_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_LAB_NAME + " TEXT, " +
                    COLUMN_LAB_LOCATION + " TEXT, " +
                    COLUMN_TOTAL_COMPUTERS + " INTEGER, " +
                    COLUMN_AVAILABLE_COMPUTERS + " INTEGER, " +
                    COLUMN_STATUS + " TEXT, " +
                    COLUMN_OPEN_TIME + " TEXT, " +
                    COLUMN_CLOSE_DAY + " TEXT);";

    // SQL to create BOOKINGS table
    private static final String CREATE_TABLE_BOOKINGS =
            "CREATE TABLE " + TABLE_BOOKINGS + "(" +
                    COLUMN_BOOKING_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_BOOKING_NAME + " TEXT, " +
                    COLUMN_BOOKING_REG_NUMBER + " TEXT, " +
                    COLUMN_BOOKING_EMAIL + " TEXT, " +
                    COLUMN_BOOKING_LAB_LOCATION + " TEXT, " +
                    COLUMN_BOOKING_DATE + " TEXT, " +
                    COLUMN_BOOKING_TIME + " TEXT);";

    // Constructor
    public MyDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Called when the database is created for the first time
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_LABS);
        db.execSQL(CREATE_TABLE_BOOKINGS);
        insertSampleLabs(db); // Populate LABS table with predefined data
    }

    // Called when database is upgraded
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Add new columns if upgrading from older version
        if (oldVersion < 2) {
            db.execSQL("ALTER TABLE " + TABLE_LABS + " ADD COLUMN " + COLUMN_STATUS + " TEXT;");
        }
    }

    // Inserts predefined lab data
    private void insertSampleLabs(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();

        // Add labs
        cv.put(COLUMN_LAB_NAME, "Nawala Lab 1");
        cv.put(COLUMN_LAB_LOCATION, "Nawala");
        cv.put(COLUMN_TOTAL_COMPUTERS, 30);
        cv.put(COLUMN_AVAILABLE_COMPUTERS, 10);
        cv.put(COLUMN_STATUS, "Open");
        cv.put(COLUMN_OPEN_TIME, "9 AM - 5 PM");
        cv.put(COLUMN_CLOSE_DAY, "Close every Monday");
        db.insert(TABLE_LABS, null, cv);

        cv.clear();
        cv.put(COLUMN_LAB_NAME, "Nawala Lab 2");
        cv.put(COLUMN_LAB_LOCATION, "Nawala");
        cv.put(COLUMN_TOTAL_COMPUTERS, 25);
        cv.put(COLUMN_AVAILABLE_COMPUTERS, 0);
        cv.put(COLUMN_STATUS, "Closed");
        cv.put(COLUMN_OPEN_TIME, "9 AM - 5 PM");
        cv.put(COLUMN_CLOSE_DAY, "Close every Monday");
        db.insert(TABLE_LABS, null, cv);

        cv.clear();
        cv.put(COLUMN_LAB_NAME, "Kandy Lab 1");
        cv.put(COLUMN_LAB_LOCATION, "Kandy");
        cv.put(COLUMN_TOTAL_COMPUTERS, 50);
        cv.put(COLUMN_AVAILABLE_COMPUTERS, 20);
        cv.put(COLUMN_STATUS, "Open");
        cv.put(COLUMN_OPEN_TIME, "9 AM - 5 PM");
        cv.put(COLUMN_CLOSE_DAY, "Saturday");
        db.insert(TABLE_LABS, null, cv);

        cv.clear();
        cv.put(COLUMN_LAB_NAME, "Kandy Lab 2");
        cv.put(COLUMN_LAB_LOCATION, "Kandy");
        cv.put(COLUMN_TOTAL_COMPUTERS, 40);
        cv.put(COLUMN_AVAILABLE_COMPUTERS, 5);
        cv.put(COLUMN_STATUS, "Open");
        cv.put(COLUMN_OPEN_TIME, "9 AM - 5 PM");
        cv.put(COLUMN_CLOSE_DAY, "Sunday");
        db.insert(TABLE_LABS, null, cv);

        cv.clear();
        cv.put(COLUMN_LAB_NAME, "Kandy Lab 3");
        cv.put(COLUMN_LAB_LOCATION, "Kandy");
        cv.put(COLUMN_TOTAL_COMPUTERS, 35);
        cv.put(COLUMN_AVAILABLE_COMPUTERS, 0);
        cv.put(COLUMN_STATUS, "Closed");
        cv.put(COLUMN_OPEN_TIME, "9 AM - 5 PM");
        cv.put(COLUMN_CLOSE_DAY, "Friday");
        db.insert(TABLE_LABS, null, cv);

        cv.clear();
        cv.put(COLUMN_LAB_NAME, "Matara Lab 1");
        cv.put(COLUMN_LAB_LOCATION, "Matara");
        cv.put(COLUMN_TOTAL_COMPUTERS, 20);
        cv.put(COLUMN_AVAILABLE_COMPUTERS, 7);
        cv.put(COLUMN_STATUS, "Open");
        cv.put(COLUMN_OPEN_TIME, "9 AM - 5 PM");
        cv.put(COLUMN_CLOSE_DAY, "Sunday");
        db.insert(TABLE_LABS, null, cv);

        cv.clear();
        cv.put(COLUMN_LAB_NAME, "Matara Lab 2");
        cv.put(COLUMN_LAB_LOCATION, "Matara");
        cv.put(COLUMN_TOTAL_COMPUTERS, 15);
        cv.put(COLUMN_AVAILABLE_COMPUTERS, 15);
        cv.put(COLUMN_STATUS, "Open");
        cv.put(COLUMN_OPEN_TIME, "9 AM - 5 PM");
        cv.put(COLUMN_CLOSE_DAY, "Saturday");
        db.insert(TABLE_LABS, null, cv);

        cv.clear();
        cv.put(COLUMN_LAB_NAME, "Jaffna Lab 1");
        cv.put(COLUMN_LAB_LOCATION, "Jaffna");
        cv.put(COLUMN_TOTAL_COMPUTERS, 40);
        cv.put(COLUMN_AVAILABLE_COMPUTERS, 0);
        cv.put(COLUMN_STATUS, "Closed");
        cv.put(COLUMN_OPEN_TIME, "9 AM - 5 PM");
        cv.put(COLUMN_CLOSE_DAY, "Monday");
        db.insert(TABLE_LABS, null, cv);

        cv.clear();
        cv.put(COLUMN_LAB_NAME, "Jaffna Lab 2");
        cv.put(COLUMN_LAB_LOCATION, "Jaffna");
        cv.put(COLUMN_TOTAL_COMPUTERS, 45);
        cv.put(COLUMN_AVAILABLE_COMPUTERS, 30);
        cv.put(COLUMN_STATUS, "Open");
        cv.put(COLUMN_OPEN_TIME, "10 AM - 4 PM");
        cv.put(COLUMN_CLOSE_DAY, "Sunday");
        db.insert(TABLE_LABS, null, cv);

        Log.d(TAG, "Sample labs inserted.");
    }

    // Inserts a booking into the BOOKINGS table
    public long insertBooking(String name, String regNumber, String email, String labName, String date, String time) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_BOOKING_NAME, name);
        cv.put(COLUMN_BOOKING_REG_NUMBER, regNumber);
        cv.put(COLUMN_BOOKING_EMAIL, email);
        cv.put(COLUMN_BOOKING_LAB_LOCATION, labName);
        cv.put(COLUMN_BOOKING_DATE, date);
        cv.put(COLUMN_BOOKING_TIME, time);
        return db.insert(TABLE_BOOKINGS, null, cv);
    }

    // Retrieves all labs from the LABS table
    public Cursor getAllLabs() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_LABS, null);
    }

    // Retrieves labs filtered by location
    public Cursor getLabsByLocation(String location) {
        SQLiteDatabase db = this.getReadableDatabase();
        location = location.trim();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_LABS +
                        " WHERE " + COLUMN_LAB_LOCATION + " LIKE ? COLLATE NOCASE",
                new String[]{"%" + location + "%"});
        Log.d(TAG, "Labs found for location '" + location + "': " + cursor.getCount());
        return cursor;
    }

    //  Retrieve all bookings
    public Cursor getAllBookings() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_BOOKINGS, null);
    }

    //  Delete booking by ID
    public void deleteBooking(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_BOOKINGS, COLUMN_BOOKING_ID + " = ?", new String[]{String.valueOf(id)});
    }
}
