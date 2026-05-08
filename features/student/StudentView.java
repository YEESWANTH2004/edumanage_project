package com.yourname.edumanage.features.student;

import java.util.List;
import java.util.Scanner;

public class StudentView {
    private final StudentModel model = new StudentModel();
    private final Scanner sc;

    public StudentView(Scanner sc) {
        this.sc = sc;
    }

    public void showMenu() {
        while (true) {
            System.out.println("\n--- Student Management ---");
            System.out.println("1. List All Students");
            System.out.println("2. Add Student");
            System.out.println("3. Update Student");
            System.out.println("4. Delete Student");
            System.out.println("0. Back");
            System.out.print("Choice: ");
            String choice = sc.nextLine();
            switch (choice) {
                case "1" -> listAll();
                case "2" -> addStudent();
                case "3" -> updateStudent();
                case "4" -> deleteStudent();
                case "0" -> { return; }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private void listAll() {
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

    private void addStudent() {
        System.out.print("Name: ");
        String name = sc.nextLine();
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Phone: ");
        String phone = sc.nextLine();
        if (model.add(name, email, phone)) {
            System.out.println("Student added.");
        } else {
            System.out.println("Failed to add student.");
        }
    }

    private void updateStudent() {
        System.out.print("Student ID to update: ");
        int id = Integer.parseInt(sc.nextLine());
        System.out.print("New Name: ");
        String name = sc.nextLine();
        System.out.print("New Email: ");
        String email = sc.nextLine();
        System.out.print("New Phone: ");
        String phone = sc.nextLine();
        if (model.update(id, name, email, phone)) {
            System.out.println("Student updated.");
        } else {
            System.out.println("Failed to update.");
        }
    }

    private void deleteStudent() {
        System.out.print("Student ID to delete: ");
        int id = Integer.parseInt(sc.nextLine());
        if (model.delete(id)) {
            System.out.println("Student deleted.");
        } else {
            System.out.println("Failed to delete.");
        }
    }
}
