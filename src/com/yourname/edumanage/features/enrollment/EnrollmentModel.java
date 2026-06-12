package com.yourname.edumanage.features.enrollment;

import com.yourname.edumanage.db.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EnrollmentModel {

    // Student applies for a course — status = pending
    public boolean apply(int studentId, int courseId) {
        try (Connection con = DBConnection.get()) {
            // Check if already applied or enrolled
            PreparedStatement check = con.prepareStatement(
                    "SELECT id, status FROM enrollments WHERE student_id=? AND course_id=?");
            check.setInt(1, studentId);
            check.setInt(2, courseId);
            ResultSet rs = check.executeQuery();
            if (rs.next()) {
                System.out.println("You have already applied for this course. Status: " + rs.getString("status"));
                return false;
            }
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO enrollments (student_id, course_id, status, enrolled_date) VALUES (?, ?, 'pending', CURDATE())");
            ps.setInt(1, studentId);
            ps.setInt(2, courseId);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return false;
    }

    // Admin: get all pending applications
    public List<String[]> getPending() {
        List<String[]> list = new ArrayList<>();
        try (Connection con = DBConnection.get()) {
            ResultSet rs = con.createStatement().executeQuery(
                    "SELECT e.id, s.name, c.title, e.enrolled_date " +
                    "FROM enrollments e " +
                    "JOIN students s ON e.student_id = s.id " +
                    "JOIN courses c ON e.course_id = c.id " +
                    "WHERE e.status = 'pending'");
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

    // Admin: approve an application
    public boolean approve(int enrollmentId) {
        return updateStatus(enrollmentId, "approved");
    }

    // Admin: reject an application
    public boolean reject(int enrollmentId) {
        return updateStatus(enrollmentId, "rejected");
    }

    private boolean updateStatus(int enrollmentId, String status) {
        try (Connection con = DBConnection.get()) {
            PreparedStatement ps = con.prepareStatement(
                    "UPDATE enrollments SET status=? WHERE id=?");
            ps.setString(1, status);
            ps.setInt(2, enrollmentId);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return false;
    }

    // Admin: get all enrollments with status
    public List<String[]> getAll() {
        List<String[]> list = new ArrayList<>();
        try (Connection con = DBConnection.get()) {
            ResultSet rs = con.createStatement().executeQuery(
                    "SELECT e.id, s.name, c.title, e.status, e.enrolled_date " +
                    "FROM enrollments e " +
                    "JOIN students s ON e.student_id = s.id " +
                    "JOIN courses c ON e.course_id = c.id " +
                    "ORDER BY e.status");
            while (rs.next()) {
                list.add(new String[]{
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getString("title"),
                        rs.getString("status"),
                        rs.getString("enrolled_date")
                });
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return list;
    }

    // Student: get their own enrollments with status
    public List<String[]> getByStudent(int studentId) {
        List<String[]> list = new ArrayList<>();
        try (Connection con = DBConnection.get()) {
            PreparedStatement ps = con.prepareStatement(
                    "SELECT e.id, c.title, e.status, e.enrolled_date " +
                    "FROM enrollments e JOIN courses c ON e.course_id = c.id " +
                    "WHERE e.student_id = ?");
            ps.setInt(1, studentId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new String[]{
                        rs.getString("id"),
                        rs.getString("title"),
                        rs.getString("status"),
                        rs.getString("enrolled_date")
                });
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return list;
    }

    public boolean delete(int id) {
        try (Connection con = DBConnection.get()) {
            PreparedStatement ps = con.prepareStatement("DELETE FROM enrollments WHERE id=?");
            ps.setInt(1, id);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return false;
    }
}
