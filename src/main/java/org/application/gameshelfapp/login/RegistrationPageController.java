package org.application.gameshelfapp.login;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;


import java.io.IOException;


public class RegistrationPageController extends Application {

    @FXML
    private void goToStartPage(MouseEvent event) {

        try {
            FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/org/application/gameshelfapp/Starting-Page.fxml"));
            Parent root = fxmlloader.load();

            Scene scene = new Scene(root, 1028, 798);
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        } catch (IOException e){
            System.out.println("Couldn't load Starting page...");
        }
    }

    @Override
    public void start(Stage myStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/application/gameshelfapp/Registration-Page.fxml"));
        Parent root = fxmlLoader.load();

        Scene scene = new Scene(root, 1440, 1024);

        myStage.setScene(scene);

        myStage.setTitle("Gameshelf");

        myStage.show();
    }

    public static void main(String[] args){
        launch(args);
    }
}
