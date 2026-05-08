package com.yourname.edumanage.features.course.details;

import com.yourname.edumanage.features.course.CourseModel;

public class CourseDetailsView {
    private final CourseModel model = new CourseModel();

    public void show(int courseId) {
        String[] c = model.getById(courseId);
        if (c == null) {
            System.out.println("Course not found.");
            return;
        }
        System.out.println("\n--- Course Details ---");
        System.out.println("ID          : " + c[0]);
        System.out.println("Title       : " + c[1]);
        System.out.println("Description : " + c[2]);
        System.out.println("Duration    : " + c[3] + " hrs");
    }
}
