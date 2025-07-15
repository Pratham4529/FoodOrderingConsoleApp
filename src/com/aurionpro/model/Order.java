package com.aurionpro.model;

import java.util.ArrayList;
import java.util.List;

public class Order {

    private List<OrderItem> orderItems;

    public Order() {
        this.orderItems = new ArrayList<>();
    }

    public void addItem(OrderItem newItem) {
        for (OrderItem existingItem : orderItems) {
            if (existingItem.getItem().equals(newItem.getItem())) {
                int updatedQuantity = existingItem.getQuantity() + newItem.getQuantity();
                orderItems.set(orderItems.indexOf(existingItem), new OrderItem(existingItem.getItem(), updatedQuantity));
                return;
            }
        }
        this.orderItems.add(newItem);
    }

    public List<OrderItem> getItems() {
        return orderItems;
    }

    public double getTotalBeforeDiscount() {
        double total = 0;
        for (OrderItem item : orderItems) {
            total += item.getTotal();
        }
        return total;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("Order Summary:\n");
        for (OrderItem item : orderItems) {
            builder.append(item).append("\n");
        }
        builder.append("Subtotal: ₹").append(getTotalBeforeDiscount());
        return builder.toString();
    }
}
