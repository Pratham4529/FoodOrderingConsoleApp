package com.aurionpro.Test;

import java.util.Scanner;

import com.aurionpro.service.LoginService;
import com.aurionpro.service.Menu;
import com.aurionpro.user.*;

public class DriverCode {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        LoginService loginService = new LoginService(UserStore.getUsers());
        Menu menu = new Menu(); // Shared Menu instance

        while (true) {
            DriverUI.border();
            System.out.println("  🍔 Welcome to Snack Shell Console App");
            DriverUI.border();

            User user = null;
            while (user == null) {
                System.out.print("\n🔐 Username: ");
                String username = sc.nextLine();
                System.out.print("🔐 Password: ");
                String password = sc.nextLine();
                user = loginService.login(username, password);
                if (user == null) System.out.println("❌ Invalid credentials. Try again.");
            }

            if (user instanceof Admin) {
                AdminTest.run(sc, menu);
            } else if (user instanceof Customer) {
                CustomerTest.start((Customer) user, sc, menu);
                break; 
            }
        }

        sc.close();
    }
}
