package com.aurionpro.model;

public class OrderItem {

    private MenuItem item;
    private int quantity;

    public OrderItem(MenuItem item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }

    public MenuItem getItem() {
        return item;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getTotal() {
        return item.getPrice() * quantity;
    }

    @Override
    public String toString() {
        return String.format(
            "%s | Cuisine: %s | Course: %s | Qty: %d | ₹%.2f",
            item.getName(),
            item.getCuisineName(),
            item.getCourseType(),
            quantity,
            getTotal()
        );
    }
}
