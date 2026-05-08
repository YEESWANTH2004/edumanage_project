package com.yourname.edumanage.features.signin;

import java.util.Scanner;

public class SigninView {
    private final SigninModel model = new SigninModel();
    private final Scanner sc;

    public SigninView(Scanner sc) {
        this.sc = sc;
    }

    public String[] show() {
        System.out.println("\n=== Login ===");
        System.out.print("Username: ");
        String u = sc.nextLine();
        System.out.print("Password: ");
        String p = sc.nextLine();
        String[] result = model.login(u, p);
        if (result == null) {
            System.out.println("Invalid credentials.");
        }
        return result;
    }
}
