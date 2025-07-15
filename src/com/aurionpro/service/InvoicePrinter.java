package com.aurionpro.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import com.aurionpro.model.Order;
import com.aurionpro.model.OrderItem;
import com.aurionpro.user.Customer;

public class InvoicePrinter {

    private String generateInvoiceNumber() {
        return "INV-" + UUID.randomUUID()
                            .toString()
                            .substring(0, 8)
                            .toUpperCase();  
    }

    private String getCurrentTimestamp() {
        return LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("dd/MMM/yyyy HH:mm"));
    }


    public void print(Order order,
                      double discount,
                      double finalTotal,
                      String paymentMode,
                      String partnerName,
                      Customer customer) {

        String invoiceNo = generateInvoiceNumber();
        String timestamp = getCurrentTimestamp();

        System.out.println("\n======= INVOICE =======");
        System.out.println("Invoice No   : " + invoiceNo);
        System.out.println("Date / Time  : " + timestamp);
        System.out.println("Customer ID  : " + customer.getCustomerId());
        System.out.println("Customer Name: " + customer.getFullName());
        System.out.println("------------------------");

        for (OrderItem item : order.getItems()) {
            System.out.printf("%-20s x %-2d = ₹%.2f%n",
                    item.getItem().getName(),
                    item.getQuantity(),
                    item.getTotal());
        }

        System.out.println("------------------------");
        System.out.printf("Subtotal         : ₹%.2f%n", order.getTotalBeforeDiscount());
        System.out.printf("Discount         : ₹%.2f%n", discount);
        System.out.printf("Total (with GST) : ₹%.2f%n", finalTotal);
        System.out.println("------------------------");
        System.out.println("Payment Mode     : " + paymentMode);
        System.out.println("Delivery Partner : " + partnerName);
        System.out.println("========================\n");
    }
}
