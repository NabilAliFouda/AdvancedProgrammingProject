/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package asu.advancedprogrammingproject;

import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author nabil
 */
public class TeacherDashboard {
        public static Scene createStudentDashboardScene(Stage primaryStage, Teacher teacher) {
        primaryStage.setTitle("Student Dashboard");

        // **TabPane for different sections**
        TabPane tabPane = new TabPane();

        
        // **Quizzes Tab**
        Tab quizzesTab = new Tab("Quizzes", getQuizzesView(teacher, primaryStage));
        quizzesTab.setClosable(false);
        
        // **Profile Tab**
        Tab profileTab = new Tab("Profile", getProfileView(teacher));
        profileTab.setClosable(false);

        tabPane.getTabs().addAll(quizzesTab, profileTab);

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

        private static VBox getQuizzesView(Teacher teacher, Stage primaryStage) {
            VBox layout = new VBox(10);
            layout.setPadding(new Insets(15));
            Label title = new Label("Created Quizzes");

            List<Quiz> createdQuizzes = teacher.getCreatedQuizzes();
            for (int i = 0; i < createdQuizzes.size(); i++) {
                Label quizLabel = new Label("Quiz number : "+ (i+1) + " for Course: " + createdQuizzes.get(i).getCourse().getID());
                layout.getChildren().add(quizLabel);
            }
            if (createdQuizzes.isEmpty()) {
                Label noQuizzesLabel = new Label("No quizzes created.");
                layout.getChildren().add(noQuizzesLabel);
            }
            
            Button createQuizButton = new Button("Create Quiz");
            createQuizButton.setOnAction(e -> {
                primaryStage.setScene(QuizCreator.createQuizScene(primaryStage, teacher));
            });
            layout.getChildren().add(createQuizButton);
            return layout;
        }

    private static VBox getProfileView(Teacher teacher) {
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(15));
        Label title = new Label("Profile Information");

        Label profileDetails = new Label(
            "Name: " + teacher.getName() + "\n" +
            "ID: " + teacher.getID() + "\n" +
            "Language" + teacher.getLanguage().getLanguageName() + "\n" +
            "Quizzes created: " + teacher.getCreatedQuizzes().size()
        );

        layout.getChildren().addAll(title, profileDetails);
        return layout;
    }
    
}
