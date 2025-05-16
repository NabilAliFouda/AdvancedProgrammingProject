package asu.advancedprogrammingproject;

import java.util.ArrayList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.List;

public class StudentDashboard {

    private static VBox quizBox = new VBox(10);
    private static Label quizzesTakenLabel = new Label();
    private static VBox profileLayout = new VBox(10);
    private static Label profileDetails = new Label();

    public static Scene createStudentDashboardScene(Stage primaryStage, Student student) {
        primaryStage.setTitle("Student Dashboard");

        Tab profileTab = new Tab("Profile");
        profileTab.setClosable(false);
        profileLayout.setPadding(new Insets(15));
        profileLayout.getChildren().addAll(new Label("Profile Information"), profileDetails);
        profileTab.setContent(profileLayout);
        updateProfileView(student);

        TabPane tabPane = new TabPane();

        Tab coursesTab = new Tab("Courses", getCoursesView(student));
        coursesTab.setClosable(false);

        Tab quizzesTab = new Tab("Quizzes");
        quizzesTab.setClosable(false);
        quizzesTab.setContent(getQuizzesView(student));

        Tab gradesTab = new Tab("Grades", getGradesView(student));
        gradesTab.setClosable(false);

        tabPane.getTabs().addAll(coursesTab, quizzesTab, gradesTab, profileTab);

        // Update profile info whenever Profile tab is selected
        tabPane.getSelectionModel().selectedItemProperty().addListener((obs, oldTab, newTab) -> {
            if (newTab == profileTab) {
                updateProfileView(student);
            }
            if (newTab == quizzesTab) {
                refreshQuizzesView(student);
            }
        });

        Button logoutButton = new Button("Logout");
        logoutButton.setOnAction(e -> {
            App appInstance = new App();
            primaryStage.setScene(appInstance.createLoginScene(primaryStage));
        });

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(15));
        layout.getChildren().addAll(tabPane, logoutButton);

        return new Scene(layout, 600, 400);
    }

    private static VBox getCoursesView(Student student) {
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(15));
        Label title = new Label("Enrolled Courses");

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
        Label title = new Label("Available Quizzes");

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
                TextField answerField = new TextField();
                answerField.setPromptText("Answer (True/False)");
                answerFields.add(answerField);
                quizLayout.getChildren().addAll(questionLabel, answerField);
            }

            Button submitButton = new Button("Submit");
            // Inside the openQuizButton.setOnAction(...) where you build the quiz UI:

            Label resultLabel = new Label();  // starts empty, no message yet

            submitButton.setOnAction(event -> {
                // Clear previous message each submit
                resultLabel.setText("");

                // Check if all questions answered
                for (TextField answerField : answerFields) {
                    if (answerField.getText().trim().isEmpty()) {
                        resultLabel.setText("Question not answered");
                        return;
                    }
                }

                int correctCount = 0;
                for (int i = 0; i < questions.size(); i++) {
                    String studentAnswer = answerFields.get(i).getText().trim();
                    if (studentAnswer.equalsIgnoreCase(questions.get(i).getCorrectAnswer())) {
                        correctCount++;
                    }
                }

                double score = ((double) correctCount / questions.size()) * 100;
                resultLabel.setText("You scored: " + score + "%");

                if (!student.getQuizzesTaken().contains(quiz)) {
                    student.takeQuiz(quiz);
                    student.setGrade(course, (int) score);
                    openQuizButton.setDisable(true);
                    refreshQuizzesView(student);
                    updateProfileView(student);
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
        quizBox.getChildren().add(quizzesTakenLabel);

        for (Quiz q : student.getQuizzesTaken()) {
            Integer grade = student.getGrade(q.getCourse());
            Label gradeLabel = new Label(q.getCourse().getLanguage().getLanguageName()
                    + " quiz grade: " + (grade != null ? grade + "%" : "N/A"));
            quizBox.getChildren().add(gradeLabel);
        }
    }

    private static void updateProfileView(Student student) {
        profileDetails.setText(
            "Name: " + student.getName() + "\n" +
            "ID: " + student.getID() + "\n" +
            "Enrolled Courses: " + student.getEnrolledCourses().size() + "\n" +
            "Quizzes Taken: " + student.getQuizzesTaken().size()
        );
    }

    private static VBox getGradesView(Student student) {
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(15));
        Label title = new Label("Grades");

        Label gradesLabel = new Label(student.getGrades().isEmpty() ? "No grades available." : student.getGrades());
        layout.getChildren().addAll(title, gradesLabel);
        return layout;
    }

    private static VBox getProfileView(Student student) {
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(15));
        Label title = new Label("Profile Information");

        Label profileDetails = new Label(
            "Name: " + student.getName() + "\n" +
            "ID: " + student.getID() + "\n" +
            "Enrolled Courses: " + student.getEnrolledCourses().size() + "\n" +
            "Quizzes Taken: " + student.getQuizzesTaken().size()
        );

        layout.getChildren().addAll(title, profileDetails);
        return layout;
    }
}
