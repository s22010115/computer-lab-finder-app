package com.s22010115.myproject522513094;
public class Lab {

    private String name;
    private String location;
    private int totalComputers;
    private int availableComputers;
    private String openTime;
    private String closeDay;
    private String status;

    // Constructor
    public Lab(String name, String location, int totalComputers, int availableComputers,
               String openTime, String closeDay, String status) {
        this.name = name;
        this.location = location;
        this.totalComputers = totalComputers;
        this.availableComputers = availableComputers;
        this.openTime = openTime;
        this.closeDay = closeDay;
        this.status = status;
    }

    // Getters
    public String getName() { return name; }
    public String getLocation() { return location; }
    public int getTotalComputers() { return totalComputers; }
    public int getAvailableComputers() { return availableComputers; }
    public String getOpenTime() { return openTime; }
    public String getCloseDay() { return closeDay; }
    public String getStatus() { return status; }
}
