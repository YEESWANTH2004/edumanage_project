package com.yourname.edumanage.features.course.list;

import com.yourname.edumanage.features.course.CourseModel;

import java.util.List;

public class CourseListView {
    private final CourseModel model = new CourseModel();

    public void show() {
        List<String[]> courses = model.getAll();
        if (courses.isEmpty()) {
            System.out.println("No courses found.");
            return;
        }
        System.out.printf("%-5s %-25s %-35s %-10s%n", "ID", "Title", "Description", "Duration");
        System.out.println("-".repeat(75));
        for (String[] c : courses) {
            System.out.printf("%-5s %-25s %-35s %-10s%n", c[0], c[1], c[2], c[3] + " hrs");
        }
    }
}
