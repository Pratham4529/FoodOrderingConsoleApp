package com.aurionpro.payment;

import java.util.Scanner;

public class UPIPayment implements IPayment  {
	@Override
	public void pay(double amount) {
	    Scanner scanner = new Scanner(System.in);
	    System.out.printf("Total to be paid: ₹%.2f%n", amount);
	    System.out.print("Enter your UPI ID to proceed with the payment: ");
	    String upiId = scanner.nextLine();
	    System.out.println("Processing UPI payment from: " + upiId + "...");
	    System.out.println("Payment successful! Thank you for using UPI.");
	}


}
