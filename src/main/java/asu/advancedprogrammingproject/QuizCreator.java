package asu.advancedprogrammingproject;

import javafx.scene.Scene;
import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/**
 *
 * @author nabil
 */
public class QuizCreator {
    public static Scene createQuizScene(Stage primaryStage, Teacher teacher) {
        primaryStage.setTitle("Create Quiz");

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

        // **Styled Course Dropdown**
        ComboBox<Course> courseComboBox = new ComboBox<>();
        courseComboBox.getItems().addAll(teacher.getLanguage().getcourses());
        courseComboBox.setPromptText("Select Course");
        courseComboBox.setStyle(
            "-fx-background-color: #f0f0f0;" +
            "-fx-background-radius: 5;" +
            "-fx-padding: 8;" +
            "-fx-border-color: transparent;"
        );

        List<Question> questions = new ArrayList<>();

        // **Styled Question Input**
        Label questionLabel = createBoldLabel("Question:");
        TextField questionTextField = new TextField();
        questionTextField.setPromptText("Enter Question");
        questionTextField.setStyle(
            "-fx-background-color: #f0f0f0;" +
            "-fx-background-radius: 5;" +
            "-fx-padding: 8;" +
            "-fx-border-color: transparent;"
        );
        questionTextField.setMinWidth(200);  // ✅ Set consistent width
        HBox questionBox = new HBox(10);
        questionBox.setAlignment(Pos.CENTER_LEFT);
        questionBox.getChildren().addAll(questionLabel, questionTextField);

        // **Styled Answer Input**
        Label answerLabel = createBoldLabel("Answer:");
        TextField answerTextField = new TextField();
        answerTextField.setPromptText("Enter Answer");
        answerTextField.setStyle(
            "-fx-background-color: #f0f0f0;" +
            "-fx-background-radius: 5;" +
            "-fx-padding: 8;" +
            "-fx-border-color: transparent;"
        );
        answerTextField.setMinWidth(200);  // ✅ Match width with question input
        HBox answerBox = new HBox(10);
        answerBox.setAlignment(Pos.CENTER_LEFT);
        answerBox.getChildren().addAll(answerLabel, answerTextField);

        // **Styled "Add Question" Button**
        Button addQuestionButton = new Button("Add Question");
        addQuestionButton.setStyle(
            "-fx-background-color: #2196F3;" +
            "-fx-text-fill: white;" +
            "-fx-font-weight: bold;" +
            "-fx-background-radius: 5;" +
            "-fx-padding: 8 16;" +
            "-fx-cursor: hand;"
        );
        addQuestionButton.setOnAction(e -> {
            String questionText = questionTextField.getText();
            String answerText = answerTextField.getText();
            if (!questionText.isEmpty() && !answerText.isEmpty()) {
                System.out.println("Added question: " + questionText + " with answer: " + answerText);
                questionTextField.clear();
                answerTextField.clear();
                questions.add(new Question(questionText, answerText));
            } else {
                System.out.println("Please enter both question and answer.");
            }
        });

        // **Styled "Create Quiz" Button**
        Button createQuizButton = new Button("Create Quiz");
        createQuizButton.setStyle(
            "-fx-background-color: #d55a9d;" +
            "-fx-text-fill: white;" +
            "-fx-font-weight: bold;" +
            "-fx-background-radius: 5;" +
            "-fx-padding: 8 16;" +
            "-fx-cursor: hand;"
        );
        createQuizButton.setOnAction(e -> {
            Course selectedCourse = courseComboBox.getValue();
            if (selectedCourse != null) {
                teacher.createQuiz(selectedCourse, questions);
                System.out.println("Creating quiz for course: " + selectedCourse.getID());
                primaryStage.setScene(TeacherDashboard.createTeacherDashboardScene(primaryStage, teacher));
            } else {
                System.out.println("Please select a course.");
            }
        });

        // **Styled Layout**
        card.getChildren().addAll(courseComboBox, questionBox, answerBox, addQuestionButton, createQuizButton);
        root.getChildren().add(card);

        return new Scene(root, 600, 400);
    }

    private static Label createBoldLabel(String text) {
        Label label = new Label(text);
        label.setFont(Font.font("System", FontWeight.BOLD, 14));
        label.setTextFill(Color.DARKBLUE);
        return label;
    }
}
