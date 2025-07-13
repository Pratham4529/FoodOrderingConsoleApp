package com.aurionpro.service;

import com.aurionpro.model.Order;
import com.aurionpro.model.OrderItem;
import com.aurionpro.user.Customer;

public class InvoicePrinter {
	
	public void print(Order order, double discount, double finalTotal, String paymentMode, String partnerName, Customer customer) {
	    System.out.println("\n======= INVOICE =======");
	    System.out.println("Customer ID  : " + customer.getCustomerId());
	    System.out.println("Customer Name: " + customer.getFullName());
	    System.out.println("------------------------");
	    for (OrderItem item : order.getItems()) {
	        System.out.println(item.getItem().getName() + " x " + item.getQuantity() + " = ₹" + item.getTotal());
	    }
	    System.out.println("------------------------");
	    System.out.printf("Subtotal     : ₹%.2f%n", order.getTotalBeforeDiscount());
	    System.out.printf("Discount     : ₹%.2f%n", discount);
	    System.out.printf("Total        : ₹%.2f%n", finalTotal);
	    System.out.println("Payment Mode : " + paymentMode);
	    System.out.println("Delivery By  : " + partnerName);
	    System.out.println("========================\n");
	}


}
