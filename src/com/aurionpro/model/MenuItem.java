package com.aurionpro.model;

public class MenuItem {

    private String name;
    private double price;
    private String cuisineName;
    private CourseType courseType;

    public MenuItem(String name, double price, String cuisineName, CourseType courseType) {
        this.name = name;
        this.price = price;
        this.cuisineName = cuisineName;
        this.courseType = courseType;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getCuisineName() {
        return cuisineName;
    }

    public CourseType getCourseType() {
        return courseType;
    }

    @Override
    public String toString() {
        return name + " (" + cuisineName + " - " + courseType + ") - ₹" + price;
    }
}
