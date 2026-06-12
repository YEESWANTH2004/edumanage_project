package com.yourname.edumanage.features.student.list;

import com.yourname.edumanage.features.student.StudentModel;

import java.util.List;

public class StudentListView {
    private final StudentModel model = new StudentModel();

    public void show() {
        List<String[]> students = model.getAll();
        if (students.isEmpty()) {
            System.out.println("No students found.");
            return;
        }
        System.out.printf("%-5s %-20s %-25s %-15s %-12s %-10s %-20s%n",
                "ID", "Name", "Email", "Phone", "DOB", "Gender", "Domain");
        System.out.println("-".repeat(107));
        for (String[] s : students) {
            System.out.printf("%-5s %-20s %-25s %-15s %-12s %-10s %-20s%n",
                    s[0], s[1], s[2], s[3], s[4], s[5], s[6]);
        }
    }
}
