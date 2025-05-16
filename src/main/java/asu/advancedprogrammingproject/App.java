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
        Quiz englishQuiz = new Quiz(new Question[]{q1, q2}, englishCourse);
        englishCourse.addQuiz(englishQuiz); 
        testStudent.takeQuiz(englishQuiz);

        // Add users
        users.add(testStudent);
        users.add(mona);
        users.add(carlos);

        primaryStage.setScene(createLoginScene(primaryStage));
        primaryStage.setTitle("Login");
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
        TextField idField = new TextField();
        idField.setPromptText("Enter ID");

        PasswordField passField = new PasswordField();
        passField.setPromptText("Enter Password");

        TextField visiblePassField = new TextField();
        visiblePassField.setPromptText("Enter Password");
        visiblePassField.setManaged(false);
        visiblePassField.setVisible(false);

        visiblePassField.textProperty().bindBidirectional(passField.textProperty());

        CheckBox showPasswordCheckBox = new CheckBox("Show Password");
        showPasswordCheckBox.setOnAction(e -> {
            if (showPasswordCheckBox.isSelected()) {
                visiblePassField.setManaged(true);
                visiblePassField.setVisible(true);

                passField.setManaged(false);
                passField.setVisible(false);
            } else {
                visiblePassField.setManaged(false);
                visiblePassField.setVisible(false);

                passField.setManaged(true);
                passField.setVisible(true);
            }
        });

        Label messageLabel = new Label();

        Button loginBtn = new Button("Login");
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
                    messageLabel.setText("Login successful! Redirecting...");

                    if (user instanceof Student) {
                        primaryStage.setScene(StudentDashboard.createStudentDashboardScene(primaryStage, (Student) user));
                    } else if(user instanceof Teacher) {
                        primaryStage.setScene(TeacherDashboard.createStudentDashboardScene(primaryStage, (Teacher) user));}
                     else {
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
        layout.getChildren().addAll(
                new Label("ID:"), idField,
                new Label("Password:"), passField, visiblePassField,
                showPasswordCheckBox, loginBtn, messageLabel
        );

        return new Scene(layout, 320, 300);
        
    }

    public static void main(String[] args) {
        launch(args);
    }
}
