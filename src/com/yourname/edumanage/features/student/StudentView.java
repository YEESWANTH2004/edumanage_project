package com.yourname.edumanage.features.student;

import com.yourname.edumanage.features.student.details.StudentDetailsView;
import com.yourname.edumanage.features.student.list.StudentListView;

import java.util.Scanner;

public class StudentView {
    private final StudentModel model = new StudentModel();
    private final StudentListView listView = new StudentListView();
    private final StudentDetailsView detailsView = new StudentDetailsView();
    private final Scanner sc;

    public StudentView(Scanner sc) {
        this.sc = sc;
    }

    public void showMenu() {
        while (true) {
            System.out.println("\n--- Student Management ---");
            System.out.println("1. List All Students");
            System.out.println("2. View Student Details");
            System.out.println("3. Delete Student");
            System.out.println("0. Back");
            System.out.print("Choice: ");
            String choice = sc.nextLine();
            switch (choice) {
                case "1" -> listView.show();
                case "2" -> viewDetails();
                case "3" -> deleteStudent();
                case "0" -> { return; }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private void viewDetails() {
        System.out.print("Student ID: ");
        try {
            int id = Integer.parseInt(sc.nextLine());
            detailsView.show(id);
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID. Please enter a number.");
        }
    }

    private void deleteStudent() {
        System.out.print("Student ID to delete: ");
        try {
            int id = Integer.parseInt(sc.nextLine());
            if (model.delete(id)) {
                System.out.println("Student deleted.");
            } else {
                System.out.println("Failed to delete.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID. Please enter a number.");
        }
    }
}
