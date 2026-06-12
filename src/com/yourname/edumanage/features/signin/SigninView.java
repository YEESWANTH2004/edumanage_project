package com.yourname.edumanage.features.signin;

import com.yourname.edumanage.data.dto.LoginRequest;

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

        LoginRequest request = new LoginRequest(u, p);
        String[] result = model.login(request);
        if (result == null) {
            System.out.println("Invalid credentials.");
        }
        return result;
    }
}
