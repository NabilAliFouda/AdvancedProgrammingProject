package asu.advancedprogrammingproject;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.layout.StackPane;

/**
 *
 * @author Ingy
 */

public class App extends Application {

    private List<User> users = new ArrayList<>();
    private List<Language> predefinedLanguages = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) {
        // Predefined languages
        predefinedLanguages.add(new Language("English"));
        predefinedLanguages.add(new Language("Spanish"));

        // Create test student (Ali)
        Student testStudent = new Student("Ali", 101, "pass123");

        // Create courses and teachers
        Teacher mona = new Teacher("Mona", 201, "teachpass", predefinedLanguages.get(0));
        Teacher carlos = new Teacher("Carlos", 202, "teachpass", predefinedLanguages.get(1));

        Course englishCourse = new Course(predefinedLanguages.get(0), 1, "Beginner", 100, mona, new ArrayList<>());
        Course spanishCourse = new Course(predefinedLanguages.get(1), 2, "Intermediate", 120, carlos, new ArrayList<>());

        // Enroll Ali
        testStudent.enroll(englishCourse);
        testStudent.enroll(spanishCourse);

        // Create a quiz for English course and add it to the course
        Question q1 = new Question("Java is a type of snake.", "False");
        Question q2 = new Question("The word 'algorithm' comes from a mathematician’s name.", "True");
        Quiz englishQuiz = new Quiz(new Question[]{q1, q2}, englishCourse, "Quiz 3");
        englishCourse.addQuiz(englishQuiz); 

        // Add users
        users.add(testStudent);
        users.add(mona);
        users.add(carlos);

        primaryStage.setScene(createLoginScene(primaryStage));
        primaryStage.setTitle("Login to Courses");
        primaryStage.show();
        
        System.out.println("Quizzes in English course: " + englishCourse.getQuizzes().size());
        System.out.println("Courses for Ali: " + testStudent.getEnrolledCourses().size());

        for (Course course : testStudent.getEnrolledCourses()) {
            System.out.println("Course: " + course);
            for (Quiz q : course.getQuizzes()) {
                System.out.println("Quiz available with " + q.getQuestions().length + " questions");
            }
        }

    }

public Scene createLoginScene(Stage primaryStage) {
    StackPane root = new StackPane();
    root.setStyle("-fx-background-color: linear-gradient(to bottom, #d55a9d, #574bdf);");

    VBox card = new VBox(15);
    card.setPadding(new Insets(25));
    card.setAlignment(Pos.CENTER);
    card.setMaxWidth(300);
    card.setStyle(
        "-fx-background-color: white;" +
        "-fx-background-radius: 10;" +
        "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 8);"
    );

    Label title = new Label("LOGIN");
    title.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");

    // ID Field
    TextField idField = new TextField();
    idField.setPromptText("ID");
    idField.setStyle(
        "-fx-background-color: #f0f0f0;" +
        "-fx-background-radius: 5;" +
        "-fx-padding: 8;" +
        "-fx-border-color: transparent;"
    );

    // Password Fields (one hidden, one visible)
    PasswordField passField = new PasswordField();
    passField.setPromptText("Password");
    passField.setStyle(
        "-fx-background-color: #f0f0f0;" +
        "-fx-background-radius: 5;" +
        "-fx-padding: 8;" +
        "-fx-border-color: transparent;"
    );

    TextField visiblePassField = new TextField();
    visiblePassField.setPromptText("Password");
    visiblePassField.setStyle(
        "-fx-background-color: #f0f0f0;" +
        "-fx-background-radius: 5;" +
        "-fx-padding: 8;" +
        "-fx-border-color: transparent;"
    );
    visiblePassField.setManaged(false);
    visiblePassField.setVisible(false);

    // Bind both password fields
    visiblePassField.textProperty().bindBidirectional(passField.textProperty());

    // Show Password Checkbox
    CheckBox showPassword = new CheckBox("Show Password");
    showPassword.setStyle("-fx-text-fill: #d55a9d;");
    showPassword.setOnAction(e -> {
        boolean show = showPassword.isSelected();
        passField.setVisible(!show);
        passField.setManaged(!show);
        visiblePassField.setVisible(show);
        visiblePassField.setManaged(show);
    });

    // Login Button
    Button loginBtn = new Button("LOGIN");
    loginBtn.setStyle(
        "-fx-background-color: #d55a9d;" +
        "-fx-text-fill: white;" +
        "-fx-font-weight: bold;" +
        "-fx-background-radius: 5;" +
        "-fx-padding: 8 16;" +
        "-fx-cursor: hand;"
    );

    Label messageLabel = new Label();
    messageLabel.setStyle("-fx-text-fill: red;");

    loginBtn.setOnAction(e -> {
        String idText = idField.getText();
        String passText = passField.isVisible() ? passField.getText() : visiblePassField.getText();
        try {
            int id = Integer.parseInt(idText);
            User user = users.stream().filter(u -> u.getID() == id).findFirst().orElse(null);
            if (user == null) {
                messageLabel.setText("User not found.");
                return;
            }
            if (!user.getPassword().trim().equals(passText.trim())) {
                messageLabel.setText("Incorrect password.");
                return;
            }

            try {
                user.logIn(id, passText);
                messageLabel.setStyle("-fx-text-fill: green;");
                messageLabel.setText("Login successful!");

                if (user instanceof Student) {
                    primaryStage.setScene(StudentDashboard.createStudentDashboardScene(primaryStage, (Student) user));
                } else if (user instanceof Teacher) {
                    primaryStage.setScene(TeacherDashboard.createTeacherDashboardScene(primaryStage, (Teacher) user));
                } else {
                    primaryStage.setScene(Dashboard.createDashboardScene(primaryStage, user));
                }

            } catch (AlreadyLoggedInException ex) {
                messageLabel.setText("Error: " + ex.getMessage());
            }

        } catch (NumberFormatException nfe) {
            messageLabel.setText("ID must be a number.");
        }
    });

    card.getChildren().addAll(
        title, idField, passField, visiblePassField,
        showPassword, loginBtn, messageLabel
    );
    root.getChildren().add(card);

    return new Scene(root, 420, 480);
}



    public static void main(String[] args) {
        launch(args);
    }
}
