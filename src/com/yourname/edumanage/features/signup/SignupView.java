package com.yourname.edumanage.features.signup;

import java.util.Scanner;

public class SignupView {
    private final SignupModel model = new SignupModel();
    private final Scanner sc;

    public SignupView(Scanner sc) {
        this.sc = sc;
    }

    public void show() {
        System.out.println("\n=== Student Registration ===");
        System.out.print("Full Name: ");
        String name = sc.nextLine();
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Phone: ");
        String phone = sc.nextLine();
        System.out.print("Date of Birth (YYYY-MM-DD): ");
        String dob = sc.nextLine();
        System.out.print("Gender (Male/Female/Other): ");
        String gender = sc.nextLine();
        System.out.print("Domain (e.g. Computer Science, Business): ");
        String domain = sc.nextLine();
        System.out.print("Username: ");
        String username = sc.nextLine();
        System.out.print("Password: ");
        String password = sc.nextLine();

        if (model.register(username, password, name, email, phone, dob, gender, domain)) {
            System.out.println("Registration successful! You can now login.");
        } else {
            System.out.println("Registration failed. Username or email may already exist.");
        }
    }
}
