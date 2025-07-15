package com.aurionpro.Test;

import java.util.*;

import com.aurionpro.delivery.IDelivery;
import com.aurionpro.discount.FlatDiscount;
import com.aurionpro.discount.IDiscount;
import com.aurionpro.model.*;
import com.aurionpro.payment.*;
import com.aurionpro.service.*;
import com.aurionpro.user.Customer;

public class CustomerTest {

    public static void start(Customer cust, Scanner sc, Menu menu) {
        DriverUI.border();
        System.out.println("👋 Hello, " + cust.getFullName() + "! Let's place your order.");
        DriverUI.border();

        Order order = new Order();
        boolean ordering = true;

        while (ordering) {
            Map<String, Map<CourseType, List<MenuItem>>> cuisineMap = menu.getCuisineMap();
            List<String> cuisines = new ArrayList<>(cuisineMap.keySet());

            System.out.println("\n🍽️  Choose a Cuisine");
            DriverUI.listWithIndex(cuisines);
            int cuisineChoice = DriverUI.getChoice(sc, "Select cuisine: ", 1, cuisines.size());
            String selectedCuisine = cuisines.get(cuisineChoice - 1);

            CourseType[] courseTypes = CourseType.values();
            System.out.println("\n🍱 Choose Course Type");
            DriverUI.listWithIndex(Arrays.asList(courseTypes));
            int courseChoice = DriverUI.getChoice(sc, "Select course: ", 1, courseTypes.length);
            CourseType selectedCourse = courseTypes[courseChoice - 1];

            List<MenuItem> items = cuisineMap.get(selectedCuisine).get(selectedCourse);
            if (items == null || items.isEmpty()) {
                System.out.println("⚠️  No items in this course. Try another.");
                continue;
            }

            System.out.println("\n🍔 Items Available");
            System.out.println("+----+------------------------------+--------+");
            System.out.printf("| %-2s | %-28s | %-6s |\n", "#", "Item (Cuisine - Course)", "Price");
            System.out.println("+----+------------------------------+--------+");

            for (int i = 0; i < items.size(); i++) {
                MenuItem item = items.get(i);
                String fullName = String.format("%s (%s - %s)", item.getName(), item.getCuisineName(), item.getCourseType());
                System.out.printf("| %-2d | %-28s | ₹%-5.0f |\n", i + 1, fullName, item.getPrice());
            }

            System.out.println("+----+------------------------------+--------+");
            int itemChoice = DriverUI.getChoice(sc, "Select item: ", 1, items.size());
            MenuItem selectedItem = items.get(itemChoice - 1);

            int qty = DriverUI.getChoice(sc, "Quantity: ", 1, Integer.MAX_VALUE);
            order.addItem(new OrderItem(selectedItem, qty));

            while (true) {
                System.out.print("Add more items? (yes/no): ");
                String response = sc.nextLine().trim().toLowerCase();
                if (response.equals("yes")) {
                    ordering = true;
                    break;
                } else if (response.equals("no")) {
                    ordering = false;
                    break;
                } else {
                    System.out.println("❌ Please enter 'yes' or 'no'.");
                }
            }
        }

        // ----- Billing -----
        IDiscount discountStrategy = new FlatDiscount();
        DiscountCalculator dc = new DiscountCalculator(discountStrategy);
        double discount = dc.calculate(order);
        double subtotal = order.getTotalBeforeDiscount();
        double afterDiscount = subtotal - discount;

        double cgst = subtotal * 0.025;
        double sgst = subtotal * 0.025;
        double handlingFee = 10.0;
        double platformFee = subtotal * 0.02;
        double deliveryCharges = 40.0;

        double total = afterDiscount + cgst + sgst + handlingFee + platformFee + deliveryCharges;

        DriverUI.border();
        System.out.printf("Subtotal         : ₹%.2f\n", subtotal);
        System.out.printf("Discount         : ₹%.2f\n", discount);
        System.out.println("-------------------------------");
        System.out.printf("CGST (2.5%%)      : ₹%.2f\n", cgst);
        System.out.printf("SGST (2.5%%)      : ₹%.2f\n", sgst);
        System.out.printf("Handling Fee     : ₹%.2f\n", handlingFee);
        System.out.printf("Platform Fee     : ₹%.2f\n", platformFee);
        System.out.printf("Delivery Charges : ₹%.2f\n", deliveryCharges);
        System.out.println("-------------------------------");
        System.out.printf("Total            : ₹%.2f\n", total);
        DriverUI.border();

        int payChoice = DriverUI.getChoice(sc, "Select payment 1. 💵 Cash  2. 📱 UPI: ", 1, 2);
        IPayment payMethod = (payChoice == 1) ? new CashPayment() : new UPIPayment();
        new PaymentProcessor(payMethod).process(total);

        List<IDelivery> partners = UserStore.getDeliveryPartners();
        IDelivery partner = partners.isEmpty() ? new DeliveryService().assign() : partners.get(0);

        new InvoicePrinter().print(order, discount, total,
                (payMethod instanceof CashPayment) ? "Cash" : "UPI",
                partner.getName(), cust);

        DriverUI.border();
        System.out.println("✅ Order placed successfully. Enjoy your meal!");
        DriverUI.border();
    }
}
