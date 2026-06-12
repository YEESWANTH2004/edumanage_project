package com.yourname.edumanage.features.signin;

import com.yourname.edumanage.data.dto.LoginRequest;
import com.yourname.edumanage.db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SigninModel {
    public String[] login(LoginRequest request) {
        try (Connection con = DBConnection.get()) {
            PreparedStatement ps = con.prepareStatement(
                    "SELECT id, role FROM users WHERE username=? AND password=?");
            ps.setString(1, request.username);
            ps.setString(2, request.password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new String[]{String.valueOf(rs.getInt("id")), rs.getString("role")};
            }
        } catch (Exception e) {
            System.out.println("Login error: " + e.getMessage());
        }
        return null;
    }
}
