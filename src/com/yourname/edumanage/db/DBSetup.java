package com.yourname.edumanage.db;

import java.sql.Connection;
import java.sql.Statement;

public class DBSetup {
    public static void createTables() {
        String users = "CREATE TABLE IF NOT EXISTS users (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "username VARCHAR(50) UNIQUE NOT NULL," +
                "password VARCHAR(100) NOT NULL," +
                "role VARCHAR(10) NOT NULL" +
                ")";

        String students = "CREATE TABLE IF NOT EXISTS students (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "name VARCHAR(100) NOT NULL," +
                "email VARCHAR(100) UNIQUE NOT NULL," +
                "phone VARCHAR(15)," +
                "dob DATE," +
                "gender VARCHAR(10)," +
                "domain VARCHAR(100)," +
                "user_id INT," +
                "FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE SET NULL" +
                ")";

        String courses = "CREATE TABLE IF NOT EXISTS courses (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "title VARCHAR(100) NOT NULL," +
                "description VARCHAR(255)," +
                "duration INT" +
                ")";

        String enrollments = "CREATE TABLE IF NOT EXISTS enrollments (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "student_id INT NOT NULL," +
                "course_id INT NOT NULL," +
                "status VARCHAR(20) DEFAULT 'pending'," +
                "enrolled_date DATE," +
                "FOREIGN KEY (student_id) REFERENCES students(id) ON DELETE CASCADE," +
                "FOREIGN KEY (course_id) REFERENCES courses(id) ON DELETE CASCADE" +
                ")";

        String marks = "CREATE TABLE IF NOT EXISTS marks (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "student_id INT NOT NULL," +
                "course_id INT NOT NULL," +
                "score DOUBLE NOT NULL," +
                "FOREIGN KEY (student_id) REFERENCES students(id) ON DELETE CASCADE," +
                "FOREIGN KEY (course_id) REFERENCES courses(id) ON DELETE CASCADE" +
                ")";

        try (Connection con = DBConnection.get();
             Statement st = con.createStatement()) {
            st.execute(users);
            st.execute(students);
            st.execute(courses);
            st.execute(enrollments);
            st.execute(marks);

            var rs = st.executeQuery("SELECT COUNT(*) FROM users WHERE role='admin'");
            rs.next();
            if (rs.getInt(1) == 0) {
                st.execute("INSERT INTO users (username, password, role) VALUES ('admin', 'admin123', 'admin')");
            }
            System.out.println("Database ready.");
        } catch (Exception e) {
            System.out.println("DB Setup Error: " + e.getMessage());
        }
    }
}