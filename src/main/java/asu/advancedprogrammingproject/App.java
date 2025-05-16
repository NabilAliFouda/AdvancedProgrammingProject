package asu.advancedprogrammingproject;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;

public class App extends Application {

    private List<User> users = new ArrayList<>();
    private List<Language> predefinedLanguages = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) {
        // **Predefined languages**
        predefinedLanguages.add(new Language("English"));
        predefinedLanguages.add(new Language("Spanish"));

        // **Create test student (Ali)**
        Student testStudent = new Student("Ali", 101, "pass123");

        // **Enroll Ali in two courses**
        Course englishCourse = new Course(predefinedLanguages.get(0), 1, "Beginner", 100, new Teacher("Mona", 201, "teachpass", predefinedLanguages.get(0)), new ArrayList<>());
        Course spanishCourse = new Course(predefinedLanguages.get(1), 2, "Intermediate", 120, new Teacher("Carlos", 202, "teachpass", predefinedLanguages.get(1)), new ArrayList<>());

        testStudent.enroll(englishCourse);
        testStudent.enroll(spanishCourse);

        // **Create a quiz ONLY for English course**
        Question q1 = new Question("What is Java?", "Programming Language");
        Quiz englishQuiz = new Quiz(new Question[]{q1}, englishCourse);
        testStudent.takeQuiz(englishQuiz);

        // **Store test student in users list**
        users.add(testStudent);
        users.add(new Teacher("Mona", 201, "teachpass", predefinedLanguages.get(0)));
        users.add(new Teacher("Carlos", 202, "teachpass", predefinedLanguages.get(1)));

        primaryStage.setScene(createLoginScene(primaryStage));
        primaryStage.setTitle("Login");
        primaryStage.show();
    }

    public Scene createLoginScene(Stage primaryStage) {
        TextField idField = new TextField();
        idField.setPromptText("Enter ID");

        PasswordField passField = new PasswordField();
        passField.setPromptText("Enter Password");

        Label messageLabel = new Label();

        Button loginBtn = new Button("Login");
        loginBtn.setOnAction(e -> {
            String idText = idField.getText();
            String passText = passField.getText();

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
                    messageLabel.setText("Login successful! Redirecting...");
                    if (user instanceof Student) {
                        primaryStage.setScene(StudentDashboard.createStudentDashboardScene(primaryStage, (Student) user));
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

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(15));
        layout.getChildren().addAll(new Label("ID:"), idField, new Label("Password:"), passField, loginBtn, messageLabel);

        return new Scene(layout, 320, 280);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
