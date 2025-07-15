package com.aurionpro.service;

import com.aurionpro.model.*;
import java.util.*;

public class Menu {

    private final Map<String, Map<CourseType, List<MenuItem>>> cuisineMap = new HashMap<>();

    public Menu() {
        seedMenuItems();
    }

    private void seedMenuItems() {
        addMenuItem(new MenuItem("Bruschetta", 120, "Italian", CourseType.STARTERS));
        addMenuItem(new MenuItem("Caprese Salad", 110, "Italian", CourseType.STARTERS));
        addMenuItem(new MenuItem("Garlic Bread", 90, "Italian", CourseType.STARTERS));
        addMenuItem(new MenuItem("Stuffed Mushrooms", 140, "Italian", CourseType.STARTERS));
        addMenuItem(new MenuItem("Pasta Salad", 130, "Italian", CourseType.STARTERS));
        addMenuItem(new MenuItem("Margherita Pizza", 250, "Italian", CourseType.MAIN_COURSE));
        addMenuItem(new MenuItem("Pasta Alfredo", 260, "Italian", CourseType.MAIN_COURSE));
        addMenuItem(new MenuItem("Lasagna", 280, "Italian", CourseType.MAIN_COURSE));
        addMenuItem(new MenuItem("Risotto", 240, "Italian", CourseType.MAIN_COURSE));
        addMenuItem(new MenuItem("Gnocchi", 230, "Italian", CourseType.MAIN_COURSE));
        addMenuItem(new MenuItem("Tiramisu", 180, "Italian", CourseType.DESSERTS));
        addMenuItem(new MenuItem("Panna Cotta", 160, "Italian", CourseType.DESSERTS));
        addMenuItem(new MenuItem("Gelato", 150, "Italian", CourseType.DESSERTS));
        addMenuItem(new MenuItem("Cannoli", 170, "Italian", CourseType.DESSERTS));
        addMenuItem(new MenuItem("Fruit Tart", 155, "Italian", CourseType.DESSERTS));
        addMenuItem(new MenuItem("Espresso", 60, "Italian", CourseType.DRINKS));
        addMenuItem(new MenuItem("Limoncello", 90, "Italian", CourseType.DRINKS));
        addMenuItem(new MenuItem("Italian Soda", 80, "Italian", CourseType.DRINKS));
        addMenuItem(new MenuItem("Red Wine", 200, "Italian", CourseType.DRINKS));
        addMenuItem(new MenuItem("Iced Tea", 70, "Italian", CourseType.DRINKS));

        addMenuItem(new MenuItem("Paneer Tikka", 150, "Indian", CourseType.STARTERS));
        addMenuItem(new MenuItem("Samosa", 50, "Indian", CourseType.STARTERS));
        addMenuItem(new MenuItem("Aloo Tikki", 60, "Indian", CourseType.STARTERS));
        addMenuItem(new MenuItem("Hara Bhara Kabab", 100, "Indian", CourseType.STARTERS));
        addMenuItem(new MenuItem("Tandoori Mushroom", 120, "Indian", CourseType.STARTERS));
        addMenuItem(new MenuItem("Butter Chicken", 320, "Indian", CourseType.MAIN_COURSE));
        addMenuItem(new MenuItem("Paneer Butter Masala", 290, "Indian", CourseType.MAIN_COURSE));
        addMenuItem(new MenuItem("Chicken Biryani", 270, "Indian", CourseType.MAIN_COURSE));
        addMenuItem(new MenuItem("Dal Makhani", 220, "Indian", CourseType.MAIN_COURSE));
        addMenuItem(new MenuItem("Palak Paneer", 250, "Indian", CourseType.MAIN_COURSE));
        addMenuItem(new MenuItem("Gulab Jamun", 80, "Indian", CourseType.DESSERTS));
        addMenuItem(new MenuItem("Rasgulla", 90, "Indian", CourseType.DESSERTS));
        addMenuItem(new MenuItem("Kheer", 100, "Indian", CourseType.DESSERTS));
        addMenuItem(new MenuItem("Jalebi", 70, "Indian", CourseType.DESSERTS));
        addMenuItem(new MenuItem("Ladoo", 75, "Indian", CourseType.DESSERTS));
        addMenuItem(new MenuItem("Masala Chai", 40, "Indian", CourseType.DRINKS));
        addMenuItem(new MenuItem("Lassi", 60, "Indian", CourseType.DRINKS));
        addMenuItem(new MenuItem("Filter Coffee", 50, "Indian", CourseType.DRINKS));
        addMenuItem(new MenuItem("Coconut Water", 45, "Indian", CourseType.DRINKS));
        addMenuItem(new MenuItem("Badam Milk", 65, "Indian", CourseType.DRINKS));
    }

