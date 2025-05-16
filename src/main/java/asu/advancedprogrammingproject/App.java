package asu.advancedprogrammingproject;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;

/**
 * JavaFX App
 */

public class App extends Application {

    private List<User> users = new ArrayList<>();
    private List<Language> predefinedLanguages = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) {
        // **Predefined languages**
        predefinedLanguages.add(new Language("English"));
        predefinedLanguages.add(new Language("Spanish"));
        predefinedLanguages.add(new Language("French"));
        predefinedLanguages.add(new Language("German"));

        // Sample users
        users.add(new Student("Ali", 101, "pass123"));
        users.add(new Teacher("Mona", 201, "teachpass", predefinedLanguages.get(0))); // Select "English"

        primaryStage.setScene(createLoginScene(primaryStage));
        primaryStage.setTitle("Login");
        primaryStage.show();
    }

    // Creates Login Scene
    public Scene createLoginScene(Stage primaryStage) {
        TextField idField = new TextField();
        idField.setPromptText("Enter ID");

        PasswordField passField = new PasswordField();
        passField.setPromptText("Enter Password");
        
        TextField visiblePassField = new TextField();
        visiblePassField.setPromptText("Enter Password");
        visiblePassField.setManaged(false);
        visiblePassField.setVisible(false);

        // Synchronize the text between both fields
        visiblePassField.textProperty().bindBidirectional(passField.textProperty());

        // Show password checkbox
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
            // Read password from whichever field is visible
            String passText = passField.isVisible() ? passField.getText() : visiblePassField.getText();

            try {
                int id = Integer.parseInt(idText);
                User user = users.stream().filter(u -> u.getID() == id).findFirst().orElse(null);

                if (user == null) {
                    messageLabel.setText("User not found. Please check your ID.");
                    return;
                }

                if (!user.getPassword().trim().equals(passText.trim())) {
                    messageLabel.setText("Incorrect password.");
                    return;
                }

                try {
                    user.logIn(id, passText);
                    messageLabel.setText("Login successful! Redirecting...");
                    primaryStage.setScene(Dashboard.createDashboardScene(primaryStage, user)); 
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
            new Label("ID:"), 
            idField, 
            new Label("Password:"), 
            passField, 
            visiblePassField,   // add the visible password field here as well
            showPasswordCheckBox,  // add the checkbox here
            loginBtn, 
            messageLabel
        );

        return new Scene(layout, 320, 280);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
