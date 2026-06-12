package com.yourname.edumanage.features.marks;

import com.yourname.edumanage.db.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MarksModel {

    public List<String[]> getByStudent(int studentId) {
        List<String[]> list = new ArrayList<>();
        try (Connection con = DBConnection.get()) {
            PreparedStatement ps = con.prepareStatement(
                    "SELECT m.id, c.title, m.score " +
                    "FROM marks m JOIN courses c ON m.course_id = c.id " +
                    "WHERE m.student_id = ?");
            ps.setInt(1, studentId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new String[]{
                        rs.getString("id"),
                        rs.getString("title"),
                        rs.getString("score")
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
                    "SELECT m.id, s.name, c.title, m.score " +
                    "FROM marks m " +
                    "JOIN students s ON m.student_id = s.id " +
                    "JOIN courses c ON m.course_id = c.id");
            while (rs.next()) {
                list.add(new String[]{
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getString("title"),
                        rs.getString("score")
                });
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return list;
    }

    // Returns enrolled courses for a student: [course_id, course_title]
    public List<String[]> getEnrolledCourses(int studentId) {
        List<String[]> list = new ArrayList<>();
        try (Connection con = DBConnection.get()) {
            PreparedStatement ps = con.prepareStatement(
                    "SELECT c.id, c.title " +
                    "FROM enrollments e JOIN courses c ON e.course_id = c.id " +
                    "WHERE e.student_id = ? AND e.status = 'approved'");
            ps.setInt(1, studentId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new String[]{
                        rs.getString("id"),
                        rs.getString("title")
                });
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return list;
    }

    public boolean addOrUpdate(int studentId, int courseId, double score) {
        try (Connection con = DBConnection.get()) {
            PreparedStatement check = con.prepareStatement(
                    "SELECT id FROM marks WHERE student_id=? AND course_id=?");
            check.setInt(1, studentId);
            check.setInt(2, courseId);
            ResultSet rs = check.executeQuery();
            if (rs.next()) {
                PreparedStatement upd = con.prepareStatement(
                        "UPDATE marks SET score=? WHERE student_id=? AND course_id=?");
                upd.setDouble(1, score);
                upd.setInt(2, studentId);
                upd.setInt(3, courseId);
                upd.executeUpdate();
            } else {
                PreparedStatement ins = con.prepareStatement(
                        "INSERT INTO marks (student_id, course_id, score) VALUES (?, ?, ?)");
                ins.setInt(1, studentId);
                ins.setInt(2, courseId);
                ins.setDouble(3, score);
                ins.executeUpdate();
            }
            return true;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return false;
    }

    public boolean delete(int id) {
        try (Connection con = DBConnection.get()) {
            PreparedStatement ps = con.prepareStatement("DELETE FROM marks WHERE id=?");
            ps.setInt(1, id);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return false;
    }
}
