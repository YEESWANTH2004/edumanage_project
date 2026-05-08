package com.yourname.edumanage.features.signup;

import java.util.Scanner;

public class SignupView {
    private final SignupModel model = new SignupModel();
    private final Scanner sc;

    public SignupView(Scanner sc) {
        this.sc = sc;
    }

    public void show() {
        System.out.println("\n=== Register ===");
        System.out.print("Username: ");
        String u = sc.nextLine();
        System.out.print("Password: ");
        String p = sc.nextLine();
        System.out.print("Full Name: ");
        String name = sc.nextLine();
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Phone: ");
        String phone = sc.nextLine();
        if (model.register(u, p, name, email, phone)) {
            System.out.println("Registered successfully! Please login.");
        } else {
            System.out.println("Registration failed.");
        }
    }
}
