package com.yourname.edumanage.features.marks;

import java.util.List;
import java.util.Scanner;

public class MarksView {
    private final MarksModel model = new MarksModel();
    private final Scanner sc;

    public MarksView(Scanner sc) {
        this.sc = sc;
    }

    public void showAdminMenu() {
        while (true) {
            System.out.println("\n--- Marks Management ---");
            System.out.println("1. View All Marks");
            System.out.println("2. Add / Update Marks");
            System.out.println("3. Delete Mark Entry");
            System.out.println("0. Back");
            System.out.print("Choice: ");
            String choice = sc.nextLine();
            switch (choice) {
                case "1" -> listAll();
                case "2" -> addOrUpdate();
                case "3" -> delete();
                case "0" -> { return; }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    public void showStudentMenu(int studentId) {
        while (true) {
            System.out.println("\n--- My Marks ---");
            System.out.println("1. View My Marks");
            System.out.println("0. Back");
            System.out.print("Choice: ");
            String choice = sc.nextLine();
            switch (choice) {
                case "1" -> listByStudent(studentId);
                case "0" -> { return; }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private void listAll() {
        List<String[]> list = model.getAll();
        if (list.isEmpty()) {
            System.out.println("No marks found.");
            return;
        }
        System.out.printf("%-5s %-25s %-25s %-10s%n", "ID", "Student", "Course", "Score");
        System.out.println("-".repeat(65));
        for (String[] m : list) {
            System.out.printf("%-5s %-25s %-25s %-10s%n", m[0], m[1], m[2], m[3]);
        }
    }

    private void listByStudent(int studentId) {
        List<String[]> list = model.getByStudent(studentId);
        if (list.isEmpty()) {
            System.out.println("No marks found.");
            return;
        }
        System.out.printf("%-5s %-30s %-10s%n", "ID", "Course", "Score");
        System.out.println("-".repeat(45));
        for (String[] m : list) {
            System.out.printf("%-5s %-30s %-10s%n", m[0], m[1], m[2]);
        }
    }

    private void addOrUpdate() {
        System.out.print("Student ID: ");
        int sid = Integer.parseInt(sc.nextLine());
        System.out.print("Course ID: ");
        int cid = Integer.parseInt(sc.nextLine());
        System.out.print("Score: ");
        double score = Double.parseDouble(sc.nextLine());
        if (model.addOrUpdate(sid, cid, score)) {
            System.out.println("Marks saved.");
        } else {
            System.out.println("Failed to save marks.");
        }
    }

    private void delete() {
        System.out.print("Mark ID to delete: ");
        int id = Integer.parseInt(sc.nextLine());
        if (model.delete(id)) {
            System.out.println("Mark deleted.");
        } else {
            System.out.println("Failed to delete.");
        }
    }
}
