package org.application.gameshelfapp.login.graphiccontrollers;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;


public class ErrorPageController extends Application {

    @FXML
    private Label errorLabel;

    public void showErrorMessage(String s){
        this.errorLabel.setText(s);
    }

    public static void displayErrorWindow(String s){

        try {
            ErrorPageController errorPageController = new ErrorPageController();
            errorPageController.start(new Stage());
            errorPageController.showErrorMessage(s);

        } catch(IOException e){
            System.exit(1);
        }
    }
    @Override
    public void start(Stage myStage) throws IOException{

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/application/gameshelfapp/GUI/Error-Page.fxml"));
        Parent root = fxmlLoader.load();

        Scene scene = new Scene(root, 480, 1256);
        myStage.setScene(scene);
        myStage.show();
    }
}
