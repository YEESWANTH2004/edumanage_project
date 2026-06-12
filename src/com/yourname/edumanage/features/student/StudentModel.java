package com.yourname.edumanage.features.student;

import com.yourname.edumanage.db.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentModel {

    public List<String[]> getAll() {
        List<String[]> list = new ArrayList<>();
        try (Connection con = DBConnection.get()) {
            ResultSet rs = con.createStatement().executeQuery(
                    "SELECT id, name, email, phone, dob, gender, domain FROM students");
            while (rs.next()) {
                list.add(new String[]{
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("dob"),
                        rs.getString("gender"),
                        rs.getString("domain")
                });
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return list;
    }

    public String[] getById(int id) {
        try (Connection con = DBConnection.get()) {
            PreparedStatement ps = con.prepareStatement(
                    "SELECT id, name, email, phone, dob, gender, domain FROM students WHERE id=?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new String[]{
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("dob"),
                        rs.getString("gender"),
                        rs.getString("domain")
                };
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }

    public int getStudentIdByUserId(int userId) {
        try (Connection con = DBConnection.get()) {
            PreparedStatement ps = con.prepareStatement(
                    "SELECT id FROM students WHERE user_id=?");
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt("id");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return -1;
    }

    public boolean add(String name, String email, String phone, String dob, String gender, String domain, String username, String password) {
        try (Connection con = DBConnection.get()) {
            PreparedStatement userPs = con.prepareStatement(
                    "INSERT INTO users (username, password, role) VALUES (?, ?, 'student')",
                    PreparedStatement.RETURN_GENERATED_KEYS);
            userPs.setString(1, username);
            userPs.setString(2, password);
            userPs.executeUpdate();

            ResultSet keys = userPs.getGeneratedKeys();
            if (keys.next()) {
                int userId = keys.getInt(1);
                PreparedStatement studentPs = con.prepareStatement(
                        "INSERT INTO students (name, email, phone, dob, gender, domain, user_id) VALUES (?, ?, ?, ?, ?, ?, ?)");
                studentPs.setString(1, name);
                studentPs.setString(2, email);
                studentPs.setString(3, phone);
                studentPs.setString(4, dob);
                studentPs.setString(5, gender);
                studentPs.setString(6, domain);
                studentPs.setInt(7, userId);
                studentPs.executeUpdate();
                return true;
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return false;
    }

    public boolean update(int id, String name, String email, String phone, String dob, String gender, String domain) {
        try (Connection con = DBConnection.get()) {
            PreparedStatement ps = con.prepareStatement(
                    "UPDATE students SET name=?, email=?, phone=?, dob=?, gender=?, domain=? WHERE id=?");
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, phone);
            ps.setString(4, dob);
            ps.setString(5, gender);
            ps.setString(6, domain);
            ps.setInt(7, id);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return false;
    }

    public boolean delete(int id) {
        try (Connection con = DBConnection.get()) {
            PreparedStatement getUser = con.prepareStatement(
                    "SELECT user_id FROM students WHERE id=?");
            getUser.setInt(1, id);
            ResultSet rs = getUser.executeQuery();
            if (rs.next()) {
                int userId = rs.getInt("user_id");
                PreparedStatement delStudent = con.prepareStatement("DELETE FROM students WHERE id=?");
                delStudent.setInt(1, id);
                delStudent.executeUpdate();
                if (userId > 0) {
                    PreparedStatement delUser = con.prepareStatement("DELETE FROM users WHERE id=?");
                    delUser.setInt(1, userId);
                    delUser.executeUpdate();
                }
                return true;
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return false;
    }
}
