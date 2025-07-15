package com.aurionpro.Test;

import java.util.List;
import java.util.Scanner;

public class DriverUI {
	
	

    public static void border() {
        System.out.println("===========================================");
    }

    public static void listWithIndex(List<?> list) {
        for (int i = 0; i < list.size(); i++) {
            System.out.println((i + 1) + ". " + list.get(i));
        }
    }

    public static int getChoice(Scanner sc, String prompt, int min, int max) {
        int choice;
        while (true) {
            System.out.print(prompt);
            if (sc.hasNextInt()) {
                choice = sc.nextInt();
                sc.nextLine();
                if (choice >= min && choice <= max) return choice;
            } else {
                sc.nextLine();
            }
            System.out.println("❌ Invalid input. Try again.");
        }
    }
}
