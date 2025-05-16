package asu.advancedprogrammingproject;

import java.util.ArrayList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import java.util.List;

/**
 *
 * @author Kareem
 */
public class StudentDashboard {

    private static VBox quizBox = new VBox(10);
    private static Label quizzesTakenLabel = new Label();
    private static VBox profileLayout = new VBox(10);
    private static Label profileDetails = new Label();

    public static Scene createStudentDashboardScene(Stage primaryStage, Student student) {
        primaryStage.setTitle("Student Dashboard");

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
            "-fx-tab-min-width: 71.5px;" + // Expand tab width
            "-fx-tab-max-width: 81.5px;" + // Ensure uniformity
            "-fx-background-color: transparent;" // Removes default background
        );


        // **Tabs**
        Tab profileTab = new Tab("Profile", profileLayout);
        profileTab.setClosable(false);

        profileLayout.setPadding(new Insets(15));
        profileLayout.getChildren().addAll(createBoldLabel("Profile Information"), profileDetails);
        updateProfileView(student);

        Tab coursesTab = new Tab("Courses", getCoursesView(student));
        coursesTab.setClosable(false);

        Tab quizzesTab = new Tab("Quizzes", getQuizzesView(student));
        quizzesTab.setClosable(false);

        Tab gradesTab = new Tab("Grades", getGradesView(student));
        gradesTab.setClosable(false);

        tabPane.getTabs().addAll(coursesTab, quizzesTab, gradesTab, profileTab);
        
        // Apply distinct styles for each tab
        profileTab.setStyle("-fx-background-color: #d55a9d; -fx-text-fill: white; -fx-font-weight: bold;");
        coursesTab.setStyle("-fx-background-color: #574bdf; -fx-text-fill: white; -fx-font-weight: bold;");
        quizzesTab.setStyle("-fx-background-color: #892be2; -fx-text-fill: white; -fx-font-weight: bold;");
        gradesTab.setStyle("-fx-background-color: #ff8b94; -fx-text-fill: white; -fx-font-weight: bold;");

        // **Tab switching listener**
        tabPane.getSelectionModel().selectedItemProperty().addListener((obs, oldTab, newTab) -> {
            if (newTab == profileTab) {
                updateProfileView(student);
            }
            if (newTab == quizzesTab) {
                refreshQuizzesView(student);
            }
        });

        // **Styled Logout Button**
        Button logoutButton = new Button("LOGOUT");
        logoutButton.setStyle(
            "-fx-background-color: #A020F0;" +
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

    private static VBox getCoursesView(Student student) {
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(15));
        Label title = createBoldLabel("Enrolled Courses");

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
        Label title = createBoldLabel("Available Quizzes");

        List<Course> courses = student.getEnrolledCourses();
        if (courses.isEmpty()) {
            layout.getChildren().add(new Label("No enrolled courses."));
            return layout;
        }

        ComboBox<Language> languageComboBox = new ComboBox<>();
        for (Course course : courses) {
            if (!languageComboBox.getItems().contains(course.getLanguage())) {
                languageComboBox.getItems().add(course.getLanguage());
            }
        }
        languageComboBox.setPromptText("Select a Language");

        Button openQuizButton = new Button("Open Quiz");
        openQuizButton.setVisible(false);
        openQuizButton.setStyle(
            "-fx-background-color: #A020F0;" +
            "-fx-text-fill: white;" +
            "-fx-font-weight: bold;" +
            "-fx-background-radius: 5;" +
            "-fx-padding: 8 16;" +
            "-fx-cursor: hand;"
        );
        
        languageComboBox.setOnAction(e -> {
            quizBox.getChildren().clear();
            Language selectedLanguage = languageComboBox.getValue();
            if (selectedLanguage == null) return;

            Course course = courses.stream()
                .filter(c -> c.getLanguage().equals(selectedLanguage))
                .findFirst()
                .orElse(null);

            if (course != null && course.getQuizzes() != null && !course.getQuizzes().isEmpty()) {
                Quiz quiz = course.getQuizzes().get(0);
                openQuizButton.setVisible(true);
                openQuizButton.setUserData(course);
                openQuizButton.setDisable(student.getQuizzesTaken().contains(quiz));
            } else {
                openQuizButton.setVisible(false);
                quizBox.getChildren().add(new Label("No quiz available for selected language."));
            }
        });

        openQuizButton.setOnAction(e -> {
            Course course = (Course) openQuizButton.getUserData();
            Quiz quiz = course.getQuizzes().get(0);

            if (student.getQuizzesTaken().contains(quiz)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Quiz Already Taken");
                alert.setHeaderText(null);
                alert.setContentText("You have already completed this quiz.");
                alert.showAndWait();
                return;
            }

            Stage quizStage = new Stage();
            quizStage.setTitle("Quiz - " + course.getLanguage().getLanguageName());

            VBox quizLayout = new VBox(10);
            quizLayout.setPadding(new Insets(15));
            List<Question> questions = List.of(quiz.getQuestions());
            List<TextField> answerFields = new ArrayList<>();

            for (int i = 0; i < questions.size(); i++) {
                Question q = questions.get(i);
                Label questionLabel = new Label((i + 1) + ". " + q.getText());
                questionLabel.setFont(Font.font("System", FontWeight.BOLD, 12));
                TextField answerField = new TextField();
                answerField.setPromptText("Answer (True/False)");
                answerFields.add(answerField);
                quizLayout.getChildren().addAll(questionLabel, answerField);
            }

            Button submitButton = new Button("Submit");
            submitButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-font-weight: bold;");
            Label resultLabel = new Label();
            resultLabel.setTextFill(Color.DARKBLUE);
            resultLabel.setFont(Font.font("System", FontWeight.BOLD, 13));

            submitButton.setOnAction(event -> {
                resultLabel.setText("");

                for (TextField answerField : answerFields) {
                    if (answerField.getText().trim().isEmpty()) {
                        resultLabel.setText("Please answer all questions before submitting.");
                        return;
                    }
                }

                for (int i = 0; i < questions.size(); i++) {
                    String studentAnswer = answerFields.get(i).getText().trim();
                    questions.get(i).answer(studentAnswer);
                }

                if (!student.getQuizzesTaken().contains(quiz)) {
                    student.takeQuiz(quiz);
                    student.setGrade(course, student.getGrade(course));

                    int total = quiz.getTotalGrade();
                    Integer score = student.getGrade(course);
                    int percentage = (score != null && total > 0) ? (score * 100) / total : 0;
                    resultLabel.setText("You scored: " + percentage + "%");

                    openQuizButton.setDisable(true);
                    refreshQuizzesView(student);
                    updateProfileView(student);
                } else {
                    resultLabel.setText("You already took this quiz.");
                }

                submitButton.setDisable(true);
            });

            quizLayout.getChildren().addAll(submitButton, resultLabel);
            Scene quizScene = new Scene(quizLayout, 400, 400);
            quizStage.setScene(quizScene);
            quizStage.show();
        });

        quizBox.setPadding(new Insets(10));
        refreshQuizzesView(student);

        layout.getChildren().addAll(title, languageComboBox, openQuizButton, quizBox);
        return layout;
    }

