package com.s22010115.myproject522513094;

public class Booking {
    private String bookingId;
    private String userId;
    private String labId;
    private String labName;
    private String studentName;
    private String registerNumber;
    private String bookingDate;
    private String bookingTime;
    private String duration;
    private String status;
    private long timestamp;
    private String purpose;

    // Empty constructor required for Firebase
    public Booking() {
    }

    // Constructor with all fields
    public Booking(String bookingId, String userId, String labId, String labName,
                   String studentName, String registerNumber, String bookingDate,
                   String bookingTime, String duration, String status, long timestamp,
                   String purpose) {
        this.bookingId = bookingId;
        this.userId = userId;
        this.labId = labId;
        this.labName = labName;
        this.studentName = studentName;
        this.registerNumber = registerNumber;
        this.bookingDate = bookingDate;
        this.bookingTime = bookingTime;
        this.duration = duration;
        this.status = status;
        this.timestamp = timestamp;
        this.purpose = purpose;
    }

    // Getters and Setters for all fields
    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLabId() {
        return labId;
    }

    public void setLabId(String labId) {
        this.labId = labId;
    }

    public String getLabName() {
        return labName;
    }

    public void setLabName(String labName) {
        this.labName = labName;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getRegisterNumber() {
        return registerNumber;
    }

    public void setRegisterNumber(String registerNumber) {
        this.registerNumber = registerNumber;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getBookingTime() {
        return bookingTime;
    }

    public void setBookingTime(String bookingTime) {
        this.bookingTime = bookingTime;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    // Returns a string representation of the booking
    @Override
    public String toString() {
        return "Booking{" +
                "bookingId='" + bookingId + '\'' +
                ", userId='" + userId + '\'' +
                ", labId='" + labId + '\'' +
                ", labName='" + labName + '\'' +
                ", studentName='" + studentName + '\'' +
                ", registerNumber='" + registerNumber + '\'' +
                ", bookingDate='" + bookingDate + '\'' +
                ", bookingTime='" + bookingTime + '\'' +
                ", duration='" + duration + '\'' +
                ", status='" + status + '\'' +
                ", timestamp=" + timestamp +
                ", purpose='" + purpose + '\'' +
                '}';
    }
}
