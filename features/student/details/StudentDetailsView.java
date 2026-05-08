package com.yourname.edumanage.features.student.details;

import com.yourname.edumanage.features.student.StudentModel;

public class StudentDetailsView {
    private final StudentModel model = new StudentModel();

    public void show(int studentId) {
        String[] s = model.getById(studentId);
        if (s == null) {
            System.out.println("Student not found.");
            return;
        }
        System.out.println("\n--- Student Details ---");
        System.out.println("ID    : " + s[0]);
        System.out.println("Name  : " + s[1]);
        System.out.println("Email : " + s[2]);
        System.out.println("Phone : " + s[3]);
    }
}
