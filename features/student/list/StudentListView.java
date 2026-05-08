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
        System.out.printf("%-5s %-25s %-30s %-15s%n", "ID", "Name", "Email", "Phone");
        System.out.println("-".repeat(75));
        for (String[] s : students) {
            System.out.printf("%-5s %-25s %-30s %-15s%n", s[0], s[1], s[2], s[3]);
        }
    }
}
