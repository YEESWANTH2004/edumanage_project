package com.yourname.edumanage.features.course;

import com.yourname.edumanage.db.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseModel {
    public List<String[]> getAll() {
        List<String[]> list = new ArrayList<>();
        try (Connection con = DBConnection.get()) {
            ResultSet rs = con.createStatement().executeQuery(
                    "SELECT id, title, description, duration FROM courses");
            while (rs.next()) {
                list.add(new String[]{
                        rs.getString("id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getString("duration")
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
                    "SELECT id, title, description, duration FROM courses WHERE id=?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new String[]{
                        rs.getString("id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getString("duration")
                };
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }

    public boolean add(String title, String description, int duration) {
        try (Connection con = DBConnection.get()) {
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO courses (title, description, duration) VALUES (?, ?, ?)");
            ps.setString(1, title);
            ps.setString(2, description);
            ps.setInt(3, duration);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return false;
    }

    public boolean update(int id, String title, String description, int duration) {
        try (Connection con = DBConnection.get()) {
            PreparedStatement ps = con.prepareStatement(
                    "UPDATE courses SET title=?, description=?, duration=? WHERE id=?");
            ps.setString(1, title);
            ps.setString(2, description);
            ps.setInt(3, duration);
            ps.setInt(4, id);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return false;
    }

    public boolean delete(int id) {
        try (Connection con = DBConnection.get()) {
            PreparedStatement ps = con.prepareStatement(
                    "DELETE FROM courses WHERE id=?");
            ps.setInt(1, id);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return false;
    }
}
