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

    public static void start(Customer cust, Scanner sc) {
        Menu menu = new Menu();

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
            DriverUI.listWithIndex(items);
            int itemChoice = DriverUI.getChoice(sc, "Select item: ", 1, items.size());
            MenuItem selectedItem = items.get(itemChoice - 1);

            int qty = DriverUI.getChoice(sc, "Quantity: ", 1, Integer.MAX_VALUE);
            order.addItem(new OrderItem(selectedItem, qty));

            System.out.print("Add more items? (yes/no): ");
            ordering = sc.nextLine().trim().equalsIgnoreCase("yes");
        }

        // ----- Billing -----
        IDiscount discountStrategy = new FlatDiscount();
        DiscountCalculator dc = new DiscountCalculator(discountStrategy);
        double discount = dc.calculate(order);
        double subtotal = order.getTotalBeforeDiscount();
        double finalTotal = subtotal - discount;

        DriverUI.border();
        System.out.printf("Subtotal : ₹%.2f\n", subtotal);
        System.out.printf("Discount : ₹%.2f\n", discount);
        System.out.printf("Total    : ₹%.2f\n", finalTotal);
        DriverUI.border();

        int payChoice = DriverUI.getChoice(sc, "Select payment 1. 💵 Cash  2. 📱 UPI: ", 1, 2);
        IPayment payMethod = (payChoice == 1) ? new CashPayment() : new UPIPayment();
        new PaymentProcessor(payMethod).process(finalTotal);

        List<IDelivery> partners = UserStore.getDeliveryPartners();
        IDelivery partner = partners.isEmpty() ? new DeliveryService().assign() : partners.get(0);

        new InvoicePrinter().print(order, discount, finalTotal,
                (payMethod instanceof CashPayment) ? "Cash" : "UPI",
                partner.getName(), cust);

        DriverUI.border();
        System.out.println("✅ Order placed successfully. Enjoy your meal!");
        DriverUI.border();
    }
}
