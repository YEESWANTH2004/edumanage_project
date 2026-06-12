package com.yourname.edumanage.features.enrollment;

import com.yourname.edumanage.features.course.CourseModel;

import java.util.List;
import java.util.Scanner;

public class EnrollmentView {
    private final EnrollmentModel model = new EnrollmentModel();
    private final CourseModel courseModel = new CourseModel();
    private final Scanner sc;

    public EnrollmentView(Scanner sc) {
        this.sc = sc;
    }

    // Admin menu
    public void showAdminMenu() {
        while (true) {
            System.out.println("\n--- Enrollment Management ---");
            System.out.println("1. View All Enrollments");
            System.out.println("2. View Pending Applications");
            System.out.println("3. Approve Application");
            System.out.println("4. Reject Application");
            System.out.println("5. Remove Enrollment");
            System.out.println("0. Back");
            System.out.print("Choice: ");
            String choice = sc.nextLine();
            switch (choice) {
                case "1" -> listAll();
                case "2" -> listPending();
                case "3" -> approveApplication();
                case "4" -> rejectApplication();
                case "5" -> removeEnrollment();
                case "0" -> { return; }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    // Student menu
    public void showStudentMenu(int studentId) {
        while (true) {
            System.out.println("\n--- My Enrollments ---");
            System.out.println("1. View My Applications");
            System.out.println("2. Apply for a Course");
            System.out.println("0. Back");
            System.out.print("Choice: ");
            String choice = sc.nextLine();
            switch (choice) {
                case "1" -> listByStudent(studentId);
                case "2" -> applyForCourse(studentId);
                case "0" -> { return; }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private void listAll() {
        List<String[]> list = model.getAll();
        if (list.isEmpty()) {
            System.out.println("No enrollments found.");
            return;
        }
        System.out.printf("%-5s %-20s %-25s %-12s %-15s%n", "ID", "Student", "Course", "Status", "Applied On");
        System.out.println("-".repeat(77));
        for (String[] e : list) {
            System.out.printf("%-5s %-20s %-25s %-12s %-15s%n", e[0], e[1], e[2], e[3], e[4]);
        }
    }

    private void listPending() {
        List<String[]> list = model.getPending();
        if (list.isEmpty()) {
            System.out.println("No pending applications.");
            return;
        }
        System.out.printf("%-5s %-20s %-25s %-15s%n", "ID", "Student", "Course", "Applied On");
        System.out.println("-".repeat(65));
        for (String[] e : list) {
            System.out.printf("%-5s %-20s %-25s %-15s%n", e[0], e[1], e[2], e[3]);
        }
    }

    private void approveApplication() {
        listPending();
        System.out.print("\nEnrollment ID to approve: ");
        try {
            int id = Integer.parseInt(sc.nextLine());
            if (model.approve(id)) {
                System.out.println("Application approved.");
            } else {
                System.out.println("Failed to approve.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID. Please enter a number.");
        }
    }

    private void rejectApplication() {
        listPending();
        System.out.print("\nEnrollment ID to reject: ");
        try {
            int id = Integer.parseInt(sc.nextLine());
            if (model.reject(id)) {
                System.out.println("Application rejected.");
            } else {
                System.out.println("Failed to reject.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID. Please enter a number.");
        }
    }

    private void removeEnrollment() {
        try {
            System.out.print("Enrollment ID to remove: ");
            int id = Integer.parseInt(sc.nextLine());
            if (model.delete(id)) {
                System.out.println("Enrollment removed.");
            } else {
                System.out.println("Failed to remove.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID. Please enter a number.");
        }
    }

    private void listByStudent(int studentId) {
        List<String[]> list = model.getByStudent(studentId);
        if (list.isEmpty()) {
            System.out.println("You have not applied for any courses yet.");
            return;
        }
        System.out.printf("%-5s %-30s %-12s %-15s%n", "ID", "Course", "Status", "Applied On");
        System.out.println("-".repeat(62));
        for (String[] e : list) {
            System.out.printf("%-5s %-30s %-12s %-15s%n", e[0], e[1], e[2], e[3]);
        }
    }

    private void applyForCourse(int studentId) {
        // Show available courses
        List<String[]> courses = courseModel.getAll();
        if (courses.isEmpty()) {
            System.out.println("No courses available.");
            return;
        }
        System.out.println("\nAvailable Courses:");
        System.out.printf("%-5s %-25s %-35s %-10s%n", "ID", "Title", "Description", "Duration");
        System.out.println("-".repeat(75));
        for (String[] c : courses) {
            System.out.printf("%-5s %-25s %-35s %-10s%n", c[0], c[1], c[2], c[3] + " hrs");
        }

        System.out.print("\nEnter Course ID to apply: ");
        try {
            int cid = Integer.parseInt(sc.nextLine());
            if (model.apply(studentId, cid)) {
                System.out.println("Application submitted! Waiting for admin approval.");
            } else {
                System.out.println("Application failed.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID. Please enter a number.");
        }
    }
}
