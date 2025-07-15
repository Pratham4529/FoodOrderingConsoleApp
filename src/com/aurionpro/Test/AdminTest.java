package com.aurionpro.Test;

import java.util.*;

import com.aurionpro.delivery.IDelivery;
import com.aurionpro.model.CourseType;
import com.aurionpro.model.MenuItem;
import com.aurionpro.service.Menu;
import com.aurionpro.user.Customer;

public class AdminTest {

    public static void run(Scanner sc, Menu menu) {
        boolean running = true;
        while (running) {
            DriverUI.border();
            System.out.println("🛠️  🧑‍💼 Admin Console Panel");
            DriverUI.border();
            System.out.println("1️⃣  👥 View Customers\t2️⃣  🍱 View Cuisines\t3️⃣  📋 Full Menu");
            System.out.println("4️⃣  ➕ Add Cuisine   \t5️⃣  ❌ Remove Cuisine");
            System.out.println("6️⃣  🍽️ Add Item      \t7️⃣  🗑️ Remove Item");
            System.out.println("8️⃣  🧮 View Discounts \t9️⃣  💳 View Payments");
            System.out.println("🔟  🚚 View DeliveryPartners");
            System.out.println("0️⃣  🔚 Logout");

            int choice = DriverUI.getChoice(sc, "\nChoose an option: ", 0, 10);

            switch (choice) {
                case 0 -> running = false;
                case 1 -> listCustomers();
                case 2 -> menu.displayAllCuisines();
                case 3 -> menu.displayFullMenu();
                case 4 -> addCuisine(sc, menu);
                case 5 -> removeCuisine(sc, menu);
                case 6 -> addMenuItem(sc, menu);
                case 7 -> removeMenuItem(sc, menu);
                case 8 -> manageStrategies(sc, UserStore.getDiscountStrategies(), "discount");
                case 9 -> manageStrategies(sc, UserStore.getPaymentStrategies(), "payment");
                case 10 -> manageDelivery(sc);
            }
        }
    }

    // ---------- CRUD Helpers ----------
    private static void listCustomers() {
        System.out.println("👥 Registered Customers:");
        UserStore.getUsers().stream()
                .filter(u -> u instanceof Customer)
                .map(u -> (Customer) u)
                .forEach(c -> System.out.println("🆔 " + c.getCustomerId() + " | 👤 " + c.getFullName()));
    }

    private static void addCuisine(Scanner sc, Menu menu) {
        System.out.print("🆕 Enter cuisine name: ");
        String input = sc.nextLine();
        String normalized = input.trim().toLowerCase();
        boolean exists = menu.getCuisineMap().keySet().stream()
                .anyMatch(c -> c.toLowerCase().equals(normalized));
        if (exists) {
            System.out.println("⚠️  Cuisine already exists.");
        } else {
            menu.addCuisine(input);
        }
    }

    private static void removeCuisine(Scanner sc, Menu menu) {
        List<String> cuisines = new ArrayList<>(menu.getCuisineMap().keySet());
        if (cuisines.isEmpty()) {
            System.out.println("⚠️  No cuisines available.");
            return;
        }
        System.out.println("📤 Select cuisine to remove:");
        DriverUI.listWithIndex(cuisines);
        int idx = DriverUI.getChoice(sc, "Remove cuisine #: ", 1, cuisines.size());
        menu.removeCuisine(cuisines.get(idx - 1));
    }

    private static void addMenuItem(Scanner sc, Menu menu) {
        System.out.print("🍽️  Enter item name: ");
        String name = sc.nextLine();
        System.out.print("💰 Enter price: ");
        double price = sc.nextDouble();
        sc.nextLine();
        System.out.print("🍴 Enter cuisine: ");
        String cuisine = sc.nextLine();

        String matchedCuisine = menu.getCuisineMap().keySet().stream()
            .filter(c -> c.equalsIgnoreCase(cuisine))
            .findFirst()
            .orElse(cuisine);

        CourseType[] ct = CourseType.values();
        System.out.println("📚 Choose course type:");
        DriverUI.listWithIndex(Arrays.asList(ct));
        int typeIdx = DriverUI.getChoice(sc, "Course #: ", 1, ct.length);
        menu.addMenuItem(new MenuItem(name, price, matchedCuisine, ct[typeIdx - 1]));
        System.out.println("✅ Item added successfully to " + matchedCuisine);
    }

    private static void removeMenuItem(Scanner sc, Menu menu) {
        var map = menu.getCuisineMap();
        List<String> cuisines = new ArrayList<>(map.keySet());
        if (cuisines.isEmpty()) {
            System.out.println("⚠️  No cuisines to display.");
            return;
        }
        System.out.println("🗂️  Select cuisine:");
        DriverUI.listWithIndex(cuisines);
        int cIdx = DriverUI.getChoice(sc, "Cuisine #: ", 1, cuisines.size());

        CourseType[] ct = CourseType.values();
        System.out.println("📚 Choose course type:");
        DriverUI.listWithIndex(Arrays.asList(ct));
        int ctIdx = DriverUI.getChoice(sc, "Course #: ", 1, ct.length);

        List<MenuItem> list = map.get(cuisines.get(cIdx - 1)).get(ct[ctIdx - 1]);
        if (list == null || list.isEmpty()) {
            System.out.println("⚠️  No items found in selected course.");
            return;
        }

        System.out.println("🧾 Select item to remove:");
        DriverUI.listWithIndex(list);
        int iIdx = DriverUI.getChoice(sc, "Item #: ", 1, list.size());
        System.out.println("❌ Removed " + list.remove(iIdx - 1).getName());
    }

    // ---------- Strategy Management ----------
    private static <T> void manageStrategies(Scanner sc, List<T> list, String pkg) {
        System.out.println("🔧 Current Strategies:");
        DriverUI.listWithIndex(list);
        System.out.print("Add class or 'remove #: ': ");
        String input = sc.nextLine();
        if (input.startsWith("remove")) {
            int idx = Integer.parseInt(input.split(" ")[1]) - 1;
            if (idx >= 0 && idx < list.size()) list.remove(idx);
        } else {
            try {
                Class<?> clazz = Class.forName("com.aurionpro." + pkg + "." + input);
                @SuppressWarnings("unchecked")
                T obj = (T) clazz.getDeclaredConstructor().newInstance();
                list.add(obj);
            } catch (Exception e) {
                System.out.println("❗ Invalid class.");
            }
        }
    }

    private static void manageDelivery(Scanner sc) {
        List<IDelivery> list = UserStore.getDeliveryPartners();
        System.out.println("🚚 Available Delivery Partners:");
        DriverUI.listWithIndex(list);
        System.out.print("Add class or 'remove #: ': ");
        String input = sc.nextLine();
        if (input.startsWith("remove")) {
            int idx = Integer.parseInt(input.split(" ")[1]) - 1;
            if (idx >= 0 && idx < list.size()) list.remove(idx);
        } else {
            try {
                Class<?> clazz = Class.forName("com.aurionpro.delivery." + input);
                IDelivery obj = (IDelivery) clazz.getDeclaredConstructor().newInstance();
                list.add(obj);
            } catch (Exception e) {
                System.out.println("❗ Invalid class.");
            }
        }
    }
}
