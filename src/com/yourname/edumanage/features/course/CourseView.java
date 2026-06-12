package com.yourname.edumanage.features.course;

import com.yourname.edumanage.features.course.details.CourseDetailsView;
import com.yourname.edumanage.features.course.list.CourseListView;

import java.util.Scanner;

public class CourseView {
    private final CourseModel model = new CourseModel();
    private final CourseListView listView = new CourseListView();
    private final CourseDetailsView detailsView = new CourseDetailsView();
    private final Scanner sc;

    public CourseView(Scanner sc) {
        this.sc = sc;
    }

    public void showMenu() {
        while (true) {
            System.out.println("\n--- Course Management ---");
            System.out.println("1. List All Courses");
            System.out.println("2. View Course Details");
            System.out.println("3. Add Course");
            System.out.println("4. Update Course");
            System.out.println("5. Delete Course");
            System.out.println("0. Back");
            System.out.print("Choice: ");
            String choice = sc.nextLine();
            switch (choice) {
                case "1" -> listView.show();
                case "2" -> viewDetails();
                case "3" -> addCourse();
                case "4" -> updateCourse();
                case "5" -> deleteCourse();
                case "0" -> { return; }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    public void listAll() {
        listView.show();
    }

    private void viewDetails() {
        System.out.print("Course ID: ");
        try {
            int id = Integer.parseInt(sc.nextLine());
            detailsView.show(id);
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID. Please enter a number.");
        }
    }

    private void addCourse() {
        System.out.print("Title: ");
        String title = sc.nextLine();
        System.out.print("Description: ");
        String desc = sc.nextLine();
        System.out.print("Duration (hours): ");
        try {
            int dur = Integer.parseInt(sc.nextLine());
            if (model.add(title, desc, dur)) {
                System.out.println("Course added.");
            } else {
                System.out.println("Failed to add course.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid duration. Please enter a number.");
        }
    }

    private void updateCourse() {
        System.out.print("Course ID to update: ");
        try {
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
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter numbers for ID and duration.");
        }
    }

    private void deleteCourse() {
        System.out.print("Course ID to delete: ");
        try {
            int id = Integer.parseInt(sc.nextLine());
            if (model.delete(id)) {
                System.out.println("Course deleted.");
            } else {
                System.out.println("Failed to delete.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID. Please enter a number.");
        }
    }
}
