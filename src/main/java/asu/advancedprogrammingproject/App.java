package asu.advancedprogrammingproject;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


/**
 * JavaFX App
 */
public class App extends Application {
    @Override
    public void start(Stage stage) {
        TextField Name = new TextField();
        Name.setPromptText("Enter your name");
        TextField password = new TextField();
        password.setPromptText("Enter your password");
        Label Name_label = new Label("Name: ");
        Label Password_label = new Label("Password: ");
        HBox Name_layout = new HBox();
        HBox Password_layout = new HBox();
        Name_layout.getChildren().addAll(Name_label, Name);
        Password_layout.getChildren().addAll(Password_label, password);
        VBox Vertical_layout= new VBox();
        Vertical_layout.getChildren().addAll(Name_layout, Password_layout);
        Scene scene = new Scene(Vertical_layout, 300, 250);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}

    



