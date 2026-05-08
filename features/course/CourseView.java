package com.yourname.edumanage.features.course;

import java.util.List;
import java.util.Scanner;

public class CourseView {
    private final CourseModel model = new CourseModel();
    private final Scanner sc;

    public CourseView(Scanner sc) {
        this.sc = sc;
    }

    public void showMenu() {
        while (true) {
            System.out.println("\n--- Course Management ---");
            System.out.println("1. List All Courses");
            System.out.println("2. Add Course");
            System.out.println("3. Update Course");
            System.out.println("4. Delete Course");
            System.out.println("0. Back");
            System.out.print("Choice: ");
            String choice = sc.nextLine();
            switch (choice) {
                case "1" -> listAll();
                case "2" -> addCourse();
                case "3" -> updateCourse();
                case "4" -> deleteCourse();
                case "0" -> { return; }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    public void listAll() {
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

    private void addCourse() {
        System.out.print("Title: ");
        String title = sc.nextLine();
        System.out.print("Description: ");
        String desc = sc.nextLine();
        System.out.print("Duration (hours): ");
        int dur = Integer.parseInt(sc.nextLine());
        if (model.add(title, desc, dur)) {
            System.out.println("Course added.");
        } else {
            System.out.println("Failed to add course.");
        }
    }

    private void updateCourse() {
        System.out.print("Course ID to update: ");
        int id = Integer.parseInt(sc.nextLine());
        System.out.print("New Title: ");
        String title = sc.nextLine();
        System.out.print("New Description: ");
        String desc = sc.nextLine();
        System.out.print("New Duration (hours): ");
        int dur = Integer.parseInt(sc.nextLine());
        if (model.update(id, title, desc, dur)) {
            System.out.println("Course updated.");
        } else {
            System.out.println("Failed to update.");
        }
    }

    private void deleteCourse() {
        System.out.print("Course ID to delete: ");
        int id = Integer.parseInt(sc.nextLine());
        if (model.delete(id)) {
            System.out.println("Course deleted.");
        } else {
            System.out.println("Failed to delete.");
        }
    }
}
