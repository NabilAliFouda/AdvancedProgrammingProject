package asu.advancedprogrammingproject;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {
        var javaVersion = SystemInfo.javaVersion();
        var javafxVersion = SystemInfo.javafxVersion();

        var label = new Label("Hello, JavaFX " + javafxVersion + ", running on Java " + javaVersion + ".");
        var scene = new Scene(new StackPane(label), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
         launch();
         Student student = new Student("Alice", 101, "pass123");

        System.out.println("---- TEST: Incorrect Login ----");
        try {
            student.logIn(101, "wrongpass"); // Wrong password
        } catch (AlreadyLoggedInException e) {
            System.out.println("Exception: " + e.getMessage());
        }

        System.out.println("---- TEST: Correct Login ----");
        try {
            student.logIn(101, "pass123"); // Correct login
        } catch (AlreadyLoggedInException e) {
            System.out.println("Exception: " + e.getMessage());
        }

        System.out.println("---- TEST: Login Again (Exception) ----");
        try {
            student.logIn(101, "pass123"); // Already logged in
        } catch (AlreadyLoggedInException e) {
            System.out.println("Exception: " + e.getMessage());
        }

        System.out.println("---- TEST: Logout ----");
        try {
            student.logout(); // Should succeed
        } catch (AlreadyLoggedOutException e) {
            System.out.println("Exception: " + e.getMessage());
        }

        System.out.println("---- TEST: Logout Again (Exception) ----");
        try {
            student.logout(); // Already logged out
        } catch (AlreadyLoggedOutException e) {
            System.out.println("Exception: " + e.getMessage());
        }

        System.out.println("---- TEST: Get Role ----");
        student.getRole();
    }
}

    



