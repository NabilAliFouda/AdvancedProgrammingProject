package asu.advancedprogrammingproject;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Dashboard {

    private static User loggedInUser;

    public static Scene createDashboardScene(Stage primaryStage, User user) {
        loggedInUser = user;
        String role = (user instanceof Student) ? "Student" : "Teacher";

        // Create menu bar
        MenuBar menuBar = new MenuBar();
        Menu menu = new Menu("Menu");
        
        // Menu items
        MenuItem coursesItem = new MenuItem("Courses");
        MenuItem quizzesItem = new MenuItem("Quizzes");
        MenuItem profileItem = new MenuItem("Profile");
        MenuItem logoutItem = new MenuItem("Logout"); // Last item

        // Set actions for menu items
        coursesItem.setOnAction(e -> showCourses(primaryStage));
        quizzesItem.setOnAction(e -> showQuizzes(primaryStage));
        profileItem.setOnAction(e -> showProfile(primaryStage));
        logoutItem.setOnAction(e -> {
            App appInstance = new App(); // Create instance of App
            primaryStage.setScene(appInstance.createLoginScene(primaryStage));
        });


        // Add items to menu
        menu.getItems().addAll(coursesItem, quizzesItem, profileItem, logoutItem);
        menuBar.getMenus().add(menu);

        // Welcome message
        Label welcomeLabel = new Label("Welcome, " + user.getName() + " (" + role + ")");
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(15));
        layout.getChildren().addAll(menuBar, welcomeLabel);

        return new Scene(layout, 500, 350);
    }

    private static void showCourses(Stage primaryStage) {
        Label label = new Label(loggedInUser instanceof Student ? "Enrolled Courses" : "Teaching Courses");
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(15));
        layout.getChildren().addAll(label, new Label("(Display course details here)"));
        primaryStage.setScene(new Scene(layout, 500, 350));
    }

    private static void showQuizzes(Stage primaryStage) {
        Label label = new Label(loggedInUser instanceof Student ? "Available Quizzes" : "Assigned Quizzes");
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(15));
        layout.getChildren().addAll(label, new Label("(Display quiz details here)"));
        primaryStage.setScene(new Scene(layout, 500, 350));
    }

    private static void showProfile(Stage primaryStage) {
        Label label = new Label("User Profile");
        Label details = new Label(loggedInUser instanceof Student ? 
            "Student ID: " + loggedInUser.getID() + "\nCourses Enrolled: ..." :
            "Teacher ID: " + loggedInUser.getID() + "\nCourses Taught: ...");
        
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(15));
        layout.getChildren().addAll(label, details);
        primaryStage.setScene(new Scene(layout, 500, 350));
    }
}
