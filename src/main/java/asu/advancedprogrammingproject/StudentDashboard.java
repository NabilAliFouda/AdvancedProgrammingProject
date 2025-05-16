/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package asu.advancedprogrammingproject;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.List;

public class StudentDashboard {

    public static Scene createStudentDashboardScene(Stage primaryStage, Student student) {
        primaryStage.setTitle("Student Dashboard");

        // **TabPane for different sections**
        TabPane tabPane = new TabPane();
        
        // **Courses Tab**
        Tab coursesTab = new Tab("Courses", getCoursesView(student));
        coursesTab.setClosable(false);
        
        // **Quizzes Tab**
        Tab quizzesTab = new Tab("Quizzes", getQuizzesView(student));
        quizzesTab.setClosable(false);
        
        // **Grades Tab**
        Tab gradesTab = new Tab("Grades", getGradesView(student));
        gradesTab.setClosable(false);
        
        // **Profile Tab**
        Tab profileTab = new Tab("Profile", getProfileView(student));
        profileTab.setClosable(false);

        tabPane.getTabs().addAll(coursesTab, quizzesTab, gradesTab, profileTab);

        // **Logout Button**
        Button logoutButton = new Button("Logout");
        logoutButton.setOnAction(e -> {
            App appInstance = new App(); // Create instance of App
            primaryStage.setScene(appInstance.createLoginScene(primaryStage));
        });

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(15));
        layout.getChildren().addAll(tabPane, logoutButton);

        return new Scene(layout, 600, 400);
    }

    private static VBox getCoursesView(Student student) {
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(15));
        Label title = new Label("Enrolled Courses");

        List<Course> courses = student.getEnrolledCourses();
        StringBuilder coursesList = new StringBuilder();

        for (Course course : courses) {
            coursesList.append("- ").append(course.getID()).append(" | ").append(course.getLanguage().getLanguageName()).append("\n");
        }

        Label coursesLabel = new Label(coursesList.toString().isEmpty() ? "No enrolled courses." : coursesList.toString());
        layout.getChildren().addAll(title, coursesLabel);
        return layout;
    }

        private static VBox getQuizzesView(Student student) {
            VBox layout = new VBox(10);
            layout.setPadding(new Insets(15));
            Label title = new Label("Available Quizzes");

            List<Course> courses = student.getEnrolledCourses();
            if (courses.isEmpty()) {
                layout.getChildren().add(new Label("No enrolled courses."));
                return layout;
            }

            // Course Dropdown (with names)
            ComboBox<Course> courseSelection = new ComboBox<>();
            courseSelection.getItems().addAll(courses);
            courseSelection.setPromptText("Select a Course");

            Button openQuizButton = new Button("Open Quiz");
            Label quizStatusLabel = new Label();

            openQuizButton.setOnAction(e -> {
                Course selectedCourse = courseSelection.getSelectionModel().getSelectedItem();
                if (selectedCourse == null) {
                    quizStatusLabel.setText("Please select a course.");
                    return;
                }

                // Find the quiz for the selected course
                Quiz quiz = student.getQuizzesTaken().stream()
                                  .filter(q -> q.getCourse().equals(selectedCourse))
                                  .findFirst()
                                  .orElse(null);

                if (quiz == null) {
                    quizStatusLabel.setText("No quiz available for this course.");
                } else {
                    quizStatusLabel.setText("Quiz available: " + selectedCourse.getLanguage().getLanguageName());
                }
            });

            layout.getChildren().addAll(title, courseSelection, openQuizButton, quizStatusLabel);
            return layout;
        }



    private static VBox getGradesView(Student student) {
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(15));
        Label title = new Label("Grades");

        Label gradesLabel = new Label(student.getGrades().isEmpty() ? "No grades available." : student.getGrades());
        layout.getChildren().addAll(title, gradesLabel);
        return layout;
    }

    private static VBox getProfileView(Student student) {
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(15));
        Label title = new Label("Profile Information");

        Label profileDetails = new Label(
            "Name: " + student.getName() + "\n" +
            "ID: " + student.getID() + "\n" +
            "Enrolled Courses: " + student.getEnrolledCourses().size() + "\n" +
            "Quizzes Taken: " + student.getQuizzesTaken().size()
        );

        layout.getChildren().addAll(title, profileDetails);
        return layout;
    }
}