    public void addCuisine(String cuisineName) {
        cuisineMap.putIfAbsent(cuisineName, new EnumMap<>(CourseType.class));
        System.out.println("Cuisine '" + cuisineName + "' added.");
    }

    public boolean removeCuisine(String cuisineName) {
        String normalizedInput = cuisineName.trim().toLowerCase();
        String matchedKey = null;
        for (String key : cuisineMap.keySet()) {
            if (key.toLowerCase().equals(normalizedInput)) {
                matchedKey = key;
                break;
            }
        }
        if (matchedKey != null) {
            cuisineMap.remove(matchedKey);
            System.out.println("Cuisine '" + matchedKey + "' removed.");
            return true;
        }
        System.out.println("Cuisine not found.");
        return false;
    }

    public void addMenuItem(MenuItem item) {
        cuisineMap
            .computeIfAbsent(item.getCuisineName(), k -> new EnumMap<>(CourseType.class))
            .computeIfAbsent(item.getCourseType(), k -> new ArrayList<>())
            .add(item);
    }

    public boolean removeMenuItem(String cuisine, CourseType course, int index) {
        Map<CourseType, List<MenuItem>> courseMap = cuisineMap.get(cuisine);
        if (courseMap == null) return false;
        List<MenuItem> list = courseMap.get(course);
        if (list == null || index < 1 || index > list.size()) return false;
        list.remove(index - 1);
        return true;
    }

    public void displayAllCuisines() {
        if (cuisineMap.isEmpty()) {
            System.out.println("No cuisines available.");
            return;
        }
        System.out.println("--- Cuisine List ---");
        int idx = 1;
        for (String c : cuisineMap.keySet()) {
            System.out.println(idx++ + ". " + c);
        }
    }

    public void displayFullMenu() {
        if (cuisineMap.isEmpty()) {
            System.out.println("No cuisines available to display menu.");
            return;
        }
        for (String cuisine : cuisineMap.keySet()) {
            displayCuisineMenu(cuisine);
            System.out.println();
        }
    }

    public void displayCuisineMenu(String cuisine) {
        Map<CourseType, List<MenuItem>> courseMap = cuisineMap.get(cuisine);
        if (courseMap == null) {
            System.out.println("Cuisine not found.");
            return;
        }

        System.out.println("===========================================");
        System.out.printf("           %s MENU%n", cuisine.toUpperCase());
        System.out.println("===========================================");

        for (CourseType ct : CourseType.values()) {
            List<MenuItem> items = courseMap.get(ct);
            if (items == null || items.isEmpty()) continue;

            System.out.printf("\n[%s]\n", ct);
            System.out.println("+----+---------------------------+--------+");
            System.out.printf("| %-2s | %-25s | %-6s |\n", "#", "Item Name", "Price");
            System.out.println("+----+---------------------------+--------+");

            for (int i = 0; i < items.size(); i++) {
                MenuItem mi = items.get(i);
                System.out.printf("| %-2d | %-25s | ₹%-5.0f |\n", i + 1, mi.getName(), mi.getPrice());
            }

            System.out.println("+----+---------------------------+--------+");
        }
    }


    public Map<String, Map<CourseType, List<MenuItem>>> getCuisineMap() {
        return cuisineMap;
    }
}
