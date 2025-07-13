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
            System.out.println("🛠️  Admin Console");
            DriverUI.border();
            System.out.println("1️⃣  View Customers\t2️⃣  View Cuisines\t3️⃣  Full Menu");
            System.out.println("4️⃣  Add Cuisine   \t5️⃣  Remove Cuisine");
            System.out.println("6️⃣  Add Item      \t7️⃣  Remove Item");
            System.out.println("8️⃣  Discounts     \t9️⃣  Payments       🔟  Delivery");
            System.out.println("0️⃣  Logout");

            int choice = DriverUI.getChoice(sc, "Choose option: ", 0, 10);

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
        UserStore.getUsers().stream()
                .filter(u -> u instanceof Customer)
                .map(u -> (Customer) u)
                .forEach(c -> System.out.println("🆔 " + c.getCustomerId() + " | 👤 " + c.getFullName()));
    }

    private static void addCuisine(Scanner sc, Menu menu) {
        System.out.print("Cuisine name: ");
        menu.addCuisine(sc.nextLine());
    }

    private static void removeCuisine(Scanner sc, Menu menu) {
        List<String> cuisines = new ArrayList<>(menu.getCuisineMap().keySet());
        if (cuisines.isEmpty()) {
            System.out.println("⚠️  No cuisines.");
            return;
        }
        DriverUI.listWithIndex(cuisines);
        int idx = DriverUI.getChoice(sc, "Remove cuisine #: ", 1, cuisines.size());
        menu.removeCuisine(cuisines.get(idx - 1));
    }

    private static void addMenuItem(Scanner sc, Menu menu) {
        System.out.print("Item name: ");
        String name = sc.nextLine();
        System.out.print("Price: ");
        double price = sc.nextDouble();
        sc.nextLine();
        System.out.print("Cuisine: ");
        String cuisine = sc.nextLine();
        CourseType[] ct = CourseType.values();
        DriverUI.listWithIndex(Arrays.asList(ct));
        int typeIdx = DriverUI.getChoice(sc, "Course #: ", 1, ct.length);
        menu.addMenuItem(new MenuItem(name, price, cuisine, ct[typeIdx - 1]));
    }

    private static void removeMenuItem(Scanner sc, Menu menu) {
        var map = menu.getCuisineMap();
        List<String> cuisines = new ArrayList<>(map.keySet());
        DriverUI.listWithIndex(cuisines);
        int cIdx = DriverUI.getChoice(sc, "Cuisine #: ", 1, cuisines.size());
        CourseType[] ct = CourseType.values();
        DriverUI.listWithIndex(Arrays.asList(ct));
        int ctIdx = DriverUI.getChoice(sc, "Course #: ", 1, ct.length);
        List<MenuItem> list = map.get(cuisines.get(cIdx - 1)).get(ct[ctIdx - 1]);
        if (list == null || list.isEmpty()) {
            System.out.println("⚠️  No items.");
            return;
        }
        DriverUI.listWithIndex(list);
        int iIdx = DriverUI.getChoice(sc, "Item #: ", 1, list.size());
        System.out.println("❌ Removed " + list.remove(iIdx - 1).getName());
    }

    // ---------- Strategy Management ----------
    private static <T> void manageStrategies(Scanner sc, List<T> list, String pkg) {
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
