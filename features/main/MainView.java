package com.yourname.edumanage.features.main;

import com.yourname.edumanage.features.course.CourseView;
import com.yourname.edumanage.features.enrollment.EnrollmentView;
import com.yourname.edumanage.features.marks.MarksView;
import com.yourname.edumanage.features.signin.SigninView;
import com.yourname.edumanage.features.signup.SignupView;
import com.yourname.edumanage.features.student.StudentModel;
import com.yourname.edumanage.features.student.StudentView;

import java.util.Scanner;

public class MainView {
    private final Scanner sc = new Scanner(System.in);

    public void start() {
        while (true) {
            System.out.println("EduManage System");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("0. Exit");
            System.out.print("Choice: ");
            String choice = sc.nextLine();
            switch (choice) {
                case "1" -> {
                    SigninView signin = new SigninView(sc);
                    String[] result = signin.show();
                    if (result != null) {
                        int userId = Integer.parseInt(result[0]);
                        String role = result[1];
                        if (role.equals("admin")) {
                            showAdminMenu();
                        } else {
                            StudentModel sm = new StudentModel();
                            int studentId = sm.getStudentIdByUserId(userId);
                            if (studentId == -1) {
                                System.out.println("Student profile not found.");
                            } else {
                                showStudentMenu(studentId);
                            }
                        }
                    }
                }
                case "2" -> new SignupView(sc).show();
                case "0" -> {
                    System.out.println("Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private void showAdminMenu() {
        StudentView studentView = new StudentView(sc);
        CourseView courseView = new CourseView(sc);
        EnrollmentView enrollmentView = new EnrollmentView(sc);
        MarksView marksView = new MarksView(sc);

        while (true) {
            System.out.println("Admin Dashboard");
            System.out.println("1. Manage Students");
            System.out.println("2. Manage Courses");
            System.out.println("3. Manage Enrollments");
            System.out.println("4. Manage Marks");
            System.out.println("0. Logout");
            System.out.print("Choice: ");
            String choice = sc.nextLine();
            switch (choice) {
                case "1" -> studentView.showMenu();
                case "2" -> courseView.showMenu();
                case "3" -> enrollmentView.showAdminMenu();
                case "4" -> marksView.showAdminMenu();
                case "0" -> { return; }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private void showStudentMenu(int studentId) {
        CourseView courseView = new CourseView(sc);
        EnrollmentView enrollmentView = new EnrollmentView(sc);
        MarksView marksView = new MarksView(sc);

        while (true) {
            System.out.println("Student Dashboard");
            System.out.println("1. View Available Courses");
            System.out.println("2. My Enrollments");
            System.out.println("3. My Marks");
            System.out.println("0. Logout");
            System.out.print("Choice: ");
            String choice = sc.nextLine();
            switch (choice) {
                case "1" -> courseView.listAll();
                case "2" -> enrollmentView.showStudentMenu(studentId);
                case "3" -> marksView.showStudentMenu(studentId);
                case "0" -> { return; }
                default -> System.out.println("Invalid option.");
            }
        }
    }
}
