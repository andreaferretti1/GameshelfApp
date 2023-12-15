package org.application.gameshelfapp;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;


public class StartingPageController extends Application {

    @FXML
    private void switchToRegistrationPage(MouseEvent event) throws IOException {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("Registration-Page.fxml"));
        Parent root = fxmlloader.load();
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1024, 1440);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void logIn(MouseEvent event){

    }

    @Override
    public void start(Stage stage) throws Exception {
        // Load the FXML file
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Starting-Page.fxml"));
        Parent root = fxmlLoader.load();

        // Set the FXML content as the root of the scene
        Scene scene = new Scene(root, 1080, 768);

        // Set the scene to the stage
        stage.setScene(scene);

        // Set other stage properties if needed
        stage.setTitle("GameShelf");

        // Show the stage
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
