package com.yourname.edumanage.features.enrollment;

import com.yourname.edumanage.db.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EnrollmentModel {
    public List<String[]> getByStudent(int studentId) {
        List<String[]> list = new ArrayList<>();
        try (Connection con = DBConnection.get()) {
            PreparedStatement ps = con.prepareStatement(
                    "SELECT e.id, c.title, e.enrolled_date " +
                    "FROM enrollments e JOIN courses c ON e.course_id = c.id " +
                    "WHERE e.student_id = ?");
            ps.setInt(1, studentId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new String[]{
                        rs.getString("id"),
                        rs.getString("title"),
                        rs.getString("enrolled_date")
                });
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return list;
    }

    public List<String[]> getAll() {
        List<String[]> list = new ArrayList<>();
        try (Connection con = DBConnection.get()) {
            ResultSet rs = con.createStatement().executeQuery(
                    "SELECT e.id, s.name, c.title, e.enrolled_date " +
                    "FROM enrollments e " +
                    "JOIN students s ON e.student_id = s.id " +
                    "JOIN courses c ON e.course_id = c.id");
            while (rs.next()) {
                list.add(new String[]{
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getString("title"),
                        rs.getString("enrolled_date")
                });
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return list;
    }

    public boolean enroll(int studentId, int courseId) {
        try (Connection con = DBConnection.get()) {
            PreparedStatement check = con.prepareStatement(
                    "SELECT id FROM enrollments WHERE student_id=? AND course_id=?");
            check.setInt(1, studentId);
            check.setInt(2, courseId);
            if (check.executeQuery().next()) {
                System.out.println("Already enrolled in this course.");
                return false;
            }
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO enrollments (student_id, course_id, enrolled_date) VALUES (?, ?, CURDATE())");
            ps.setInt(1, studentId);
            ps.setInt(2, courseId);
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
                    "DELETE FROM enrollments WHERE id=?");
            ps.setInt(1, id);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return false;
    }
}
