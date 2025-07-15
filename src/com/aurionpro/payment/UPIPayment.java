package com.aurionpro.payment;

import java.util.Scanner;
import java.util.regex.Pattern;

public class UPIPayment implements IPayment {
    private static final String UPI_REGEX = "^[a-zA-Z0-9._]{3,}@[a-zA-Z]{3,}$";

    @Override
    public void pay(double amount) {
        Scanner scanner = new Scanner(System.in);
        System.out.printf("Total to be paid: ₹%.2f%n", amount);

        String upiId;
        while (true) {
            System.out.print("Enter your UPI ID to proceed (e.g., name@bank): ");
            upiId = scanner.nextLine();

            if (Pattern.matches(UPI_REGEX, upiId)) {
                break;
            } else {
                System.out.println("❌ Invalid UPI ID format. Try again.");
            }
        }

        System.out.println("Processing UPI payment from: " + upiId + "...");
        System.out.println("✅ Payment successful! Thank you for using UPI.");
    }
}
