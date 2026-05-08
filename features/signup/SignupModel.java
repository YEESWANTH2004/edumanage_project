package com.yourname.edumanage.features.signup;

import com.yourname.edumanage.db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SignupModel {
    public boolean register(String username, String password, String name, String email, String phone) {
        try (Connection con = DBConnection.get()) {
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO users (username, password, role) VALUES (?, ?, 'student')",
                    PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, username);
            ps.setString(2, password);
            ps.executeUpdate();
            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next()) {
                int uid = keys.getInt(1);
                PreparedStatement ps2 = con.prepareStatement(
                        "INSERT INTO students (name, email, phone, user_id) VALUES (?, ?, ?, ?)");
                ps2.setString(1, name);
                ps2.setString(2, email);
                ps2.setString(3, phone);
                ps2.setInt(4, uid);
                ps2.executeUpdate();
                return true;
            }
        } catch (Exception e) {
            System.out.println("Signup error: " + e.getMessage());
        }
        return false;
    }
}
