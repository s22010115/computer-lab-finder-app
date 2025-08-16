package com.s22010115.myproject522513094;

public class LabSoftware {
    String location;
    String category;
    String name;

    // Constructor to initialize a LabSoftware object with location, category, and name
    public LabSoftware(String location, String category, String name) {
        this.location = location;
        this.category = category;
        this.name = name;
    }

    // Getter method to retrieve the location of the lab where the software is available
    public String getLocation() { return location; }
    // Getter method to retrieve the category of the software
    public String getCategory() { return category; }
    // Getter method to retrieve the name of the software
    public String getName() { return name; }
}
