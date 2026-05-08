package com.yourname.edumanage.features.enrollment;

import java.util.List;
import java.util.Scanner;

public class EnrollmentView {
    private final EnrollmentModel model = new EnrollmentModel();
    private final Scanner sc;

    public EnrollmentView(Scanner sc) {
        this.sc = sc;
    }

    public void showAdminMenu() {
        while (true) {
            System.out.println("\n--- Enrollment Management ---");
            System.out.println("1. View All Enrollments");
            System.out.println("2. Enroll Student in Course");
            System.out.println("3. Remove Enrollment");
            System.out.println("0. Back");
            System.out.print("Choice: ");
            String choice = sc.nextLine();
            switch (choice) {
                case "1" -> listAll();
                case "2" -> enrollStudent();
                case "3" -> removeEnrollment();
                case "0" -> { return; }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    public void showStudentMenu(int studentId) {
        while (true) {
            System.out.println("\n--- My Enrollments ---");
            System.out.println("1. View My Courses");
            System.out.println("2. Enroll in a Course");
            System.out.println("0. Back");
            System.out.print("Choice: ");
            String choice = sc.nextLine();
            switch (choice) {
                case "1" -> listByStudent(studentId);
                case "2" -> enrollSelf(studentId);
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
        System.out.printf("%-5s %-25s %-25s %-15s%n", "ID", "Student", "Course", "Enrolled On");
        System.out.println("-".repeat(70));
        for (String[] e : list) {
            System.out.printf("%-5s %-25s %-25s %-15s%n", e[0], e[1], e[2], e[3]);
        }
    }

    private void listByStudent(int studentId) {
        List<String[]> list = model.getByStudent(studentId);
        if (list.isEmpty()) {
            System.out.println("You are not enrolled in any courses.");
            return;
        }
        System.out.printf("%-5s %-30s %-15s%n", "ID", "Course", "Enrolled On");
        System.out.println("-".repeat(50));
        for (String[] e : list) {
            System.out.printf("%-5s %-30s %-15s%n", e[0], e[1], e[2]);
        }
    }

    private void enrollStudent() {
        System.out.print("Student ID: ");
        int sid = Integer.parseInt(sc.nextLine());
        System.out.print("Course ID: ");
        int cid = Integer.parseInt(sc.nextLine());
        if (model.enroll(sid, cid)) {
            System.out.println("Enrolled successfully.");
        } else {
            System.out.println("Enrollment failed.");
        }
    }

    private void enrollSelf(int studentId) {
        System.out.print("Course ID to enroll in: ");
        int cid = Integer.parseInt(sc.nextLine());
        if (model.enroll(studentId, cid)) {
            System.out.println("Enrolled successfully.");
        } else {
            System.out.println("Enrollment failed.");
        }
    }

    private void removeEnrollment() {
        System.out.print("Enrollment ID to remove: ");
        int id = Integer.parseInt(sc.nextLine());
        if (model.delete(id)) {
            System.out.println("Enrollment removed.");
        } else {
            System.out.println("Failed to remove.");
        }
    }
}
