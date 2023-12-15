package org.application.gameshelfapp;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

import java.io.IOException;


public class RegistrationPageController extends Application {

    @Override
    public void start(Stage myStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Registration-page.fxml"));
        Parent root = fxmlLoader.load();

        Scene scene = new Scene(root, 1440, 1040);

        myStage.setScene(scene);

        myStage.setTitle("Gameshelf");

        myStage.show();
    }

    public static void main(String[] args){
        launch(args);
    }
}