    private static void refreshQuizzesView(Student student) {
        quizBox.getChildren().clear();

        quizzesTakenLabel.setText("Quizzes Taken: " + student.getQuizzesTaken().size());
        quizzesTakenLabel.setFont(Font.font("System", FontWeight.BOLD, 12));
        quizzesTakenLabel.setTextFill(Color.DARKBLUE);
        quizBox.getChildren().add(quizzesTakenLabel);

        for (Quiz q : student.getQuizzesTaken()) {
            Integer grade = student.getGrade(q.getCourse());
            int total = q.getTotalGrade();
            int percentage = (grade != null && total > 0) ? (grade * 100) / total : 0;

            Label quizGradeLabel = new Label(q.getCourse().getLanguage().getLanguageName()
                    + " quiz grade: " + (grade != null ? grade + "/" + total + " (" + percentage + "%)" : "N/A"));
            quizGradeLabel.setTextFill(Color.DARKBLUE);
            quizGradeLabel.setFont(Font.font("System", FontWeight.NORMAL, 12));
            quizBox.getChildren().add(quizGradeLabel);
        }
    }

    private static void updateProfileView(Student student) {
         StringBuilder history = new StringBuilder();
        for (Quiz quiz : student.getQuizzesTaken()) {
            int grade = student.getGrade(quiz.getCourse());
            history.append("Quiz: ").append(quiz.getTitle())
                   .append(" | Score: ").append(grade).append("%\n");
        }
        profileDetails.setText(
            "Name: " + student.getName() + "\n" +
            "ID: " + student.getID() + "\n" +
            "Enrolled Courses: " + student.getEnrolledCourses().size() + "\n" +
            "Quizzes Taken: " + student.getQuizzesTaken().size() + "\n\n" +
            "Quiz History:\n" + (history.length() > 0 ? history : "No quizzes completed yet.")
        );
        profileDetails.setFont(Font.font("System", FontWeight.NORMAL, 13));
    }

    private static VBox getGradesView(Student student) {
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(15));
        Label title = createBoldLabel("Grades");

        Label gradesLabel = new Label(student.getGrades().isEmpty() ? "No grades available." : student.getGrades());
        gradesLabel.setTextFill(Color.DARKBLUE);
        gradesLabel.setFont(Font.font("System", FontWeight.NORMAL, 12));
        
        layout.getChildren().addAll(title, gradesLabel);
        return layout;
    }

    private static VBox getProfileView(Student student) {
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(15));
        Label title = createBoldLabel("Profile Information");

        Label profileDetails = new Label(
            "Name: " + student.getName() + "\n" +
            "ID: " + student.getID() + "\n" +
            "Enrolled Courses: " + student.getEnrolledCourses().size() + "\n" +
            "Quizzes Taken: " + student.getQuizzesTaken().size()
        );

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
