package asu.advancedprogrammingproject;

import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class TeacherDashboard {

    public static Scene createTeacherDashboardScene(Stage primaryStage, Teacher teacher) {
        primaryStage.setTitle("Teacher Dashboard");

        // **Styled Background**
        StackPane root = new StackPane();
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #d55a9d, #574bdf);");

        VBox card = new VBox(15);
        card.setPadding(new Insets(25));
        card.setAlignment(Pos.CENTER);
        card.setMaxWidth(400);
        card.setStyle(
            "-fx-background-color: white;" +
            "-fx-background-radius: 10;" +
            "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 8);"
        );

        // **Styled TabPane**
        TabPane tabPane = new TabPane();        
        tabPane.setStyle(
            "-fx-tab-min-width: 160px;" + // Expand tab width
            "-fx-tab-max-width: 260px;" + // Ensure uniformity
            "-fx-background-color: transparent;" // Removes default background
        );
        // **Styled Tabs**
        Tab quizzesTab = new Tab("Quizzes", getQuizzesView(teacher, primaryStage));
        quizzesTab.setClosable(false);
        quizzesTab.setStyle("-fx-background-color: #892be2; -fx-text-fill: white; -fx-font-weight: bold;");

        Tab profileTab = new Tab("Profile", getProfileView(teacher));
        profileTab.setClosable(false);
        profileTab.setStyle("-fx-background-color: #ff8b94; -fx-text-fill: white; -fx-font-weight: bold;");

        tabPane.getTabs().addAll(quizzesTab, profileTab);

        // **Logout Button**
        Button logoutButton = new Button("LOGOUT");
        logoutButton.setStyle(
            "-fx-background-color: #2196F3;" +
            "-fx-text-fill: white;" +
            "-fx-font-weight: bold;" +
            "-fx-background-radius: 5;" +
            "-fx-padding: 8 16;" +
            "-fx-cursor: hand;"
        );
        logoutButton.setOnAction(e -> {
            App appInstance = new App();
            primaryStage.setScene(appInstance.createLoginScene(primaryStage));
        });

        card.getChildren().addAll(tabPane, logoutButton);
        root.getChildren().add(card);

        return new Scene(root, 600, 450);
    }

    private static VBox getQuizzesView(Teacher teacher, Stage primaryStage) {
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(15));
        Label title = createBoldLabel("Created Quizzes");

        List<Quiz> createdQuizzes = teacher.getCreatedQuizzes();
        for (int i = 0; i < createdQuizzes.size(); i++) {
            Label quizLabel = new Label("Quiz number: " + (i + 1) + " for Course: " + createdQuizzes.get(i).getCourse().getID());
            quizLabel.setTextFill(Color.DARKBLUE);
            quizLabel.setFont(Font.font("System", FontWeight.NORMAL, 12));
            layout.getChildren().add(quizLabel);
        }

        if (createdQuizzes.isEmpty()) {
            Label noQuizzesLabel = new Label("No quizzes created.");
            noQuizzesLabel.setTextFill(Color.DARKBLUE);
            noQuizzesLabel.setFont(Font.font("System", FontWeight.BOLD, 12));
            layout.getChildren().add(noQuizzesLabel);
        }

        Button createQuizButton = new Button("Create Quiz");
        createQuizButton.setStyle(
            "-fx-background-color: #2196F3;" +
            "-fx-text-fill: white;" +
            "-fx-font-weight: bold;" +
            "-fx-background-radius: 5;" +
            "-fx-padding: 8 16;" +
            "-fx-cursor: hand;"
        );
        createQuizButton.setOnAction(e -> {
            primaryStage.setScene(QuizCreator.createQuizScene(primaryStage, teacher));
        });

        layout.getChildren().add(createQuizButton);
        return layout;
    }

    private static VBox getProfileView(Teacher teacher) {
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(15));
        Label title = createBoldLabel("Profile Information");

        Label profileDetails = new Label(
            "Name: " + teacher.getName() + "\n" +
            "ID: " + teacher.getID() + "\n" +
            "Language: " + teacher.getLanguage().getLanguageName() + "\n" +
            "Quizzes Created: " + teacher.getCreatedQuizzes().size()
        );
        profileDetails.setFont(Font.font("System", FontWeight.NORMAL, 13));

        layout.getChildren().addAll(title, profileDetails);
        return layout;
    }

    private static Label createBoldLabel(String text) {
        Label label = new Label(text);
        label.setFont(Font.font("System", FontWeight.BOLD, 14));
        label.setTextFill(Color.DARKBLUE);
        return label;
    }
}
