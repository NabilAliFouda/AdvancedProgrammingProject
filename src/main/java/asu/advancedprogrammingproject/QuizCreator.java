/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package asu.advancedprogrammingproject;

import javafx.scene.Scene;

import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author nabil
 */
public class QuizCreator {
    public static Scene createQuizScene(Stage primaryStage, Teacher teacher) {
        primaryStage.setTitle("Create Quiz");

        // **ComboBox for selecting course**
        ComboBox<Course> courseComboBox = new ComboBox<>();
        courseComboBox.getItems().addAll(teacher.getLanguage().getcourses());
        courseComboBox.setPromptText("Select Course");
        courseComboBox.setOnAction(e -> {
            Course selectedCourse = courseComboBox.getValue();
            if (selectedCourse != null) {
                System.out.println("Selected course: " + selectedCourse.getID());
            }
        });
        List<Question> questions = new ArrayList<Question>();
        // **TextField for entering question and answer**
        TextField QuestionTextField = new TextField();
        QuestionTextField.setPromptText("Enter Question");
        Label QuestionLabel = new Label("Question");
        QuestionLabel.setLabelFor(QuestionTextField);
        HBox QuestionBox = new HBox(10);
        QuestionBox.getChildren().addAll(QuestionLabel, QuestionTextField);
        TextField AnswerTextField = new TextField();
        AnswerTextField.setPromptText("Enter Answer");
        Label AnswerLabel = new Label("Answer");
        AnswerLabel.setLabelFor(AnswerTextField);
        HBox AnswerBox = new HBox(10);
        AnswerBox.getChildren().addAll(AnswerLabel, AnswerTextField);
        Button addQuestionButton = new Button("Add Question");
        addQuestionButton.setOnAction(e -> {
            String questionText = QuestionTextField.getText();
            String answerText = AnswerTextField.getText();
            if (!questionText.isEmpty() && !answerText.isEmpty()) {
                // Logic to add question to quiz
                System.out.println("Added question: " + questionText + " with answer: " + answerText);
                QuestionTextField.clear();
                AnswerTextField.clear();
                questions.add(new Question(questionText, answerText));
            } else {
                System.out.println("Please enter both question and answer.");
            }
        });      
        // **Button to create quiz**
        Button createQuizButton = new Button("Create Quiz");
        createQuizButton.setOnAction(e -> {
            Course selectedCourse = courseComboBox.getValue();
            if (selectedCourse != null) {
                teacher.createQuiz(selectedCourse, questions);
                System.out.println("Creating quiz for course: " + selectedCourse.getID());
                primaryStage.setScene(TeacherDashboard.createStudentDashboardScene(primaryStage, teacher));
            } else {
                System.out.println("Please select a course.");
            }

        });

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(15));
        layout.getChildren().addAll(courseComboBox,QuestionBox,AnswerBox,addQuestionButton,createQuizButton);

        return new Scene(layout, 400, 300);
    }
}
